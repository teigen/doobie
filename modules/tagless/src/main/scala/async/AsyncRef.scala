// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless.async

import doobie.tagless.RTS
import doobie.tagless.jdbc._
import org.slf4j.Logger
import java.lang.String
import java.sql.Ref
import java.util.Map

/**
 * Implementation of `JdbcRef` that wraps a `java.sql.Ref` and lifts its operations
 * into blocking operations on `RTS[F]`, logged at `TRACE` level on `log`.
 */
@SuppressWarnings(Array("org.wartremover.warts.Overloading"))
class AsyncRef[F[_]](val value: Ref, rts: RTS[F], log: Logger) extends JdbcRef[F] {

  val id: String =
    s"${System.identityHashCode(value).toHexString.padTo(8, ' ')} Ref".padTo(28, ' ')

  val getBaseTypeName: F[String] =
    rts.newBlockingPrimitive {
      if (log.isTraceEnabled)
        log.trace(s"$id getBaseTypeName()")
      value.getBaseTypeName()
    }

  val getObject: F[AnyRef] =
    rts.newBlockingPrimitive {
      if (log.isTraceEnabled)
        log.trace(s"$id getObject()")
      value.getObject()
    }

  def getObject(a: Map[String, Class[_]]): F[AnyRef] =
    rts.newBlockingPrimitive {
      if (log.isTraceEnabled)
        log.trace(s"$id getObject($a)")
      value.getObject(a)
    }

  def setObject(a: AnyRef): F[Unit] =
    rts.newBlockingPrimitive {
      if (log.isTraceEnabled)
        log.trace(s"$id setObject($a)")
      value.setObject(a)
    }

}

