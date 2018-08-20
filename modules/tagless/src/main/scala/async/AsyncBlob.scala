// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless.async

import doobie.tagless.{ RTS, Logger }
import doobie.tagless.jdbc._
import org.slf4j.{ Logger => JLogger }
import java.io.InputStream
import java.io.OutputStream
import java.sql.Blob

/**
 * Implementation of `JdbcBlob` that wraps a `java.sql.Blob` and lifts its operations
 * into blocking operations on `RTS[F]`, logged at `TRACE` level on `log`.
 */
@SuppressWarnings(Array("org.wartremover.warts.Overloading"))
class AsyncBlob[F[_]](val value: Blob, val rts: RTS[F], val log: Logger[F]) extends JdbcBlob[F] {

  val id: String =
    s"${System.identityHashCode(value).toHexString.padTo(8, ' ')} Blob".padTo(28, ' ')

  private val jlog: JLogger =
    log.underlying

  val free: F[Unit] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id free()")
      value.free()
    }

  val getBinaryStream: F[InputStream] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id getBinaryStream()")
      value.getBinaryStream()
    }

  def getBinaryStream(a: Long, b: Long): F[InputStream] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id getBinaryStream($a, $b)")
      value.getBinaryStream(a, b)
    }

  def getBytes(a: Long, b: Int): F[Array[Byte]] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id getBytes($a, $b)")
      value.getBytes(a, b)
    }

  val length: F[Long] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id length()")
      value.length()
    }

  def position(a: Array[Byte], b: Long): F[Long] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id position($a, $b)")
      value.position(a, b)
    }

  def position(a: Blob, b: Long): F[Long] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id position($a, $b)")
      value.position(a, b)
    }

  def setBinaryStream(a: Long): F[OutputStream] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id setBinaryStream($a)")
      value.setBinaryStream(a)
    }

  def setBytes(a: Long, b: Array[Byte]): F[Int] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id setBytes($a, $b)")
      value.setBytes(a, b)
    }

  def setBytes(a: Long, b: Array[Byte], c: Int, d: Int): F[Int] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id setBytes($a, $b, $c, $d)")
      value.setBytes(a, b, c, d)
    }

  def truncate(a: Long): F[Unit] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id truncate($a)")
      value.truncate(a)
    }

}

