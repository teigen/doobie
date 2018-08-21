// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless.async

import cats.effect.Sync
import doobie.tagless.{ RTS, Logger }
import doobie.tagless.jdbc._
import org.slf4j.{ Logger => JLogger }
import java.lang.String
import java.sql.SQLData
import java.sql.SQLInput
import java.sql.SQLOutput

/**
 * Implementation of `JdbcSQLData` that wraps a `java.sql.SQLData` and lifts its operations
 * into blocking operations on `RTS[F]`, logged at `TRACE` level on `log`.
 */
@SuppressWarnings(Array("org.wartremover.warts.Overloading"))
class AsyncSQLData[F[_]: Sync](val value: SQLData, val rts: RTS[F]) extends JdbcSQLData[F] {

  val getSQLTypeName: F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getSQLTypeName()")
      value.getSQLTypeName()
    }

  def readSQL(a: SQLInput, b: String): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"readSQL($a, $b)")
      value.readSQL(a, b)
    }

  def writeSQL(a: SQLOutput): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeSQL($a)")
      value.writeSQL(a)
    }

}

