// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless.async

import cats.effect.Sync
import doobie.tagless.{ RTS, Logger }
import doobie.tagless.jdbc._
import org.slf4j.{ Logger => JLogger }
import java.io.InputStream
import java.io.OutputStream
import java.io.Reader
import java.io.Writer
import java.lang.String
import java.sql.Clob

/**
 * Implementation of `JdbcClob` that wraps a `java.sql.Clob` and lifts its operations
 * into blocking operations on `RTS[F]`, logged at `TRACE` level on `log`.
 */
@SuppressWarnings(Array("org.wartremover.warts.Overloading"))
class AsyncClob[F[_]: Sync](val value: Clob, val rts: RTS[F]) extends JdbcClob[F] {

  val free: F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "free()")
      value.free()
    }

  val getAsciiStream: F[InputStream] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getAsciiStream()")
      value.getAsciiStream()
    }

  val getCharacterStream: F[Reader] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getCharacterStream()")
      value.getCharacterStream()
    }

  def getCharacterStream(a: Long, b: Long): F[Reader] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getCharacterStream($a, $b)")
      value.getCharacterStream(a, b)
    }

  def getSubString(a: Long, b: Int): F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getSubString($a, $b)")
      value.getSubString(a, b)
    }

  val length: F[Long] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "length()")
      value.length()
    }

  def position(a: Clob, b: Long): F[Long] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"position($a, $b)")
      value.position(a, b)
    }

  def position(a: String, b: Long): F[Long] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"position($a, $b)")
      value.position(a, b)
    }

  def setAsciiStream(a: Long): F[OutputStream] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setAsciiStream($a)")
      value.setAsciiStream(a)
    }

  def setCharacterStream(a: Long): F[Writer] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setCharacterStream($a)")
      value.setCharacterStream(a)
    }

  def setString(a: Long, b: String): F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setString($a, $b)")
      value.setString(a, b)
    }

  def setString(a: Long, b: String, c: Int, d: Int): F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setString($a, $b, $c, $d)")
      value.setString(a, b, c, d)
    }

  def truncate(a: Long): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"truncate($a)")
      value.truncate(a)
    }

}

