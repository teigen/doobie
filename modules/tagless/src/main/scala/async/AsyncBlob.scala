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
import java.sql.Blob

/**
 * Implementation of `JdbcBlob` that wraps a `java.sql.Blob` and lifts its operations
 * into blocking operations on `RTS[F]`, logged at `TRACE` level on `log`.
 */
@SuppressWarnings(Array("org.wartremover.warts.Overloading"))
class AsyncBlob[F[_]: Sync](val value: Blob, val rts: RTS[F]) extends JdbcBlob[F] {

  val free: F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "free()")
      value.free()
    }

  val getBinaryStream: F[InputStream] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getBinaryStream()")
      value.getBinaryStream()
    }

  def getBinaryStream(a: Long, b: Long): F[InputStream] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getBinaryStream($a, $b)")
      value.getBinaryStream(a, b)
    }

  def getBytes(a: Long, b: Int): F[Array[Byte]] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getBytes($a, $b)")
      value.getBytes(a, b)
    }

  val length: F[Long] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "length()")
      value.length()
    }

  def position(a: Array[Byte], b: Long): F[Long] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"position($a, $b)")
      value.position(a, b)
    }

  def position(a: Blob, b: Long): F[Long] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"position($a, $b)")
      value.position(a, b)
    }

  def setBinaryStream(a: Long): F[OutputStream] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setBinaryStream($a)")
      value.setBinaryStream(a)
    }

  def setBytes(a: Long, b: Array[Byte]): F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setBytes($a, $b)")
      value.setBytes(a, b)
    }

  def setBytes(a: Long, b: Array[Byte], c: Int, d: Int): F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setBytes($a, $b, $c, $d)")
      value.setBytes(a, b, c, d)
    }

  def truncate(a: Long): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"truncate($a)")
      value.truncate(a)
    }

}

