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
import java.lang.Class
import java.lang.String
import java.math.BigDecimal
import java.net.URL
import java.sql.Blob
import java.sql.Clob
import java.sql.Date
import java.sql.NClob
import java.sql.Ref
import java.sql.RowId
import java.sql.SQLInput
import java.sql.SQLXML
import java.sql.Time
import java.sql.Timestamp
import java.sql.{ Array => SqlArray }

/**
 * Implementation of `JdbcSQLInput` that wraps a `java.sql.SQLInput` and lifts its operations
 * into blocking operations on `RTS[F]`, logged at `TRACE` level on `log`.
 */
@SuppressWarnings(Array("org.wartremover.warts.Overloading"))
class AsyncSQLInput[F[_]: Sync](val value: SQLInput, val rts: RTS[F]) extends JdbcSQLInput[F] {

  val readArray: F[SqlArray] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "readArray()")
      value.readArray()
    }

  val readAsciiStream: F[InputStream] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "readAsciiStream()")
      value.readAsciiStream()
    }

  val readBigDecimal: F[BigDecimal] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "readBigDecimal()")
      value.readBigDecimal()
    }

  val readBinaryStream: F[InputStream] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "readBinaryStream()")
      value.readBinaryStream()
    }

  val readBlob: F[Blob] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "readBlob()")
      value.readBlob()
    }

  val readBoolean: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "readBoolean()")
      value.readBoolean()
    }

  val readByte: F[Byte] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "readByte()")
      value.readByte()
    }

  val readBytes: F[Array[Byte]] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "readBytes()")
      value.readBytes()
    }

  val readCharacterStream: F[Reader] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "readCharacterStream()")
      value.readCharacterStream()
    }

  val readClob: F[Clob] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "readClob()")
      value.readClob()
    }

  val readDate: F[Date] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "readDate()")
      value.readDate()
    }

  val readDouble: F[Double] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "readDouble()")
      value.readDouble()
    }

  val readFloat: F[Float] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "readFloat()")
      value.readFloat()
    }

  val readInt: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "readInt()")
      value.readInt()
    }

  val readLong: F[Long] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "readLong()")
      value.readLong()
    }

  val readNClob: F[NClob] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "readNClob()")
      value.readNClob()
    }

  val readNString: F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "readNString()")
      value.readNString()
    }

  val readObject: F[AnyRef] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "readObject()")
      value.readObject()
    }

  def readObject[T](a: Class[T]): F[T] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"readObject($a)")
      value.readObject(a)
    }

  val readRef: F[Ref] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "readRef()")
      value.readRef()
    }

  val readRowId: F[RowId] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "readRowId()")
      value.readRowId()
    }

  val readSQLXML: F[SQLXML] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "readSQLXML()")
      value.readSQLXML()
    }

  val readShort: F[Short] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "readShort()")
      value.readShort()
    }

  val readString: F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "readString()")
      value.readString()
    }

  val readTime: F[Time] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "readTime()")
      value.readTime()
    }

  val readTimestamp: F[Timestamp] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "readTimestamp()")
      value.readTimestamp()
    }

  val readURL: F[URL] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "readURL()")
      value.readURL()
    }

  val wasNull: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "wasNull()")
      value.wasNull()
    }

}

