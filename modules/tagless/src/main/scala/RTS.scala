// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless

import cats.effect._
import java.util.concurrent.{ Executors, ThreadFactory }
import scala.concurrent.ExecutionContext

/**
 * A runtime system (typically unique in any program) capable of shifting, blocking, logging, and
 * constructing new blocking primitives. Doobie relies on an RTS to lift primitive JDBC operations.
 */
final case class RTS[F[_]: Sync](
  blockingContext: ExecutionContext,
  contextShift:    ContextShift[F],
  log:             Logger[F]
) {

  /**
   * Given a program `fa` return a new program that shifts to `blockingContext`, executes `fa`,
   * and shifts to `nonBlockingContext` on completion.
   */
  def block[A](fa: F[A]): F[A] =
    contextShift.evalOn(blockingContext)(fa)

  /**
   * Given side-effecting thunk `a`, construct a new primitive operation that evaluates `a` on
   * `blockingContext` and then shifts to `nonBlockingContext` on completion.
   */
  def newBlockingPrimitive[A](a: => A): F[A] =
    block(Sync[F].delay(a))

}

object RTS {

  /** A reasonable IO-bound execution context, an unbounded thread pool. */
  val defaultBlockingContext: ExecutionContext =
    ExecutionContext.fromExecutor(Executors.newCachedThreadPool(
      new ThreadFactory {
        def newThread(r: Runnable): Thread = {
          val th = new Thread(r)
          th.setName(s"doobie-rts-default-blocking-${th.getId}")
          th.setDaemon(true)
          th
        }
      }
    ))

  def default[F[_]: Sync: ContextShift]: RTS[F] =
    new RTS(defaultBlockingContext, implicitly, Logger("doobie-rts", 30))

}