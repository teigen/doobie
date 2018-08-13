// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless.async

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
class AsyncSQLInput[F[_]](val value: SQLInput, rts: RTS[F], log: Logger[F]) extends JdbcSQLInput[F] {

  val id: String =
    s"${System.identityHashCode(value).toHexString.padTo(8, ' ')} SQLInput".padTo(28, ' ')

  private val jlog: JLogger =
    log.underlying

  val readArray: F[SqlArray] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readArray()")
      value.readArray()
    }

  val readAsciiStream: F[InputStream] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readAsciiStream()")
      value.readAsciiStream()
    }

  val readBigDecimal: F[BigDecimal] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readBigDecimal()")
      value.readBigDecimal()
    }

  val readBinaryStream: F[InputStream] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readBinaryStream()")
      value.readBinaryStream()
    }

  val readBlob: F[Blob] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readBlob()")
      value.readBlob()
    }

  val readBoolean: F[Boolean] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readBoolean()")
      value.readBoolean()
    }

  val readByte: F[Byte] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readByte()")
      value.readByte()
    }

  val readBytes: F[Array[Byte]] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readBytes()")
      value.readBytes()
    }

  val readCharacterStream: F[Reader] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readCharacterStream()")
      value.readCharacterStream()
    }

  val readClob: F[Clob] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readClob()")
      value.readClob()
    }

  val readDate: F[Date] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readDate()")
      value.readDate()
    }

  val readDouble: F[Double] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readDouble()")
      value.readDouble()
    }

  val readFloat: F[Float] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readFloat()")
      value.readFloat()
    }

  val readInt: F[Int] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readInt()")
      value.readInt()
    }

  val readLong: F[Long] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readLong()")
      value.readLong()
    }

  val readNClob: F[NClob] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readNClob()")
      value.readNClob()
    }

  val readNString: F[String] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readNString()")
      value.readNString()
    }

  val readObject: F[AnyRef] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readObject()")
      value.readObject()
    }

  def readObject[T](a: Class[T]): F[T] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readObject($a)")
      value.readObject(a)
    }

  val readRef: F[Ref] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readRef()")
      value.readRef()
    }

  val readRowId: F[RowId] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readRowId()")
      value.readRowId()
    }

  val readSQLXML: F[SQLXML] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readSQLXML()")
      value.readSQLXML()
    }

  val readShort: F[Short] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readShort()")
      value.readShort()
    }

  val readString: F[String] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readString()")
      value.readString()
    }

  val readTime: F[Time] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readTime()")
      value.readTime()
    }

  val readTimestamp: F[Timestamp] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readTimestamp()")
      value.readTimestamp()
    }

  val readURL: F[URL] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id readURL()")
      value.readURL()
    }

  val wasNull: F[Boolean] =
    rts.newBlockingPrimitive {
      if (jlog.isTraceEnabled)
        jlog.trace(s"$id wasNull()")
      value.wasNull()
    }

}

