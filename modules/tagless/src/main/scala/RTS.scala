// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless

import cats.effect._
import java.util.concurrent.{ Executors, ThreadFactory }
import scala.concurrent.ExecutionContext

/**
 * A simple runtime system consistening of a pair of `ExecutionContexts`, one for blocking IO
 * operations and another for CPU-bound operations.
 */
final case class RTS[F[_]: Async](
  blockingContext:    ExecutionContext,
  nonBlockingContext: ExecutionContext
) {

  /** Enter the RTS by shifting onto `nonBlockingContext`. */
  val enter: F[Unit] =
    Async.shift(nonBlockingContext)

  /**
   * A `Resource` that shifts to `blockingContext`, yielding `()` as the resource, and shifts to
   * `nonBlockingContext` when the resource is released.
   */
  val blockingResource: Resource[F, Unit] =
    Resource.make(Async.shift(blockingContext))(_ => Async.shift(nonBlockingContext))

  /**
   * Given a program `fa` return a new program that shifts to `blockingContext`, executes `fa`,
   * and shifts to `nonBlockingContext` on completion.
   */
  def block[A](fa: F[A]): F[A] =
    blockingResource.use(_ => fa)

  /**
   * Given side-effecting thunk `a`, construct a new primitive operation that evaluates `a` on
   * `blockingContext` and then shifts to `nonBlockingContext` on completion.
   */
  def newBlockingPrimitive[A](a: => A): F[A] =
    block(Sync[F].delay(a))

}

object RTS {

  /** A reasonable CPU-bound execution context, an alias for `ExecutionContext.global`. */
  val defaultNonBlockingContext: ExecutionContext =
    ExecutionContext.global

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

  /** Construct an RTS for any async effect `F`, using `defaultNonBlockingContext` and `defaultBlocking`.  */
  def default[F[_]: Async]: RTS[F] =
    RTS(defaultBlockingContext, defaultNonBlockingContext)

}