package doobie.postgres.free

import cats.~>
import cats.effect.Async
import cats.free.{ Free => FF } // alias because some algebras have an op called Free

import org.postgresql.copy.{ CopyIn => PGCopyIn }

@SuppressWarnings(Array("org.wartremover.warts.Overloading"))
object copyin { module =>

  // Algebra of operations for PGCopyIn. Each accepts a visitor as an alternatie to pattern-matching.
  sealed trait CopyInOp[A] {
    def visit[F[_]](v: CopyInOp.Visitor[F]): F[A]
  }

  // Free monad over CopyInOp.
  type CopyInIO[A] = FF[CopyInOp, A]

  // Module of instances and constructors of CopyInOp.
  object CopyInOp {

    // Given a PGCopyIn we can embed a CopyInIO program in any algebra that understands embedding.
    implicit val CopyInOpEmbeddable: Embeddable[CopyInOp, PGCopyIn] =
      new Embeddable[CopyInOp, PGCopyIn] {
        def embed[A](j: PGCopyIn, fa: FF[CopyInOp, A]) = Embedded.CopyIn(j, fa)
      }

    // Interface for a natural tansformation CopyInOp ~> F encoded via the visitor pattern.
    // This approach is much more efficient than pattern-matching for large algebras.
    trait Visitor[F[_]] extends (CopyInOp ~> F) {
      final def apply[A](fa: CopyInOp[A]): F[A] = fa.visit(this)

      // Common
      def raw[A](f: PGCopyIn => A): F[A]
      def embed[A](e: Embedded[A]): F[A]
      def delay[A](a: () => A): F[A]
      def handleErrorWith[A](fa: CopyInIO[A], f: Throwable => CopyInIO[A]): F[A]
      def async[A](k: (Either[Throwable, A] => Unit) => Unit): F[A]

      // PGCopyIn
      def cancelCopy: F[Unit]
      def endCopy: F[Long]
      def flushCopy: F[Unit]
      def getFieldCount: F[Int]
      def getFieldFormat(a: Int): F[Int]
      def getFormat: F[Int]
      def getHandledRowCount: F[Long]
      def isActive: F[Boolean]
      def writeToCopy(a: Array[Byte], b: Int, c: Int): F[Unit]

    }

    // Common operations for all algebras.
    final case class Raw[A](f: PGCopyIn => A) extends CopyInOp[A] {
      def visit[F[_]](v: Visitor[F]) = v.raw(f)
    }
    final case class Embed[A](e: Embedded[A]) extends CopyInOp[A] {
      def visit[F[_]](v: Visitor[F]) = v.embed(e)
    }
    final case class Delay[A](a: () => A) extends CopyInOp[A] {
      def visit[F[_]](v: Visitor[F]) = v.delay(a)
    }
    final case class HandleErrorWith[A](fa: CopyInIO[A], f: Throwable => CopyInIO[A]) extends CopyInOp[A] {
      def visit[F[_]](v: Visitor[F]) = v.handleErrorWith(fa, f)
    }
    final case class Async1[A](k: (Either[Throwable, A] => Unit) => Unit) extends CopyInOp[A] {
      def visit[F[_]](v: Visitor[F]) = v.async(k)
    }

    // PGCopyIn-specific operations.
    final case object CancelCopy extends CopyInOp[Unit] {
      def visit[F[_]](v: Visitor[F]) = v.cancelCopy
    }
    final case object EndCopy extends CopyInOp[Long] {
      def visit[F[_]](v: Visitor[F]) = v.endCopy
    }
    final case object FlushCopy extends CopyInOp[Unit] {
      def visit[F[_]](v: Visitor[F]) = v.flushCopy
    }
    final case object GetFieldCount extends CopyInOp[Int] {
      def visit[F[_]](v: Visitor[F]) = v.getFieldCount
    }
    final case class  GetFieldFormat(a: Int) extends CopyInOp[Int] {
      def visit[F[_]](v: Visitor[F]) = v.getFieldFormat(a)
    }
    final case object GetFormat extends CopyInOp[Int] {
      def visit[F[_]](v: Visitor[F]) = v.getFormat
    }
    final case object GetHandledRowCount extends CopyInOp[Long] {
      def visit[F[_]](v: Visitor[F]) = v.getHandledRowCount
    }
    final case object IsActive extends CopyInOp[Boolean] {
      def visit[F[_]](v: Visitor[F]) = v.isActive
    }
    final case class  WriteToCopy(a: Array[Byte], b: Int, c: Int) extends CopyInOp[Unit] {
      def visit[F[_]](v: Visitor[F]) = v.writeToCopy(a, b, c)
    }

  }
  import CopyInOp._

  // Smart constructors for operations common to all algebras.
  val unit: CopyInIO[Unit] = FF.pure[CopyInOp, Unit](())
  def raw[A](f: PGCopyIn => A): CopyInIO[A] = FF.liftF(Raw(f))
  def embed[F[_], J, A](j: J, fa: FF[F, A])(implicit ev: Embeddable[F, J]): FF[CopyInOp, A] = FF.liftF(Embed(ev.embed(j, fa)))
  def delay[A](a: => A): CopyInIO[A] = FF.liftF(Delay(() => a))
  def handleErrorWith[A](fa: CopyInIO[A], f: Throwable => CopyInIO[A]): CopyInIO[A] = FF.liftF[CopyInOp, A](HandleErrorWith(fa, f))
  def raiseError[A](err: Throwable): CopyInIO[A] = delay(throw err)
  def async[A](k: (Either[Throwable, A] => Unit) => Unit): CopyInIO[A] = FF.liftF[CopyInOp, A](Async1(k))

  // Smart constructors for CopyIn-specific operations.
  val cancelCopy: CopyInIO[Unit] = FF.liftF(CancelCopy)
  val endCopy: CopyInIO[Long] = FF.liftF(EndCopy)
  val flushCopy: CopyInIO[Unit] = FF.liftF(FlushCopy)
  val getFieldCount: CopyInIO[Int] = FF.liftF(GetFieldCount)
  def getFieldFormat(a: Int): CopyInIO[Int] = FF.liftF(GetFieldFormat(a))
  val getFormat: CopyInIO[Int] = FF.liftF(GetFormat)
  val getHandledRowCount: CopyInIO[Long] = FF.liftF(GetHandledRowCount)
  val isActive: CopyInIO[Boolean] = FF.liftF(IsActive)
  def writeToCopy(a: Array[Byte], b: Int, c: Int): CopyInIO[Unit] = FF.liftF(WriteToCopy(a, b, c))

  // CopyInIO is an Async
  implicit val AsyncCopyInIO: Async[CopyInIO] =
    new Async[CopyInIO] {
      val M = FF.catsFreeMonadForFree[CopyInOp]
      def pure[A](x: A): CopyInIO[A] = M.pure(x)
      def handleErrorWith[A](fa: CopyInIO[A])(f: Throwable => CopyInIO[A]): CopyInIO[A] = module.handleErrorWith(fa, f)
      def raiseError[A](e: Throwable): CopyInIO[A] = module.raiseError(e)
      def async[A](k: (Either[Throwable,A] => Unit) => Unit): CopyInIO[A] = module.async(k)
      def flatMap[A, B](fa: CopyInIO[A])(f: A => CopyInIO[B]): CopyInIO[B] = M.flatMap(fa)(f)
      def tailRecM[A, B](a: A)(f: A => CopyInIO[Either[A, B]]): CopyInIO[B] = M.tailRecM(a)(f)
      def suspend[A](thunk: => CopyInIO[A]): CopyInIO[A] = M.flatten(module.delay(thunk))
    }

}

