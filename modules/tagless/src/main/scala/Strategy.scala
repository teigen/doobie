// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless

import cats._
import cats.implicits._

/**
 * A strategy used by [[Transactor]]s for configuring connections. Typically a `transactional`
 * instance is what you want, although `void` is appopriate for back ends that don't
 * support transactions (Apache Hive for example). You can provide additional per-connection
 * configuration (such as setting the search path) by updating the `before` member.
 * @param before a program
 */
final case class Strategy[F[_]](
  before:  Connection[F] => F[Unit],
  after:   Connection[F] => F[Unit],
  onError: Connection[F] => F[Unit]
)

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