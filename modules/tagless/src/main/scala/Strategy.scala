// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless

import cats._
import cats.implicits._
import fs2.Stream
import fs2.Stream.eval_


/**
 * A strategy used by [[Transactor]]s for configuring connections. Typically a `transactional`
 * instance is what you want, although `void` is appopriate for back ends that don't
 * support transactions (Apache Hive for example). You can provide additional per-connection
 * configuration (such as setting the search path) by updating the `before` member.
 * @param before a program
 */
@SuppressWarnings(Array("org.wartremover.warts.Overloading"))
final case class Strategy[F[_]](
  before:  Connection[F] => F[Unit],
  after:   Connection[F] => F[Unit],
  onError: Connection[F] => F[Unit]
) {

  /**
   * Given a computation dependent on a `Connection[F]`, construct an equivalent computation
   * wrapped with the logic defined by this strategy.
   */
  def transact[A](f: Connection[F] => F[A])(
    implicit ev: ApplicativeError[F, Throwable]
  ): Connection[F] => F[A] = c =>
    (before(c) *> f(c) <* after(c)).onError { case _ => onError(c) }

  /**
   * Given a stream dependent on a `Connection[F]`, construct an equivalent stream wrapped with the
   * logic defined by this strategy.
   */
  def transact[A](f: Connection[F] => Stream[F, A]): Connection[F] => Stream[F, A] = c =>
    eval_(before(c)) ++ f(c) ++ eval_(after(c)).onError { case _ => eval_(onError(c)) }

}

object Strategy {

  /**
   * A default transactional strategy that configures the connection by setting `AutoCommit` to
   * `false`, committing on success, and rolling back on failure.
   */
  def transactional[F[_]]: Strategy[F] =
    Strategy[F](
      _.jdbc.setAutoCommit(false),
      _.jdbc.commit,
      _.jdbc.rollback
    )

  /** A default strategy that does nothing. */
  def void[F[_]: Applicative]: Strategy[F] =
    Strategy[F](
      _ => ().pure[F],
      _ => ().pure[F],
      _ => ().pure[F]
    )

}