// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless.async

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
class AsyncDriver[F[_]](val value: Driver, rts: RTS[F], log: Logger[F]) extends JdbcDriver[F] {

  val id: String =
    s"${System.identityHashCode(value).toHexString.padTo(8, ' ')} Driver".padTo(28, ' ')

  private val jlog: JLogger =
    log.underlying

  def acceptsURL(a: String): F[Boolean] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id acceptsURL($a)")
      value.acceptsURL(a)
    }

  def connect(a: String, b: Properties): F[Connection] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id connect($a, $b)")
      value.connect(a, b)
    }

  val getMajorVersion: F[Int] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id getMajorVersion()")
      value.getMajorVersion()
    }

  val getMinorVersion: F[Int] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id getMinorVersion()")
      value.getMinorVersion()
    }

  val getParentLogger: F[JdkLogger] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id getParentLogger()")
      value.getParentLogger()
    }

  def getPropertyInfo(a: String, b: Properties): F[Array[DriverPropertyInfo]] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id getPropertyInfo($a, $b)")
      value.getPropertyInfo(a, b)
    }

  val jdbcCompliant: F[Boolean] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id jdbcCompliant()")
      value.jdbcCompliant()
    }

}

