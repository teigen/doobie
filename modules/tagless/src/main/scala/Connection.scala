// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless

import cats.{ Alternative }
import cats.effect.{ Resource, Sync }
import cats.implicits._
import doobie.Fragment
import doobie.tagless.jdbc._
import doobie.enum._
import fs2.{ Sink, Stream }
import scala.collection.generic.CanBuildFrom

@SuppressWarnings(Array("org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Overloading"))
final case class Connection[F[_]: Sync](jdbc: JdbcConnection[F], interp: Interpreter[F]) {

  /** Prepare a statement, yielding a `PreparedStatement`. */
  def prepareStatement(
    sql: String,
    resultSetType:        ResultSetType        = ResultSetType.TypeForwardOnly,
    resultSetConcurrency: ResultSetConcurrency = ResultSetConcurrency.ConcurReadOnly,
    resultSetHoldability: Holdability          = Holdability.CloseCursorsAtCommit
  ): Resource[F, PreparedStatement[F]] =
    Resource.make(jdbc.prepareStatement(
      sql,
      resultSetType.toInt,
      resultSetConcurrency.toInt,
      resultSetHoldability.toInt
    ).map(interp.forPreparedStatement))(_.jdbc.close)

  /** Prepare and execute a statement, yielding a `ResultSet`. */
  def executeQuery(
    fragment:             Fragment,
    chunkSize:            Int,
    resultSetType:        ResultSetType        = ResultSetType.TypeForwardOnly,
    resultSetConcurrency: ResultSetConcurrency = ResultSetConcurrency.ConcurReadOnly,
    resultSetHoldability: Holdability          = Holdability.CloseCursorsAtCommit
  ): Resource[F, ResultSet[F]] =
    for {
      ps <- prepareStatement(fragment.sql, resultSetType, resultSetConcurrency, resultSetHoldability)
      _  <- Resource.liftF(ps.setArguments(fragment))
      _  <- Resource.liftF(ps.jdbc.setFetchSize(chunkSize))
      rs <- ps.executeQuery
    } yield rs

  /** Stream the results of the specified `Fragment`, reading a `chunkSize` rows at a time. */
  def stream[A](query: Query[A], chunkSize: Int): Stream[F, A] =
    Stream.resource(executeQuery(query.fragment, chunkSize))
      .flatMap(_.stream[A](chunkSize)(query.read))

  /** Read at most one row, raising an error if more are returned. */
  def option[A](query: Query[A]): F[Option[A]] =
    executeQuery(query.fragment, 2).use(_.option[A](query.read, implicitly))

  /** Read exactly one row, raising an error otherwise. */
  def unique[A](query: Query[A]): F[A] =
    executeQuery(query.fragment, 2).use(_.unique[A](query.read, implicitly))

  /**
   * Accumulate the results of the specified `Fragment` into a collection `C` with
   * element type `A` using `CanBuildFrom`. This is the fastest way to accumulate a
   * resultset into a collection. Usage: `c.to[List](myQuery)`
   */
  object to {

    def apply[C[_]]: Partial[C] =
      new Partial[C]

    final class Partial[C[_]] {
      def apply[A](query: Query[A])(
        implicit cbf: CanBuildFrom[Nothing, A, C[A]]
      ): F[C[A]] =
        executeQuery(query.fragment, Int.MaxValue)
          .use(_.chunk[C, A](Int.MaxValue)(query.read, cbf))
    }

  }

  /**
   * Accumulate the results of the specified `Fragment` into a collection `C` with
   * element type `A` using `Alternative`. This is less efficient that `to`, which you
   * should prefer if a `CanBuildFrom` is available. Usage: `c.accumluate[Chain](myQuery)`
   */
  object accumulate {

    def apply[C[_]]: Partial[C] =
      new Partial[C]

    final class Partial[C[_]] {
      def apply[A](query: Query[A])(
        implicit ac: Alternative[C]
      ): F[C[A]] =
        executeQuery(query.fragment, Int.MaxValue)
          .use(_.chunkA[C, A](Int.MaxValue)(ac, query.read))
    }

  }

  /** A sink that consumes values of type `A`. */
  def sink[A](update: Update[A]): Sink[F, A] = sa =>
    for {
      ps <- Stream.resource(prepareStatement(update.fragment.sql))
      _  <- Stream.eval(ps.setArguments(update.fragment))
      _  <- ps.sink[A](update.write).apply(sa)
    } yield ()

}
