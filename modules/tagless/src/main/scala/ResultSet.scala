// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless

import cats._
import cats.effect.Sync
import cats.implicits._
import doobie.Read
import doobie.tagless.async._
import doobie.util.invariant._
import fs2.Stream
import scala.annotation.tailrec
import scala.collection.generic.CanBuildFrom
import scala.collection.mutable.Builder

final case class ResultSet[F[_]: Sync](jdbc: AsyncResultSet[F], interp: Interpreter[F]) {

  /**
   * Stream the resultset, setting fetch size to `chunkSize`, interpreting rows as type `A`,
   * reading `chunkSize` rows at a time.
   */
  def stream[A: Read](chunkSize: Int): Stream[F, A] =
    Stream.eval_(jdbc.setFetchSize(chunkSize)) ++
    Stream.repeatEval(chunk[Vector, A](chunkSize))
          .takeThrough(_.length === chunkSize)
          .flatMap((c: Vector[A]) => Stream.emits(c))

  /** Move to the next row, if any, and read into `A`. */
  def next[A](
    implicit ra: Read[A]
  ): F[Option[A]] = {
    val unwiseGet: F[A] = interp.rts.newBlockingPrimitive(ra.unsafeGet(jdbc.value, 1))
    jdbc.next.flatMap {
      case true  => unwiseGet.map(Some(_))
      case false => Option.empty[A].pure[F]
    }
  }

  /** Move to the next row (if any) and read into `A`, raising an error if more rows exist. */
  def option[A: Read](
    implicit ev: MonadError[F, Throwable]
  ): F[Option[A]] =
    (next[A], jdbc.next).tupled.flatMap {
      case (sa @ Some(_), false) => ev.pure(sa)
      case (Some(_), true)       => ev.raiseError(UnexpectedContinuation)
      case (None, _)             => ev.pure(None)
    }

  /** Move to the next row (if any) and read into `A`, raising an error if more rows exist. */
  def unique[A: Read](
    implicit ev: MonadError[F, Throwable]
  ): F[A] =
    option[A].flatMap {
      case Some(a) => ev.pure(a)
      case None    => ev.raiseError(UnexpectedEnd)
    }

  /**
   * Accumulate up to `chunkSize` rows, interpreting rows as type `A`, into a collection `C`,
   * using `CanBuildFrom`, which is very fast.
   */
  def chunk[C[_], A: Read](chunkSize: Int)(
    implicit cbf: CanBuildFrom[Nothing, A, C[A]]
  ): F[C[A]] =
    chunkMap[C, A, A](chunkSize, Predef.identity)

  /**
   * Accumulate up to `chunkSize` rows, interpreting rows as type `A`, into a collection `C`,
   * using `Alternative`, which is not as fast as `CanBuildFrom`; prefer `chunk` when possible.
   */
  def chunkA[C[_]: Alternative, A: Read](chunkSize: Int): F[C[A]] =
    chunkMapA[C, A, A](chunkSize, Predef.identity)

  /**
   * Accumulate up to `chunkSize` rows, interpreting rows as type `A`, mapping into `B`, into a
   * collection `C`, using `CanBuildFrom`, which is very fast.
   */
  @SuppressWarnings(Array("org.wartremover.warts.ToString"))
  def chunkMap[C[_], A, B](chunkSize: Int, f: A => B)(
    implicit ra: Read[A],
            cbf: CanBuildFrom[Nothing, B, C[B]]
  ): F[C[B]] =
    interp.rts.newBlockingPrimitive {

      interp.rts.log.unsafe.trace(jdbc.value, {
        val types = ra.gets.map { case (g, _) =>
          g.typeStack.last.fold("«unknown»")(_.toString)
        }
        s"chunkMap($chunkSize) of ${types.mkString(", ")}"
      })

      @tailrec
      def go(accum: Builder[B, C[B]], n: Int): C[B] =
        if (n > 0 && jdbc.value.next) {
          val b = f(ra.unsafeGet(jdbc.value, 1))
          go(accum += b, n - 1)
        } else accum.result

      go(cbf(), chunkSize)

    }

  /**
   * Accumulate up to `chunkSize` rows, interpreting rows as type `A`, mapping into `B`, into a
   * collection `C`, using `Alternative`, which is not as fast as `CanBuildFrom`; prefer `chunkMap`
   * when possible.
   */
  @SuppressWarnings(Array("org.wartremover.warts.ToString"))
  def chunkMapA[C[_], A, B](chunkSize: Int, f: A => B)(
    implicit ra: Read[A],
             ac: Alternative[C]
  ): F[C[B]] =
    interp.rts.newBlockingPrimitive {

      interp.rts.log.unsafe.trace(jdbc.value, {
        val types = ra.gets.map { case (g, _) =>
          g.typeStack.last.fold("«unknown»")(_.toString)
        }
        s"chunkMapA($chunkSize) of ${types.mkString(", ")}"
      })

      @tailrec
      def go(accum: C[B], n: Int): C[B] =
        if (n > 0 && jdbc.value.next) {
          val b = f(ra.unsafeGet(jdbc.value, 1))
          go(accum <+> ac.pure(b), n - 1)
        } else accum

      go(ac.empty, chunkSize)

    }

}
