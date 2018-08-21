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
import java.sql.Connection
import java.sql.Date
import java.sql.NClob
import java.sql.ParameterMetaData
import java.sql.PreparedStatement
import java.sql.Ref
import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.RowId
import java.sql.SQLType
import java.sql.SQLWarning
import java.sql.SQLXML
import java.sql.Time
import java.sql.Timestamp
import java.sql.{ Array => SqlArray }
import java.util.Calendar

/**
 * Implementation of `JdbcPreparedStatement` that wraps a `java.sql.PreparedStatement` and lifts its operations
 * into blocking operations on `RTS[F]`, logged at `TRACE` level on `log`.
 */
@SuppressWarnings(Array("org.wartremover.warts.Overloading"))
class AsyncPreparedStatement[F[_]: Sync](val value: PreparedStatement, val rts: RTS[F]) extends JdbcPreparedStatement[F] {

  val addBatch: F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "addBatch()")
      value.addBatch()
    }

  def addBatch(a: String): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"addBatch($a)")
      value.addBatch(a)
    }

  val cancel: F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "cancel()")
      value.cancel()
    }

  val clearBatch: F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "clearBatch()")
      value.clearBatch()
    }

  val clearParameters: F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "clearParameters()")
      value.clearParameters()
    }

  val clearWarnings: F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "clearWarnings()")
      value.clearWarnings()
    }

  val close: F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "close()")
      value.close()
    }

  val closeOnCompletion: F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "closeOnCompletion()")
      value.closeOnCompletion()
    }

  val execute: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "execute()")
      value.execute()
    }

  def execute(a: String): F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"execute($a)")
      value.execute(a)
    }

  def execute(a: String, b: Array[Int]): F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"execute($a, $b)")
      value.execute(a, b)
    }

  def execute(a: String, b: Array[String]): F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"execute($a, $b)")
      value.execute(a, b)
    }

  def execute(a: String, b: Int): F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"execute($a, $b)")
      value.execute(a, b)
    }

  val executeBatch: F[Array[Int]] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "executeBatch()")
      value.executeBatch()
    }

  val executeLargeBatch: F[Array[Long]] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "executeLargeBatch()")
      value.executeLargeBatch()
    }

  val executeLargeUpdate: F[Long] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "executeLargeUpdate()")
      value.executeLargeUpdate()
    }

  def executeLargeUpdate(a: String): F[Long] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"executeLargeUpdate($a)")
      value.executeLargeUpdate(a)
    }

  def executeLargeUpdate(a: String, b: Array[Int]): F[Long] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"executeLargeUpdate($a, $b)")
      value.executeLargeUpdate(a, b)
    }

  def executeLargeUpdate(a: String, b: Array[String]): F[Long] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"executeLargeUpdate($a, $b)")
      value.executeLargeUpdate(a, b)
    }

  def executeLargeUpdate(a: String, b: Int): F[Long] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"executeLargeUpdate($a, $b)")
      value.executeLargeUpdate(a, b)
    }

  val executeQuery: F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "executeQuery()")
      value.executeQuery()
    }

  def executeQuery(a: String): F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"executeQuery($a)")
      value.executeQuery(a)
    }

  val executeUpdate: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "executeUpdate()")
      value.executeUpdate()
    }

  def executeUpdate(a: String): F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"executeUpdate($a)")
      value.executeUpdate(a)
    }

  def executeUpdate(a: String, b: Array[Int]): F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"executeUpdate($a, $b)")
      value.executeUpdate(a, b)
    }

  def executeUpdate(a: String, b: Array[String]): F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"executeUpdate($a, $b)")
      value.executeUpdate(a, b)
    }

  def executeUpdate(a: String, b: Int): F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"executeUpdate($a, $b)")
      value.executeUpdate(a, b)
    }

  val getConnection: F[Connection] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getConnection()")
      value.getConnection()
    }

  val getFetchDirection: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getFetchDirection()")
      value.getFetchDirection()
    }

  val getFetchSize: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getFetchSize()")
      value.getFetchSize()
    }

  val getGeneratedKeys: F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getGeneratedKeys()")
      value.getGeneratedKeys()
    }

  val getLargeMaxRows: F[Long] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getLargeMaxRows()")
      value.getLargeMaxRows()
    }

  val getLargeUpdateCount: F[Long] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getLargeUpdateCount()")
      value.getLargeUpdateCount()
    }

  val getMaxFieldSize: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMaxFieldSize()")
      value.getMaxFieldSize()
    }

  val getMaxRows: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMaxRows()")
      value.getMaxRows()
    }

  val getMetaData: F[ResultSetMetaData] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMetaData()")
      value.getMetaData()
    }

  val getMoreResults: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMoreResults()")
      value.getMoreResults()
    }

  def getMoreResults(a: Int): F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getMoreResults($a)")
      value.getMoreResults(a)
    }

  val getParameterMetaData: F[ParameterMetaData] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getParameterMetaData()")
      value.getParameterMetaData()
    }

  val getQueryTimeout: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getQueryTimeout()")
      value.getQueryTimeout()
    }

  val getResultSet: F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getResultSet()")
      value.getResultSet()
    }

  val getResultSetConcurrency: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getResultSetConcurrency()")
      value.getResultSetConcurrency()
    }

  val getResultSetHoldability: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getResultSetHoldability()")
      value.getResultSetHoldability()
    }

  val getResultSetType: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getResultSetType()")
      value.getResultSetType()
    }

  val getUpdateCount: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getUpdateCount()")
      value.getUpdateCount()
    }

  val getWarnings: F[SQLWarning] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getWarnings()")
      value.getWarnings()
    }

  val isCloseOnCompletion: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "isCloseOnCompletion()")
      value.isCloseOnCompletion()
    }

  val isClosed: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "isClosed()")
      value.isClosed()
    }

  val isPoolable: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "isPoolable()")
      value.isPoolable()
    }

  def isWrapperFor(a: Class[_]): F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"isWrapperFor($a)")
      value.isWrapperFor(a)
    }

  def setArray(a: Int, b: SqlArray): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setArray($a, $b)")
      value.setArray(a, b)
    }

  def setAsciiStream(a: Int, b: InputStream): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setAsciiStream($a, $b)")
      value.setAsciiStream(a, b)
    }

  def setAsciiStream(a: Int, b: InputStream, c: Int): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setAsciiStream($a, $b, $c)")
      value.setAsciiStream(a, b, c)
    }

  def setAsciiStream(a: Int, b: InputStream, c: Long): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setAsciiStream($a, $b, $c)")
      value.setAsciiStream(a, b, c)
    }

  def setBigDecimal(a: Int, b: BigDecimal): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setBigDecimal($a, $b)")
      value.setBigDecimal(a, b)
    }

  def setBinaryStream(a: Int, b: InputStream): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setBinaryStream($a, $b)")
      value.setBinaryStream(a, b)
    }

  def setBinaryStream(a: Int, b: InputStream, c: Int): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setBinaryStream($a, $b, $c)")
      value.setBinaryStream(a, b, c)
    }

  def setBinaryStream(a: Int, b: InputStream, c: Long): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setBinaryStream($a, $b, $c)")
      value.setBinaryStream(a, b, c)
    }

  def setBlob(a: Int, b: Blob): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setBlob($a, $b)")
      value.setBlob(a, b)
    }

  def setBlob(a: Int, b: InputStream): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setBlob($a, $b)")
      value.setBlob(a, b)
    }

  def setBlob(a: Int, b: InputStream, c: Long): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setBlob($a, $b, $c)")
      value.setBlob(a, b, c)
    }

  def setBoolean(a: Int, b: Boolean): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setBoolean($a, $b)")
      value.setBoolean(a, b)
    }

  def setByte(a: Int, b: Byte): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setByte($a, $b)")
      value.setByte(a, b)
    }

  def setBytes(a: Int, b: Array[Byte]): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setBytes($a, $b)")
      value.setBytes(a, b)
    }

  def setCharacterStream(a: Int, b: Reader): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setCharacterStream($a, $b)")
      value.setCharacterStream(a, b)
    }

  def setCharacterStream(a: Int, b: Reader, c: Int): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setCharacterStream($a, $b, $c)")
      value.setCharacterStream(a, b, c)
    }

  def setCharacterStream(a: Int, b: Reader, c: Long): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setCharacterStream($a, $b, $c)")
      value.setCharacterStream(a, b, c)
    }

  def setClob(a: Int, b: Clob): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setClob($a, $b)")
      value.setClob(a, b)
    }

  def setClob(a: Int, b: Reader): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setClob($a, $b)")
      value.setClob(a, b)
    }

  def setClob(a: Int, b: Reader, c: Long): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setClob($a, $b, $c)")
      value.setClob(a, b, c)
    }

  def setCursorName(a: String): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setCursorName($a)")
      value.setCursorName(a)
    }

  def setDate(a: Int, b: Date): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setDate($a, $b)")
      value.setDate(a, b)
    }

  def setDate(a: Int, b: Date, c: Calendar): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setDate($a, $b, $c)")
      value.setDate(a, b, c)
    }

  def setDouble(a: Int, b: Double): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setDouble($a, $b)")
      value.setDouble(a, b)
    }

  def setEscapeProcessing(a: Boolean): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setEscapeProcessing($a)")
      value.setEscapeProcessing(a)
    }

  def setFetchDirection(a: Int): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setFetchDirection($a)")
      value.setFetchDirection(a)
    }

  def setFetchSize(a: Int): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setFetchSize($a)")
      value.setFetchSize(a)
    }

  def setFloat(a: Int, b: Float): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setFloat($a, $b)")
      value.setFloat(a, b)
    }

  def setInt(a: Int, b: Int): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setInt($a, $b)")
      value.setInt(a, b)
    }

  def setLargeMaxRows(a: Long): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setLargeMaxRows($a)")
      value.setLargeMaxRows(a)
    }

  def setLong(a: Int, b: Long): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setLong($a, $b)")
      value.setLong(a, b)
    }

  def setMaxFieldSize(a: Int): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setMaxFieldSize($a)")
      value.setMaxFieldSize(a)
    }

  def setMaxRows(a: Int): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setMaxRows($a)")
      value.setMaxRows(a)
    }

  def setNCharacterStream(a: Int, b: Reader): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setNCharacterStream($a, $b)")
      value.setNCharacterStream(a, b)
    }

  def setNCharacterStream(a: Int, b: Reader, c: Long): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setNCharacterStream($a, $b, $c)")
      value.setNCharacterStream(a, b, c)
    }

  def setNClob(a: Int, b: NClob): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setNClob($a, $b)")
      value.setNClob(a, b)
    }

  def setNClob(a: Int, b: Reader): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setNClob($a, $b)")
      value.setNClob(a, b)
    }

  def setNClob(a: Int, b: Reader, c: Long): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setNClob($a, $b, $c)")
      value.setNClob(a, b, c)
    }

  def setNString(a: Int, b: String): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setNString($a, $b)")
      value.setNString(a, b)
    }

  def setNull(a: Int, b: Int): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setNull($a, $b)")
      value.setNull(a, b)
    }

  def setNull(a: Int, b: Int, c: String): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setNull($a, $b, $c)")
      value.setNull(a, b, c)
    }

  def setObject(a: Int, b: AnyRef): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setObject($a, $b)")
      value.setObject(a, b)
    }

  def setObject(a: Int, b: AnyRef, c: Int): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setObject($a, $b, $c)")
      value.setObject(a, b, c)
    }

  def setObject(a: Int, b: AnyRef, c: Int, d: Int): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setObject($a, $b, $c, $d)")
      value.setObject(a, b, c, d)
    }

  def setObject(a: Int, b: AnyRef, c: SQLType): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setObject($a, $b, $c)")
      value.setObject(a, b, c)
    }

  def setObject(a: Int, b: AnyRef, c: SQLType, d: Int): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setObject($a, $b, $c, $d)")
      value.setObject(a, b, c, d)
    }

  def setPoolable(a: Boolean): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setPoolable($a)")
      value.setPoolable(a)
    }

  def setQueryTimeout(a: Int): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setQueryTimeout($a)")
      value.setQueryTimeout(a)
    }

  def setRef(a: Int, b: Ref): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setRef($a, $b)")
      value.setRef(a, b)
    }

  def setRowId(a: Int, b: RowId): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setRowId($a, $b)")
      value.setRowId(a, b)
    }

  def setSQLXML(a: Int, b: SQLXML): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setSQLXML($a, $b)")
      value.setSQLXML(a, b)
    }

  def setShort(a: Int, b: Short): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setShort($a, $b)")
      value.setShort(a, b)
    }

  def setString(a: Int, b: String): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setString($a, $b)")
      value.setString(a, b)
    }

  def setTime(a: Int, b: Time): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setTime($a, $b)")
      value.setTime(a, b)
    }

  def setTime(a: Int, b: Time, c: Calendar): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setTime($a, $b, $c)")
      value.setTime(a, b, c)
    }

  def setTimestamp(a: Int, b: Timestamp): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setTimestamp($a, $b)")
      value.setTimestamp(a, b)
    }

  def setTimestamp(a: Int, b: Timestamp, c: Calendar): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setTimestamp($a, $b, $c)")
      value.setTimestamp(a, b, c)
    }

  def setURL(a: Int, b: URL): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setURL($a, $b)")
      value.setURL(a, b)
    }

  def setUnicodeStream(a: Int, b: InputStream, c: Int): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setUnicodeStream($a, $b, $c)")
      value.setUnicodeStream(a, b, c)
    }

  def unwrap[T](a: Class[T]): F[T] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"unwrap($a)")
      value.unwrap(a)
    }

}

