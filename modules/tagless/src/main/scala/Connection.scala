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
trait Connection[F[_]] {

  def jdbc: JdbcConnection[F]

  /** Prepare a statement, yielding a `PreparedStatement`. */
  def prepareStatement(
    sql: String,
    resultSetType:        ResultSetType        = ResultSetType.TypeForwardOnly,
    resultSetConcurrency: ResultSetConcurrency = ResultSetConcurrency.ConcurReadOnly,
    resultSetHoldability: Holdability          = Holdability.CloseCursorsAtCommit
  ): Resource[F, PreparedStatement[F]]

  /** Prepare and execute a query represented as a, yielding a `ResultSet`. */
  def executeQuery(
    fragment:             Fragment,
    chunkSize:            Int,
    resultSetType:        ResultSetType        = ResultSetType.TypeForwardOnly,
    resultSetConcurrency: ResultSetConcurrency = ResultSetConcurrency.ConcurReadOnly,
    resultSetHoldability: Holdability          = Holdability.CloseCursorsAtCommit
  ): Resource[F, ResultSet[F]]

  /** Stream the results of the specified `Fragment`, reading a `chunkSize` rows at a time. */
  def stream[A](query: Query[A], chunkSize: Int): Stream[F, A]

  /** Read at most one row, raising an error if more are returned. */
  def option[A](query: Query[A]): F[Option[A]]

  /** Read exactly one row, raising an error otherwise. */
  def unique[A](query: Query[A]): F[A]

  /** A sink that consumes values of type `A`. */
  def sink[A](update: Update[A]): Sink[F, A]

  /**
   * Accumulate the results of the specified `Fragment` into a collection `C` with
   * element type `A` using `CanBuildFrom`. This is the fastest way to accumulate a
   * resultset into a collection. Usage: `c.to[List](myQuery)`
   */
  def to[C[_]]: ToPartial[C]
  trait ToPartial[C[_]] {
    def apply[A](query: Query[A])(implicit cbf: CanBuildFrom[Nothing, A, C[A]]): F[C[A]]
  }

  /**
   * Accumulate the results of the specified `Fragment` into a collection `C` with
   * element type `A` using `Alternative`. This is less efficient that `to`, which you
   * should prefer if a `CanBuildFrom` is available. Usage: `c.accumluate[Chain](myQuery)`
   */
  def accumulate[C[_]]: AccumulatePartial[C]
  trait AccumulatePartial[C[_]] {
    def apply[A](query: Query[A])(implicit ac: Alternative[C]): F[C[A]]
  }

}


@SuppressWarnings(Array("org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Overloading"))
object Connection {

  def async[F[_]: Sync](jdbc0: JdbcConnection[F], interp: Interpreter[F]): Connection[F] =
    new Connection[F] {

      def jdbc = jdbc0

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

      def stream[A](query: Query[A], chunkSize: Int): Stream[F, A] =
        Stream.resource(executeQuery(query.fragment, chunkSize))
          .flatMap(_.stream[A](chunkSize)(query.read))

      def option[A](query: Query[A]): F[Option[A]] =
        executeQuery(query.fragment, 2).use(_.option[A](query.read, implicitly))

      def unique[A](query: Query[A]): F[A] =
        executeQuery(query.fragment, 2).use(_.unique[A](query.read, implicitly))

      def to[C[_]] =
        new ToPartial[C] {
          def apply[A](query: Query[A])(
            implicit cbf: CanBuildFrom[Nothing, A, C[A]]
          ): F[C[A]] =
            executeQuery(query.fragment, Int.MaxValue)
              .use(_.chunk[C, A](Int.MaxValue)(query.read, cbf))
        }

      def accumulate[C[_]] =
        new AccumulatePartial[C] {
          def apply[A](query: Query[A])(
            implicit ac: Alternative[C],
          ): F[C[A]] =
            executeQuery(query.fragment, Int.MaxValue)
              .use(_.chunkA[C, A](Int.MaxValue)(ac, query.read))
        }

      def sink[A](update: Update[A]): Sink[F, A] = sa =>
        for {
          ps <- Stream.resource(prepareStatement(update.fragment.sql))
          _  <- Stream.eval(ps.setArguments(update.fragment))
          _  <- ps.sink[A](update.write).apply(sa)
        } yield ()

    }

}