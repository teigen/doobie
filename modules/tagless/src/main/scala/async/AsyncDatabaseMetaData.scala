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
import java.sql.DatabaseMetaData
import java.sql.ResultSet
import java.sql.RowIdLifetime

/**
 * Implementation of `JdbcDatabaseMetaData` that wraps a `java.sql.DatabaseMetaData` and lifts its operations
 * into blocking operations on `RTS[F]`, logged at `TRACE` level on `log`.
 */
@SuppressWarnings(Array("org.wartremover.warts.Overloading"))
class AsyncDatabaseMetaData[F[_]: Sync](val value: DatabaseMetaData, val rts: RTS[F]) extends JdbcDatabaseMetaData[F] {

  val allProceduresAreCallable: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "allProceduresAreCallable()")
      value.allProceduresAreCallable()
    }

  val allTablesAreSelectable: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "allTablesAreSelectable()")
      value.allTablesAreSelectable()
    }

  val autoCommitFailureClosesAllResultSets: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "autoCommitFailureClosesAllResultSets()")
      value.autoCommitFailureClosesAllResultSets()
    }

  val dataDefinitionCausesTransactionCommit: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "dataDefinitionCausesTransactionCommit()")
      value.dataDefinitionCausesTransactionCommit()
    }

  val dataDefinitionIgnoredInTransactions: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "dataDefinitionIgnoredInTransactions()")
      value.dataDefinitionIgnoredInTransactions()
    }

  def deletesAreDetected(a: Int): F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"deletesAreDetected($a)")
      value.deletesAreDetected(a)
    }

  val doesMaxRowSizeIncludeBlobs: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "doesMaxRowSizeIncludeBlobs()")
      value.doesMaxRowSizeIncludeBlobs()
    }

  val generatedKeyAlwaysReturned: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "generatedKeyAlwaysReturned()")
      value.generatedKeyAlwaysReturned()
    }

  def getAttributes(a: String, b: String, c: String, d: String): F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getAttributes($a, $b, $c, $d)")
      value.getAttributes(a, b, c, d)
    }

  def getBestRowIdentifier(a: String, b: String, c: String, d: Int, e: Boolean): F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getBestRowIdentifier($a, $b, $c, $d, $e)")
      value.getBestRowIdentifier(a, b, c, d, e)
    }

  val getCatalogSeparator: F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getCatalogSeparator()")
      value.getCatalogSeparator()
    }

  val getCatalogTerm: F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getCatalogTerm()")
      value.getCatalogTerm()
    }

  val getCatalogs: F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getCatalogs()")
      value.getCatalogs()
    }

  val getClientInfoProperties: F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getClientInfoProperties()")
      value.getClientInfoProperties()
    }

  def getColumnPrivileges(a: String, b: String, c: String, d: String): F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getColumnPrivileges($a, $b, $c, $d)")
      value.getColumnPrivileges(a, b, c, d)
    }

  def getColumns(a: String, b: String, c: String, d: String): F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getColumns($a, $b, $c, $d)")
      value.getColumns(a, b, c, d)
    }

  val getConnection: F[Connection] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getConnection()")
      value.getConnection()
    }

  def getCrossReference(a: String, b: String, c: String, d: String, e: String, f: String): F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getCrossReference($a, $b, $c, $d, $e, $f)")
      value.getCrossReference(a, b, c, d, e, f)
    }

  val getDatabaseMajorVersion: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getDatabaseMajorVersion()")
      value.getDatabaseMajorVersion()
    }

  val getDatabaseMinorVersion: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getDatabaseMinorVersion()")
      value.getDatabaseMinorVersion()
    }

  val getDatabaseProductName: F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getDatabaseProductName()")
      value.getDatabaseProductName()
    }

  val getDatabaseProductVersion: F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getDatabaseProductVersion()")
      value.getDatabaseProductVersion()
    }

  val getDefaultTransactionIsolation: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getDefaultTransactionIsolation()")
      value.getDefaultTransactionIsolation()
    }

  val getDriverMajorVersion: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getDriverMajorVersion()")
      value.getDriverMajorVersion()
    }

  val getDriverMinorVersion: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getDriverMinorVersion()")
      value.getDriverMinorVersion()
    }

  val getDriverName: F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getDriverName()")
      value.getDriverName()
    }

  val getDriverVersion: F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getDriverVersion()")
      value.getDriverVersion()
    }

  def getExportedKeys(a: String, b: String, c: String): F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getExportedKeys($a, $b, $c)")
      value.getExportedKeys(a, b, c)
    }

  val getExtraNameCharacters: F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getExtraNameCharacters()")
      value.getExtraNameCharacters()
    }

  def getFunctionColumns(a: String, b: String, c: String, d: String): F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getFunctionColumns($a, $b, $c, $d)")
      value.getFunctionColumns(a, b, c, d)
    }

  def getFunctions(a: String, b: String, c: String): F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getFunctions($a, $b, $c)")
      value.getFunctions(a, b, c)
    }

  val getIdentifierQuoteString: F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getIdentifierQuoteString()")
      value.getIdentifierQuoteString()
    }

  def getImportedKeys(a: String, b: String, c: String): F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getImportedKeys($a, $b, $c)")
      value.getImportedKeys(a, b, c)
    }

  def getIndexInfo(a: String, b: String, c: String, d: Boolean, e: Boolean): F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getIndexInfo($a, $b, $c, $d, $e)")
      value.getIndexInfo(a, b, c, d, e)
    }

  val getJDBCMajorVersion: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getJDBCMajorVersion()")
      value.getJDBCMajorVersion()
    }

  val getJDBCMinorVersion: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getJDBCMinorVersion()")
      value.getJDBCMinorVersion()
    }

  val getMaxBinaryLiteralLength: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMaxBinaryLiteralLength()")
      value.getMaxBinaryLiteralLength()
    }

  val getMaxCatalogNameLength: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMaxCatalogNameLength()")
      value.getMaxCatalogNameLength()
    }

  val getMaxCharLiteralLength: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMaxCharLiteralLength()")
      value.getMaxCharLiteralLength()
    }

  val getMaxColumnNameLength: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMaxColumnNameLength()")
      value.getMaxColumnNameLength()
    }

  val getMaxColumnsInGroupBy: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMaxColumnsInGroupBy()")
      value.getMaxColumnsInGroupBy()
    }

  val getMaxColumnsInIndex: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMaxColumnsInIndex()")
      value.getMaxColumnsInIndex()
    }

  val getMaxColumnsInOrderBy: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMaxColumnsInOrderBy()")
      value.getMaxColumnsInOrderBy()
    }

  val getMaxColumnsInSelect: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMaxColumnsInSelect()")
      value.getMaxColumnsInSelect()
    }

  val getMaxColumnsInTable: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMaxColumnsInTable()")
      value.getMaxColumnsInTable()
    }

  val getMaxConnections: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMaxConnections()")
      value.getMaxConnections()
    }

  val getMaxCursorNameLength: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMaxCursorNameLength()")
      value.getMaxCursorNameLength()
    }

  val getMaxIndexLength: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMaxIndexLength()")
      value.getMaxIndexLength()
    }

  val getMaxLogicalLobSize: F[Long] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMaxLogicalLobSize()")
      value.getMaxLogicalLobSize()
    }

  val getMaxProcedureNameLength: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMaxProcedureNameLength()")
      value.getMaxProcedureNameLength()
    }

  val getMaxRowSize: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMaxRowSize()")
      value.getMaxRowSize()
    }

  val getMaxSchemaNameLength: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMaxSchemaNameLength()")
      value.getMaxSchemaNameLength()
    }

  val getMaxStatementLength: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMaxStatementLength()")
      value.getMaxStatementLength()
    }

  val getMaxStatements: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMaxStatements()")
      value.getMaxStatements()
    }

  val getMaxTableNameLength: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMaxTableNameLength()")
      value.getMaxTableNameLength()
    }

  val getMaxTablesInSelect: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMaxTablesInSelect()")
      value.getMaxTablesInSelect()
    }

  val getMaxUserNameLength: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getMaxUserNameLength()")
      value.getMaxUserNameLength()
    }

  val getNumericFunctions: F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getNumericFunctions()")
      value.getNumericFunctions()
    }

  def getPrimaryKeys(a: String, b: String, c: String): F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getPrimaryKeys($a, $b, $c)")
      value.getPrimaryKeys(a, b, c)
    }

  def getProcedureColumns(a: String, b: String, c: String, d: String): F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getProcedureColumns($a, $b, $c, $d)")
      value.getProcedureColumns(a, b, c, d)
    }

  val getProcedureTerm: F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getProcedureTerm()")
      value.getProcedureTerm()
    }

  def getProcedures(a: String, b: String, c: String): F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getProcedures($a, $b, $c)")
      value.getProcedures(a, b, c)
    }

  def getPseudoColumns(a: String, b: String, c: String, d: String): F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getPseudoColumns($a, $b, $c, $d)")
      value.getPseudoColumns(a, b, c, d)
    }

  val getResultSetHoldability: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getResultSetHoldability()")
      value.getResultSetHoldability()
    }

  val getRowIdLifetime: F[RowIdLifetime] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getRowIdLifetime()")
      value.getRowIdLifetime()
    }

  val getSQLKeywords: F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getSQLKeywords()")
      value.getSQLKeywords()
    }

  val getSQLStateType: F[Int] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getSQLStateType()")
      value.getSQLStateType()
    }

  val getSchemaTerm: F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getSchemaTerm()")
      value.getSchemaTerm()
    }

  val getSchemas: F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getSchemas()")
      value.getSchemas()
    }

  def getSchemas(a: String, b: String): F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getSchemas($a, $b)")
      value.getSchemas(a, b)
    }

  val getSearchStringEscape: F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getSearchStringEscape()")
      value.getSearchStringEscape()
    }

  val getStringFunctions: F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getStringFunctions()")
      value.getStringFunctions()
    }

  def getSuperTables(a: String, b: String, c: String): F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getSuperTables($a, $b, $c)")
      value.getSuperTables(a, b, c)
    }

  def getSuperTypes(a: String, b: String, c: String): F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getSuperTypes($a, $b, $c)")
      value.getSuperTypes(a, b, c)
    }

  val getSystemFunctions: F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getSystemFunctions()")
      value.getSystemFunctions()
    }

  def getTablePrivileges(a: String, b: String, c: String): F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getTablePrivileges($a, $b, $c)")
      value.getTablePrivileges(a, b, c)
    }

  val getTableTypes: F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getTableTypes()")
      value.getTableTypes()
    }

  def getTables(a: String, b: String, c: String, d: Array[String]): F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getTables($a, $b, $c, $d)")
      value.getTables(a, b, c, d)
    }

  val getTimeDateFunctions: F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getTimeDateFunctions()")
      value.getTimeDateFunctions()
    }

  val getTypeInfo: F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getTypeInfo()")
      value.getTypeInfo()
    }

  def getUDTs(a: String, b: String, c: String, d: Array[Int]): F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getUDTs($a, $b, $c, $d)")
      value.getUDTs(a, b, c, d)
    }

  val getURL: F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getURL()")
      value.getURL()
    }

  val getUserName: F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getUserName()")
      value.getUserName()
    }

  def getVersionColumns(a: String, b: String, c: String): F[ResultSet] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getVersionColumns($a, $b, $c)")
      value.getVersionColumns(a, b, c)
    }

  def insertsAreDetected(a: Int): F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"insertsAreDetected($a)")
      value.insertsAreDetected(a)
    }

  val isCatalogAtStart: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "isCatalogAtStart()")
      value.isCatalogAtStart()
    }

  val isReadOnly: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "isReadOnly()")
      value.isReadOnly()
    }

  def isWrapperFor(a: Class[_]): F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"isWrapperFor($a)")
      value.isWrapperFor(a)
    }

  val locatorsUpdateCopy: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "locatorsUpdateCopy()")
      value.locatorsUpdateCopy()
    }

  val nullPlusNonNullIsNull: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "nullPlusNonNullIsNull()")
      value.nullPlusNonNullIsNull()
    }

  val nullsAreSortedAtEnd: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "nullsAreSortedAtEnd()")
      value.nullsAreSortedAtEnd()
    }

  val nullsAreSortedAtStart: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "nullsAreSortedAtStart()")
      value.nullsAreSortedAtStart()
    }

  val nullsAreSortedHigh: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "nullsAreSortedHigh()")
      value.nullsAreSortedHigh()
    }

  val nullsAreSortedLow: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "nullsAreSortedLow()")
      value.nullsAreSortedLow()
    }

  def othersDeletesAreVisible(a: Int): F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"othersDeletesAreVisible($a)")
      value.othersDeletesAreVisible(a)
    }

  def othersInsertsAreVisible(a: Int): F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"othersInsertsAreVisible($a)")
      value.othersInsertsAreVisible(a)
    }

  def othersUpdatesAreVisible(a: Int): F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"othersUpdatesAreVisible($a)")
      value.othersUpdatesAreVisible(a)
    }

  def ownDeletesAreVisible(a: Int): F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"ownDeletesAreVisible($a)")
      value.ownDeletesAreVisible(a)
    }

  def ownInsertsAreVisible(a: Int): F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"ownInsertsAreVisible($a)")
      value.ownInsertsAreVisible(a)
    }

  def ownUpdatesAreVisible(a: Int): F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"ownUpdatesAreVisible($a)")
      value.ownUpdatesAreVisible(a)
    }

  val storesLowerCaseIdentifiers: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "storesLowerCaseIdentifiers()")
      value.storesLowerCaseIdentifiers()
    }

  val storesLowerCaseQuotedIdentifiers: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "storesLowerCaseQuotedIdentifiers()")
      value.storesLowerCaseQuotedIdentifiers()
    }

  val storesMixedCaseIdentifiers: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "storesMixedCaseIdentifiers()")
      value.storesMixedCaseIdentifiers()
    }

  val storesMixedCaseQuotedIdentifiers: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "storesMixedCaseQuotedIdentifiers()")
      value.storesMixedCaseQuotedIdentifiers()
    }

  val storesUpperCaseIdentifiers: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "storesUpperCaseIdentifiers()")
      value.storesUpperCaseIdentifiers()
    }

  val storesUpperCaseQuotedIdentifiers: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "storesUpperCaseQuotedIdentifiers()")
      value.storesUpperCaseQuotedIdentifiers()
    }

  val supportsANSI92EntryLevelSQL: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsANSI92EntryLevelSQL()")
      value.supportsANSI92EntryLevelSQL()
    }

  val supportsANSI92FullSQL: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsANSI92FullSQL()")
      value.supportsANSI92FullSQL()
    }

  val supportsANSI92IntermediateSQL: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsANSI92IntermediateSQL()")
      value.supportsANSI92IntermediateSQL()
    }

  val supportsAlterTableWithAddColumn: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsAlterTableWithAddColumn()")
      value.supportsAlterTableWithAddColumn()
    }

  val supportsAlterTableWithDropColumn: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsAlterTableWithDropColumn()")
      value.supportsAlterTableWithDropColumn()
    }

  val supportsBatchUpdates: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsBatchUpdates()")
      value.supportsBatchUpdates()
    }

  val supportsCatalogsInDataManipulation: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsCatalogsInDataManipulation()")
      value.supportsCatalogsInDataManipulation()
    }

  val supportsCatalogsInIndexDefinitions: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsCatalogsInIndexDefinitions()")
      value.supportsCatalogsInIndexDefinitions()
    }

  val supportsCatalogsInPrivilegeDefinitions: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsCatalogsInPrivilegeDefinitions()")
      value.supportsCatalogsInPrivilegeDefinitions()
    }

  val supportsCatalogsInProcedureCalls: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsCatalogsInProcedureCalls()")
      value.supportsCatalogsInProcedureCalls()
    }

  val supportsCatalogsInTableDefinitions: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsCatalogsInTableDefinitions()")
      value.supportsCatalogsInTableDefinitions()
    }

  val supportsColumnAliasing: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsColumnAliasing()")
      value.supportsColumnAliasing()
    }

  val supportsConvert: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsConvert()")
      value.supportsConvert()
    }

  def supportsConvert(a: Int, b: Int): F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"supportsConvert($a, $b)")
      value.supportsConvert(a, b)
    }

  val supportsCoreSQLGrammar: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsCoreSQLGrammar()")
      value.supportsCoreSQLGrammar()
    }

  val supportsCorrelatedSubqueries: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsCorrelatedSubqueries()")
      value.supportsCorrelatedSubqueries()
    }

  val supportsDataDefinitionAndDataManipulationTransactions: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsDataDefinitionAndDataManipulationTransactions()")
      value.supportsDataDefinitionAndDataManipulationTransactions()
    }

  val supportsDataManipulationTransactionsOnly: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsDataManipulationTransactionsOnly()")
      value.supportsDataManipulationTransactionsOnly()
    }

  val supportsDifferentTableCorrelationNames: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsDifferentTableCorrelationNames()")
      value.supportsDifferentTableCorrelationNames()
    }

  val supportsExpressionsInOrderBy: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsExpressionsInOrderBy()")
      value.supportsExpressionsInOrderBy()
    }

  val supportsExtendedSQLGrammar: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsExtendedSQLGrammar()")
      value.supportsExtendedSQLGrammar()
    }

  val supportsFullOuterJoins: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsFullOuterJoins()")
      value.supportsFullOuterJoins()
    }

  val supportsGetGeneratedKeys: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsGetGeneratedKeys()")
      value.supportsGetGeneratedKeys()
    }

  val supportsGroupBy: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsGroupBy()")
      value.supportsGroupBy()
    }

  val supportsGroupByBeyondSelect: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsGroupByBeyondSelect()")
      value.supportsGroupByBeyondSelect()
    }

  val supportsGroupByUnrelated: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsGroupByUnrelated()")
      value.supportsGroupByUnrelated()
    }

  val supportsIntegrityEnhancementFacility: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsIntegrityEnhancementFacility()")
      value.supportsIntegrityEnhancementFacility()
    }

  val supportsLikeEscapeClause: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsLikeEscapeClause()")
      value.supportsLikeEscapeClause()
    }

  val supportsLimitedOuterJoins: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsLimitedOuterJoins()")
      value.supportsLimitedOuterJoins()
    }

  val supportsMinimumSQLGrammar: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsMinimumSQLGrammar()")
      value.supportsMinimumSQLGrammar()
    }

  val supportsMixedCaseIdentifiers: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsMixedCaseIdentifiers()")
      value.supportsMixedCaseIdentifiers()
    }

  val supportsMixedCaseQuotedIdentifiers: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsMixedCaseQuotedIdentifiers()")
      value.supportsMixedCaseQuotedIdentifiers()
    }

  val supportsMultipleOpenResults: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsMultipleOpenResults()")
      value.supportsMultipleOpenResults()
    }

  val supportsMultipleResultSets: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsMultipleResultSets()")
      value.supportsMultipleResultSets()
    }

  val supportsMultipleTransactions: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsMultipleTransactions()")
      value.supportsMultipleTransactions()
    }

  val supportsNamedParameters: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsNamedParameters()")
      value.supportsNamedParameters()
    }

  val supportsNonNullableColumns: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsNonNullableColumns()")
      value.supportsNonNullableColumns()
    }

  val supportsOpenCursorsAcrossCommit: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsOpenCursorsAcrossCommit()")
      value.supportsOpenCursorsAcrossCommit()
    }

  val supportsOpenCursorsAcrossRollback: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsOpenCursorsAcrossRollback()")
      value.supportsOpenCursorsAcrossRollback()
    }

  val supportsOpenStatementsAcrossCommit: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsOpenStatementsAcrossCommit()")
      value.supportsOpenStatementsAcrossCommit()
    }

  val supportsOpenStatementsAcrossRollback: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsOpenStatementsAcrossRollback()")
      value.supportsOpenStatementsAcrossRollback()
    }

  val supportsOrderByUnrelated: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsOrderByUnrelated()")
      value.supportsOrderByUnrelated()
    }

  val supportsOuterJoins: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsOuterJoins()")
      value.supportsOuterJoins()
    }

  val supportsPositionedDelete: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsPositionedDelete()")
      value.supportsPositionedDelete()
    }

  val supportsPositionedUpdate: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsPositionedUpdate()")
      value.supportsPositionedUpdate()
    }

  val supportsRefCursors: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsRefCursors()")
      value.supportsRefCursors()
    }

  def supportsResultSetConcurrency(a: Int, b: Int): F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"supportsResultSetConcurrency($a, $b)")
      value.supportsResultSetConcurrency(a, b)
    }

  def supportsResultSetHoldability(a: Int): F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"supportsResultSetHoldability($a)")
      value.supportsResultSetHoldability(a)
    }

  def supportsResultSetType(a: Int): F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"supportsResultSetType($a)")
      value.supportsResultSetType(a)
    }

  val supportsSavepoints: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsSavepoints()")
      value.supportsSavepoints()
    }

  val supportsSchemasInDataManipulation: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsSchemasInDataManipulation()")
      value.supportsSchemasInDataManipulation()
    }

  val supportsSchemasInIndexDefinitions: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsSchemasInIndexDefinitions()")
      value.supportsSchemasInIndexDefinitions()
    }

  val supportsSchemasInPrivilegeDefinitions: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsSchemasInPrivilegeDefinitions()")
      value.supportsSchemasInPrivilegeDefinitions()
    }

  val supportsSchemasInProcedureCalls: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsSchemasInProcedureCalls()")
      value.supportsSchemasInProcedureCalls()
    }

  val supportsSchemasInTableDefinitions: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsSchemasInTableDefinitions()")
      value.supportsSchemasInTableDefinitions()
    }

  val supportsSelectForUpdate: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsSelectForUpdate()")
      value.supportsSelectForUpdate()
    }

  val supportsStatementPooling: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsStatementPooling()")
      value.supportsStatementPooling()
    }

  val supportsStoredFunctionsUsingCallSyntax: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsStoredFunctionsUsingCallSyntax()")
      value.supportsStoredFunctionsUsingCallSyntax()
    }

  val supportsStoredProcedures: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsStoredProcedures()")
      value.supportsStoredProcedures()
    }

  val supportsSubqueriesInComparisons: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsSubqueriesInComparisons()")
      value.supportsSubqueriesInComparisons()
    }

  val supportsSubqueriesInExists: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsSubqueriesInExists()")
      value.supportsSubqueriesInExists()
    }

  val supportsSubqueriesInIns: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsSubqueriesInIns()")
      value.supportsSubqueriesInIns()
    }

  val supportsSubqueriesInQuantifieds: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsSubqueriesInQuantifieds()")
      value.supportsSubqueriesInQuantifieds()
    }

  val supportsTableCorrelationNames: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsTableCorrelationNames()")
      value.supportsTableCorrelationNames()
    }

  def supportsTransactionIsolationLevel(a: Int): F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"supportsTransactionIsolationLevel($a)")
      value.supportsTransactionIsolationLevel(a)
    }

  val supportsTransactions: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsTransactions()")
      value.supportsTransactions()
    }

  val supportsUnion: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsUnion()")
      value.supportsUnion()
    }

  val supportsUnionAll: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "supportsUnionAll()")
      value.supportsUnionAll()
    }

  def unwrap[T](a: Class[T]): F[T] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"unwrap($a)")
      value.unwrap(a)
    }

  def updatesAreDetected(a: Int): F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"updatesAreDetected($a)")
      value.updatesAreDetected(a)
    }

  val usesLocalFilePerTable: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "usesLocalFilePerTable()")
      value.usesLocalFilePerTable()
    }

  val usesLocalFiles: F[Boolean] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "usesLocalFiles()")
      value.usesLocalFiles()
    }

}

