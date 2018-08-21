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
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLWarning
import java.sql.Statement

/**
 * Implementation of `JdbcStatement` that wraps a `java.sql.Statement` and lifts its operations
 * into blocking operations on `RTS[F]`, logged at `TRACE` level on `log`.
 */
@SuppressWarnings(Array("org.wartremover.warts.Overloading"))
class AsyncStatement[F[_]: Sync](val value: Statement, val rts: RTS[F]) extends JdbcStatement[F] {

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

  def executeQuery(a: String): F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"executeQuery($a)")
      value.executeQuery(a)
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

  def setCursorName(a: String): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setCursorName($a)")
      value.setCursorName(a)
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

  def setLargeMaxRows(a: Long): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setLargeMaxRows($a)")
      value.setLargeMaxRows(a)
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

  def unwrap[T](a: Class[T]): F[T] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"unwrap($a)")
      value.unwrap(a)
    }

}

