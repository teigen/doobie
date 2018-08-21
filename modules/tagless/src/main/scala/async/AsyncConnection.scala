// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless.async

import cats.effect.Sync
import doobie.tagless.{ RTS, Logger }
import doobie.tagless.jdbc._
import org.slf4j.{ Logger => JLogger }
import java.lang.Class
import java.lang.String
import java.sql.Blob
import java.sql.CallableStatement
import java.sql.Clob
import java.sql.Connection
import java.sql.DatabaseMetaData
import java.sql.NClob
import java.sql.PreparedStatement
import java.sql.SQLWarning
import java.sql.SQLXML
import java.sql.Savepoint
import java.sql.Statement
import java.sql.Struct
import java.sql.{ Array => SqlArray }
import java.util.Map
import java.util.Properties
import java.util.concurrent.Executor

/**
 * Implementation of `JdbcConnection` that wraps a `java.sql.Connection` and lifts its operations
 * into blocking operations on `RTS[F]`, logged at `TRACE` level on `log`.
 */
@SuppressWarnings(Array("org.wartremover.warts.Overloading"))
class AsyncConnection[F[_]: Sync](val value: Connection, val rts: RTS[F]) extends JdbcConnection[F] {

  def abort(a: Executor): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"abort($a)")
      value.abort(a)
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

  val commit: F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "commit()")
      value.commit()
    }

  def createArrayOf(a: String, b: Array[AnyRef]): F[SqlArray] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"createArrayOf($a, $b)")
      value.createArrayOf(a, b)
    }

  val createBlob: F[Blob] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "createBlob()")
      value.createBlob()
    }

  val createClob: F[Clob] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "createClob()")
      value.createClob()
    }

  val createNClob: F[NClob] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "createNClob()")
      value.createNClob()
    }

  val createSQLXML: F[SQLXML] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "createSQLXML()")
      value.createSQLXML()
    }

  val createStatement: F[Statement] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "createStatement()")
      value.createStatement()
    }

  def createStatement(a: Int, b: Int): F[Statement] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"createStatement($a, $b)")
      value.createStatement(a, b)
    }

  def createStatement(a: Int, b: Int, c: Int): F[Statement] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"createStatement($a, $b, $c)")
      value.createStatement(a, b, c)
    }

  def createStruct(a: String, b: Array[AnyRef]): F[Struct] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"createStruct($a, $b)")
      value.createStruct(a, b)
    }

  val getAutoCommit: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getAutoCommit()")
      value.getAutoCommit()
    }

  val getCatalog: F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getCatalog()")
      value.getCatalog()
    }

  val getClientInfo: F[Properties] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getClientInfo()")
      value.getClientInfo()
    }

  def getClientInfo(a: String): F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getClientInfo($a)")
      value.getClientInfo(a)
    }

  val getHoldability: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getHoldability()")
      value.getHoldability()
    }

  val getMetaData: F[DatabaseMetaData] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMetaData()")
      value.getMetaData()
    }

  val getNetworkTimeout: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getNetworkTimeout()")
      value.getNetworkTimeout()
    }

  val getSchema: F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getSchema()")
      value.getSchema()
    }

  val getTransactionIsolation: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getTransactionIsolation()")
      value.getTransactionIsolation()
    }

  val getTypeMap: F[Map[String, Class[_]]] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getTypeMap()")
      value.getTypeMap()
    }

  val getWarnings: F[SQLWarning] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getWarnings()")
      value.getWarnings()
    }

  val isClosed: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "isClosed()")
      value.isClosed()
    }

  val isReadOnly: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "isReadOnly()")
      value.isReadOnly()
    }

  def isValid(a: Int): F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"isValid($a)")
      value.isValid(a)
    }

  def isWrapperFor(a: Class[_]): F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"isWrapperFor($a)")
      value.isWrapperFor(a)
    }

  def nativeSQL(a: String): F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"nativeSQL($a)")
      value.nativeSQL(a)
    }

  def prepareCall(a: String): F[CallableStatement] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"prepareCall($a)")
      value.prepareCall(a)
    }

  def prepareCall(a: String, b: Int, c: Int): F[CallableStatement] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"prepareCall($a, $b, $c)")
      value.prepareCall(a, b, c)
    }

  def prepareCall(a: String, b: Int, c: Int, d: Int): F[CallableStatement] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"prepareCall($a, $b, $c, $d)")
      value.prepareCall(a, b, c, d)
    }

  def prepareStatement(a: String): F[PreparedStatement] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"prepareStatement($a)")
      value.prepareStatement(a)
    }

  def prepareStatement(a: String, b: Array[Int]): F[PreparedStatement] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"prepareStatement($a, $b)")
      value.prepareStatement(a, b)
    }

  def prepareStatement(a: String, b: Array[String]): F[PreparedStatement] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"prepareStatement($a, $b)")
      value.prepareStatement(a, b)
    }

  def prepareStatement(a: String, b: Int): F[PreparedStatement] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"prepareStatement($a, $b)")
      value.prepareStatement(a, b)
    }

  def prepareStatement(a: String, b: Int, c: Int): F[PreparedStatement] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"prepareStatement($a, $b, $c)")
      value.prepareStatement(a, b, c)
    }

  def prepareStatement(a: String, b: Int, c: Int, d: Int): F[PreparedStatement] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"prepareStatement($a, $b, $c, $d)")
      value.prepareStatement(a, b, c, d)
    }

  def releaseSavepoint(a: Savepoint): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"releaseSavepoint($a)")
      value.releaseSavepoint(a)
    }

  val rollback: F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "rollback()")
      value.rollback()
    }

  def rollback(a: Savepoint): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"rollback($a)")
      value.rollback(a)
    }

  def setAutoCommit(a: Boolean): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setAutoCommit($a)")
      value.setAutoCommit(a)
    }

  def setCatalog(a: String): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setCatalog($a)")
      value.setCatalog(a)
    }

  def setClientInfo(a: Properties): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setClientInfo($a)")
      value.setClientInfo(a)
    }

  def setClientInfo(a: String, b: String): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setClientInfo($a, $b)")
      value.setClientInfo(a, b)
    }

  def setHoldability(a: Int): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setHoldability($a)")
      value.setHoldability(a)
    }

  def setNetworkTimeout(a: Executor, b: Int): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setNetworkTimeout($a, $b)")
      value.setNetworkTimeout(a, b)
    }

  def setReadOnly(a: Boolean): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setReadOnly($a)")
      value.setReadOnly(a)
    }

  val setSavepoint: F[Savepoint] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "setSavepoint()")
      value.setSavepoint()
    }

  def setSavepoint(a: String): F[Savepoint] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setSavepoint($a)")
      value.setSavepoint(a)
    }

  def setSchema(a: String): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setSchema($a)")
      value.setSchema(a)
    }

  def setTransactionIsolation(a: Int): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setTransactionIsolation($a)")
      value.setTransactionIsolation(a)
    }

  def setTypeMap(a: Map[String, Class[_]]): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setTypeMap($a)")
      value.setTypeMap(a)
    }

  def unwrap[T](a: Class[T]): F[T] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"unwrap($a)")
      value.unwrap(a)
    }

}

