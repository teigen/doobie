// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless

import cats.{ Alternative, Functor }
import cats.effect.{ Bracket, Resource }
import cats.implicits._
import doobie.{ Fragment, Read, Write }
import doobie.tagless.jdbc._
import doobie.enum._
import fs2.{ Sink, Stream }
import scala.collection.generic.CanBuildFrom

// TODO: WEAKEN BRACKET TO MONAD WHEN THERE'S A NEW DROP OF CATS-EFFECT


@SuppressWarnings(Array("org.wartremover.warts.DefaultArguments"))
final case class Connection[F[_]](jdbc: JdbcConnection[F], interp: Interpreter[F]) {

  /** Prepare a statement. */
  def prepareStatement(
    sql: String,
    resultSetType:        ResultSetType        = ResultSetType.TypeForwardOnly,
    resultSetConcurrency: ResultSetConcurrency = ResultSetConcurrency.ConcurReadOnly,
    resultSetHoldability: Holdability          = Holdability.CloseCursorsAtCommit
  )(implicit ev: Functor[F]): Resource[F, PreparedStatement[F]] =
    Resource.make(jdbc.prepareStatement(
      sql,
      resultSetType.toInt,
      resultSetConcurrency.toInt,
      resultSetHoldability.toInt
    ).map(interp.forPreparedStatement))(_.jdbc.close)

  /** Prepare and execute a statement. */
  def executeQuery(
    fragment:             Fragment,
    chunkSize:            Int,
    resultSetType:        ResultSetType        = ResultSetType.TypeForwardOnly,
    resultSetConcurrency: ResultSetConcurrency = ResultSetConcurrency.ConcurReadOnly,
    resultSetHoldability: Holdability          = Holdability.CloseCursorsAtCommit
  )(implicit ev: Bracket[F, _]): Resource[F, ResultSet[F]] =
    for {
      ps <- prepareStatement(fragment.sql, resultSetType, resultSetConcurrency, resultSetHoldability)
      _  <- Resource.liftF(ps.setArguments(fragment))
      _  <- Resource.liftF(ps.jdbc.setFetchSize(chunkSize))
      rs <- ps.executeQuery
    } yield rs

  /** Stream the results of the specified `Fragment`, reading a `chunkSize` rows at a time. */
  def stream[A: Read](fragment: Fragment, chunkSize: Int)(
    implicit ev: Bracket[F, _]
  ): Stream[F, A] =
    Stream.resource(executeQuery(fragment, chunkSize)).flatMap(_.stream[A](chunkSize))

  /** Read at most one row, raising an error if more are returned. */
  def option[A: Read](fragment: Fragment)(
    implicit ev: Bracket[F, Throwable]
  ): F[Option[A]] =
    executeQuery(fragment, 2).use(_.option[A])

  /** Read exactly one row, raising an error otherwise. */
  def unique[A: Read](fragment: Fragment)(
    implicit ev: Bracket[F, Throwable]
  ): F[A] =
    executeQuery(fragment, 2).use(_.unique[A])

  /**
   * Accumulate the results of the specified `Fragment` into a collection `C` with
   * element type `A` using `CanBuildFrom`. This is the fastest way to accumulate a
   * resultset into a collection.
   */
  def to[C[_], A: Read](fragment: Fragment)(
    implicit ev: Bracket[F, Throwable],
            cbf: CanBuildFrom[Nothing, A, C[A]]
  ): F[C[A]] =
    executeQuery(fragment, Int.MaxValue).use(_.chunk[C, A](Int.MaxValue))

  /**
   * Accumulate the results of the specified `Fragment` into a collection `C` with
   * element type `A` using `Alternative`. This is less efficient that `to`, which you
   * should prefer if a `CanBuildFrom` is available.
   */
  def accumulate[C[_]: Alternative, A: Read](fragment: Fragment)(
    implicit ev: Bracket[F, Throwable]
  ): F[C[A]] =
    executeQuery(fragment, Int.MaxValue).use(_.chunkA[C, A](Int.MaxValue))

  /** A sink that consumes values of type `A`. */
  def sink[A: Write](fragment: Fragment)(implicit ev: Functor[F]): Sink[F, A] = sa =>
    for {
      ps <- Stream.resource(prepareStatement(fragment.sql))
      _  <- Stream.eval(ps.setArguments(fragment))
      _  <- ps.sink[A].apply(sa)
    } yield ()

}
