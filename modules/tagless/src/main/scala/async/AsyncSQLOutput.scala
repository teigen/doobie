// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless.async

import cats.effect.Sync
import doobie.tagless.{ RTS, Logger }
import doobie.tagless.jdbc._
import org.slf4j.{ Logger => JLogger }
import java.io.InputStream
import java.io.Reader
import java.lang.String
import java.math.BigDecimal
import java.net.URL
import java.sql.Blob
import java.sql.Clob
import java.sql.Date
import java.sql.NClob
import java.sql.Ref
import java.sql.RowId
import java.sql.SQLData
import java.sql.SQLOutput
import java.sql.SQLType
import java.sql.SQLXML
import java.sql.Struct
import java.sql.Time
import java.sql.Timestamp
import java.sql.{ Array => SqlArray }

/**
 * Implementation of `JdbcSQLOutput` that wraps a `java.sql.SQLOutput` and lifts its operations
 * into blocking operations on `RTS[F]`, logged at `TRACE` level on `log`.
 */
@SuppressWarnings(Array("org.wartremover.warts.Overloading"))
class AsyncSQLOutput[F[_]: Sync](val value: SQLOutput, val rts: RTS[F]) extends JdbcSQLOutput[F] {

  def writeArray(a: SqlArray): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeArray($a)")
      value.writeArray(a)
    }

  def writeAsciiStream(a: InputStream): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeAsciiStream($a)")
      value.writeAsciiStream(a)
    }

  def writeBigDecimal(a: BigDecimal): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeBigDecimal($a)")
      value.writeBigDecimal(a)
    }

  def writeBinaryStream(a: InputStream): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeBinaryStream($a)")
      value.writeBinaryStream(a)
    }

  def writeBlob(a: Blob): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeBlob($a)")
      value.writeBlob(a)
    }

  def writeBoolean(a: Boolean): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeBoolean($a)")
      value.writeBoolean(a)
    }

  def writeByte(a: Byte): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeByte($a)")
      value.writeByte(a)
    }

  def writeBytes(a: Array[Byte]): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeBytes($a)")
      value.writeBytes(a)
    }

  def writeCharacterStream(a: Reader): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeCharacterStream($a)")
      value.writeCharacterStream(a)
    }

  def writeClob(a: Clob): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeClob($a)")
      value.writeClob(a)
    }

  def writeDate(a: Date): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeDate($a)")
      value.writeDate(a)
    }

  def writeDouble(a: Double): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeDouble($a)")
      value.writeDouble(a)
    }

  def writeFloat(a: Float): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeFloat($a)")
      value.writeFloat(a)
    }

  def writeInt(a: Int): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeInt($a)")
      value.writeInt(a)
    }

  def writeLong(a: Long): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeLong($a)")
      value.writeLong(a)
    }

  def writeNClob(a: NClob): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeNClob($a)")
      value.writeNClob(a)
    }

  def writeNString(a: String): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeNString($a)")
      value.writeNString(a)
    }

  def writeObject(a: AnyRef, b: SQLType): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeObject($a, $b)")
      value.writeObject(a, b)
    }

  def writeObject(a: SQLData): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeObject($a)")
      value.writeObject(a)
    }

  def writeRef(a: Ref): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeRef($a)")
      value.writeRef(a)
    }

  def writeRowId(a: RowId): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeRowId($a)")
      value.writeRowId(a)
    }

  def writeSQLXML(a: SQLXML): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeSQLXML($a)")
      value.writeSQLXML(a)
    }

  def writeShort(a: Short): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeShort($a)")
      value.writeShort(a)
    }

  def writeString(a: String): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeString($a)")
      value.writeString(a)
    }

  def writeStruct(a: Struct): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeStruct($a)")
      value.writeStruct(a)
    }

  def writeTime(a: Time): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeTime($a)")
      value.writeTime(a)
    }

  def writeTimestamp(a: Timestamp): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeTimestamp($a)")
      value.writeTimestamp(a)
    }

  def writeURL(a: URL): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"writeURL($a)")
      value.writeURL(a)
    }

}

