// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless

import cats._
import cats.effect.{ Resource, Sync }
import cats.implicits._
import doobie.{ Fragment, Write }
import doobie.tagless.async._
import doobie.tagless.jdbc.JdbcPreparedStatement
import fs2.{ Pipe, Sink, Stream }

trait PreparedStatement[F[_]] {

  def jdbc: JdbcPreparedStatement[F]

  /** Execute this statement as a query, yielding a ResultSet[F] that will be cleaned up. */
  def executeQuery: Resource[F, ResultSet[F]]

  /**
   * Set the statement parameters using `A` flattened to a column vector, starting at the given
   * offset.
   */
  def set[A: Write](a: A, offset: Int): F[Unit]

  /**
   * Construct a sink for batch update, discarding update counts. Note that the result array will
   * be computed by JDBC and then discarded, so this call has the same cost as `rawPipe`.
   */
  def sink[A: Write]: Sink[F, A]
  /**
   * Construct a pipe for batch update, translating each input value into its update count. Unless
   * you're inspecting the results it's cheaper to use `sink`.
   */
  def pipe[A: Write]: Pipe[F, A, BatchResult]


  // TODO: … fix Fragment so we don't have to do this
  private[tagless] def setArguments(f: Fragment): F[Unit]

}

object PreparedStatement {

  def async[F[_]: Sync](jdbc0: AsyncPreparedStatement[F], interp: Interpreter[F]): PreparedStatement[F] =
    new PreparedStatement[F] {

      def jdbc = jdbc0

      def setArguments(f: Fragment): F[Unit] =
        interp.rts.newBlockingPrimitive(f.unsafePrepare(jdbc.value))

      def executeQuery: Resource[F, ResultSet[F]] =
        Resource.make(jdbc.executeQuery.map(interp.forResultSet))(_.jdbc.close)

      def set[A: Write](a: A, offset: Int): F[Unit] =
        interp.rts.newBlockingPrimitive(Write[A].unsafeSet(jdbc.value, offset, a))

      def sink[A: Write]: Sink[F, A] = as =>
        as.through(rawPipe[A]).drain

      def pipe[A: Write]: Pipe[F, A, BatchResult] = as =>
        as.through(rawPipe[A]).flatMap(a => Stream.emits(a)).map(BatchResult.fromJdbc)

      @SuppressWarnings(Array("org.wartremover.warts.ToString"))
      private def rawPipe[A](
        implicit wa: Write[A]
      ): Pipe[F, A, Array[Int]] = as =>
        as.chunks.evalMap { chunk =>
          interp.rts.newBlockingPrimitive {
            interp.rts.log.unsafe.trace(jdbc.value, {
              val types = wa.puts.map { case (g, _) =>
                g.typeStack.head.fold("«unknown»")(_.toString)
              }
              s"addBatch(${chunk.size}) of ${types.mkString(", ")}"
            })
            chunk.foreach { a =>
              wa.unsafeSet(jdbc.value, 1, a)
              jdbc.value.addBatch
            }
          }
        } .drain ++ Stream.eval(jdbc.executeBatch)

    }

}