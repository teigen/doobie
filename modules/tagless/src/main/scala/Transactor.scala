// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless

import cats._
import cats.effect._
import cats.implicits._
import fs2.Stream

/**
 * A bundle of configuration sufficient to interpret programs depending on `Connection[F]`, using
 * an `Interpreter[F]` to execute JDBC operations; a `Strategy[F]` to define transactio handling;
 * and a `Connector[F]` to provide fresh connections. `Transactor` also indirectly provides an
 * `RTS` and a `Logger` via the interpreter.
 */
@SuppressWarnings(Array("org.wartremover.warts.Overloading"))
final case class Transactor[F[_]](
  interp:    Interpreter[F],
  strategy:  Strategy[F],
  connector: Connector[F]
) {

  /** The runtime system used for managing blocking operations, provided by `interp.rts`. */
  def rts: RTS[F] =
    interp.rts

  /** The logger used for JDBC tracing, provided by `interp.log`. */
  def log: Logger[F] =
    interp.log

  /**
   * `Resource` yielding a `Connection[F]`, which will be closed after use. Note that `strategy` is
   * not consulted; any configuration or transactional handling must be performed manually.
   */
  def connect(implicit ev: Functor[F]): Resource[F, Connection[F]] =
    Resource.make(connector.open.map(interp.forConnection))(_.jdbc.close)

  /**
   * Apply a `Connection[F]` to `f`, with transaction handling as defined by `strategy`, yielding
   * an `F[A]`.
   */
  def transact[A](f: Connection[F] => F[A])(implicit ev: Bracket[F, Throwable]): F[A] =
    connect.use(strategy.transact(f))

  /**
   * Apply a `Connection[F]` to `f`, with transaction handling as defined by `strategy`, yielding a
   * `Stream[F, A]`.
   */
  def transactStream[A](f: Connection[F] => Stream[F, A])(implicit ev: Functor[F]): Stream[F, A] =
    Stream.resource(connect).flatMap(strategy.transact(f))

}

