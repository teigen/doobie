// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless

import cats._
import cats.effect._
import cats.implicits._
import doobie.tagless.async._
import fs2.Stream
import fs2.Stream.eval_
import java.sql

/**
 * A source of JDBC `Connection`s, allocated in `F`. Lifetime management is the caller's
 * responsibility (i.e., it's up to you to `close()` the connection when you're done).
 */
final case class Connector[F[_]](open: F[sql.Connection])

object Connector {

    /**
     * Module of constructors for `Connector` for any async effect `F`, using the JDBC
     * `DriverManager` as a source of connections.
     */
    @SuppressWarnings(Array("org.wartremover.warts.Overloading"))
    object fromDriverManager {

      @SuppressWarnings(Array("org.wartremover.warts.NonUnitStatements"))
      private def create[F[_]: Async](driver: String, conn: => sql.Connection): Connector[F] =
        Connector(Sync[F].delay { Class.forName(driver); conn })

      def apply[F[_]: Async](driver: String, url: String): Connector[F] =
        create(driver, sql.DriverManager.getConnection(url))

      def apply[F[_]: Async](driver: String, url: String, user: String, pass: String): Connector[F] =
        create(driver, sql.DriverManager.getConnection(url, user, pass))

      def apply[F[_]: Async](driver: String, url: String, info: java.util.Properties): Connector[F] =
        create(driver, sql.DriverManager.getConnection(url, info))

    }

}