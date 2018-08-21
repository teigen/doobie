// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless

import cats.effect._
import cats.implicits._
import java.sql

/** A source of JDBC `Connection`s, allocated in `F`. */
trait Connector[F[_]] {
  def connect: Resource[F, sql.Connection]
}

object Connector {

    /**
     * Module of constructors for `Connector` for any async effect `F`, using the JDBC
     * `DriverManager` as a source of connections.
     */
    @SuppressWarnings(Array("org.wartremover.warts.Overloading"))
    object fromDriverManager {

      @SuppressWarnings(Array("org.wartremover.warts.NonUnitStatements"))
      private def create[F[_]: Sync](driver: String, conn: => sql.Connection)(
        implicit rts: RTS[F]
      ): Connector[F] =
        new Connector[F] {

          val open: F[sql.Connection] =
            rts.newBlockingPrimitive {
              Class.forName(driver)
              val c = conn
              rts.log.unsafe.trace(c, "«fresh connection»")
              c
            }

          def close(c: sql.Connection): F[Unit] =
            rts.newBlockingPrimitive {
              rts.log.unsafe.trace(c, "close()")
              c.close
            }

          val connect =
            Resource.make(open)(close)

        }

      def apply[F[_]: RTS: Sync](driver: String, url: String): Connector[F] =
        create(driver, sql.DriverManager.getConnection(url))

      def apply[F[_]: RTS: Sync](driver: String, url: String, user: String, pass: String): Connector[F] =
        create(driver, sql.DriverManager.getConnection(url, user, pass))

      def apply[F[_]: RTS: Sync](driver: String, url: String, info: java.util.Properties): Connector[F] =
        create(driver, sql.DriverManager.getConnection(url, info))

    }

}