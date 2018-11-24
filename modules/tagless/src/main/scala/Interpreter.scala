// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless

import cats.effect.Sync
import doobie.tagless.async._
import doobie.tagless.jdbc._

trait Interpreter[F[_]] {

  /** The runtime system used for managing blocking operations, provided by `async.rts`. */
  def rts: RTS[F]

  def forNClob(a: java.sql.NClob): NClob[F]
  def forBlob(a: java.sql.Blob): Blob[F]
  def forClob(a: java.sql.Clob): Clob[F]
  def forDatabaseMetaData(a: java.sql.DatabaseMetaData): DatabaseMetaData[F]
  def forDriver(a: java.sql.Driver): Driver[F]
  def forRef(a: java.sql.Ref): Ref[F]
  def forSQLData(a: java.sql.SQLData): SQLData[F]
  def forSQLInput(a: java.sql.SQLInput): SQLInput[F]
  def forSQLOutput(a: java.sql.SQLOutput): SQLOutput[F]
  def forConnection(a: java.sql.Connection): Connection[F]
  def forStatement(a: java.sql.Statement): Statement[F]
  def forPreparedStatement(a: java.sql.PreparedStatement): PreparedStatement[F]
  def forCallableStatement(a: java.sql.CallableStatement): CallableStatement[F]
  def forResultSet(a: java.sql.ResultSet): ResultSet[F]

}


@SuppressWarnings(Array("org.wartremover.warts.Overloading"))
object Interpreter {

  def async[F[_]: Sync](async: AsyncInterpreter[F]): Interpreter[F] =
    new Interpreter[F] {

      /** The runtime system used for managing blocking operations, provided by `async.rts`. */
      def rts: RTS[F] = async.rts

      def forNClob(a: java.sql.NClob): NClob[F] =
        NClob(async.forNClob(a), this)

      def forBlob(a: java.sql.Blob): Blob[F] =
        Blob(async.forBlob(a), this)

      def forClob(a: java.sql.Clob): Clob[F] =
        Clob(async.forClob(a), this)

      def forDatabaseMetaData(a: java.sql.DatabaseMetaData): DatabaseMetaData[F] =
        DatabaseMetaData(async.forDatabaseMetaData(a), this)

      def forDriver(a: java.sql.Driver): Driver[F] =
        Driver(async.forDriver(a), this)

      def forRef(a: java.sql.Ref): Ref[F] =
        Ref(async.forRef(a), this)

      def forSQLData(a: java.sql.SQLData): SQLData[F] =
        SQLData(async.forSQLData(a), this)

      def forSQLInput(a: java.sql.SQLInput): SQLInput[F] =
        SQLInput(async.forSQLInput(a), this)

      def forSQLOutput(a: java.sql.SQLOutput): SQLOutput[F] =
        SQLOutput(async.forSQLOutput(a), this)

      def forConnection(a: java.sql.Connection): Connection[F] =
        Connection.async(async.forConnection(a), this)

      def forStatement(a: java.sql.Statement): Statement[F] =
        Statement(async.forStatement(a), this)

      def forPreparedStatement(a: java.sql.PreparedStatement): PreparedStatement[F] =
        PreparedStatement.async(async.forPreparedStatement(a), this)

      def forCallableStatement(a: java.sql.CallableStatement): CallableStatement[F] =
        CallableStatement(async.forCallableStatement(a), this)

      def forResultSet(a: java.sql.ResultSet): ResultSet[F] =
        ResultSet.async(async.forResultSet(a), this)

    }

  def default[F[_]: Sync](implicit rts: RTS[F]): Interpreter[F] =
    async(new AsyncInterpreter[F](rts))

}

// Unimplemented
final case class NClob[F[_]](async: AsyncNClob[F], interp: Interpreter[F])
final case class Blob[F[_]](async: AsyncBlob[F], interp: Interpreter[F])
final case class Clob[F[_]](async: AsyncClob[F], interp: Interpreter[F])
final case class DatabaseMetaData[F[_]](async: AsyncDatabaseMetaData[F], interp: Interpreter[F])
final case class Driver[F[_]](async: AsyncDriver[F], interp: Interpreter[F])
final case class Ref[F[_]](async: AsyncRef[F], interp: Interpreter[F])
final case class SQLData[F[_]](async: AsyncSQLData[F], interp: Interpreter[F])
final case class SQLInput[F[_]](async: AsyncSQLInput[F], interp: Interpreter[F])
final case class SQLOutput[F[_]](async: AsyncSQLOutput[F], interp: Interpreter[F])
final case class Statement[F[_]](async: AsyncStatement[F], interp: Interpreter[F])
final case class CallableStatement[F[_]](async: AsyncCallableStatement[F], interp: Interpreter[F])
