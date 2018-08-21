// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless.async

import cats.effect.Sync
import doobie.tagless.{ RTS, Logger }
import doobie.tagless.jdbc._
import org.slf4j.{ Logger => JLogger }
import java.lang.String
import java.sql.Connection
import java.sql.Driver
import java.sql.DriverPropertyInfo
import java.util.Properties
import java.util.logging.{ Logger => JdkLogger }

/**
 * Implementation of `JdbcDriver` that wraps a `java.sql.Driver` and lifts its operations
 * into blocking operations on `RTS[F]`, logged at `TRACE` level on `log`.
 */
@SuppressWarnings(Array("org.wartremover.warts.Overloading"))
class AsyncDriver[F[_]: Sync](val value: Driver, val rts: RTS[F]) extends JdbcDriver[F] {

  def acceptsURL(a: String): F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"acceptsURL($a)")
      value.acceptsURL(a)
    }

  def connect(a: String, b: Properties): F[Connection] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"connect($a, $b)")
      value.connect(a, b)
    }

  val getMajorVersion: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMajorVersion()")
      value.getMajorVersion()
    }

  val getMinorVersion: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMinorVersion()")
      value.getMinorVersion()
    }

  val getParentLogger: F[JdkLogger] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getParentLogger()")
      value.getParentLogger()
    }

  def getPropertyInfo(a: String, b: Properties): F[Array[DriverPropertyInfo]] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getPropertyInfo($a, $b)")
      value.getPropertyInfo(a, b)
    }

  val jdbcCompliant: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "jdbcCompliant()")
      value.jdbcCompliant()
    }

}

