// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless.async

import cats.effect.Sync
import doobie.tagless.{ RTS, Logger }
import doobie.tagless.jdbc._
import org.slf4j.{ Logger => JLogger }
import java.lang.String
import java.sql.Ref
import java.util.Map

/**
 * Implementation of `JdbcRef` that wraps a `java.sql.Ref` and lifts its operations
 * into blocking operations on `RTS[F]`, logged at `TRACE` level on `log`.
 */
@SuppressWarnings(Array("org.wartremover.warts.Overloading"))
class AsyncRef[F[_]: Sync](val value: Ref, val rts: RTS[F]) extends JdbcRef[F] {

  val getBaseTypeName: F[String] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getBaseTypeName()")
      value.getBaseTypeName()
    }

  val getObject: F[AnyRef] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, "getObject()")
      value.getObject()
    }

  def getObject(a: Map[String, Class[_]]): F[AnyRef] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"getObject($a)")
      value.getObject(a)
    }

  def setObject(a: AnyRef): F[Unit] =
    rts.newBlockingPrimitive {
      rts.log.unsafe.trace(value, s"setObject($a)")
      value.setObject(a)
    }

}

