// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless

import cats.{ Monad, ~> }
import cats.effect._
import cats.implicits._
import doobie.{ Query0, Update }
import doobie.syntax.string._
import fs2._

trait Unapplied[G[_[_]], A] { outer =>

  def on[F[_]: Sync](c: G[F]): F[A]

  def flatMap[B](f: A => Unapplied[G, B]): Unapplied[G, B] =
    new Unapplied[G, B] {
      def on[F[_]: Sync](c: G[F]): F[B] =
        outer.on(c).flatMap(a => f(a).on(c))
    }

}
object Unapplied {

  // // G[F] induces a natural transformation Unapplied[G, ?] ~> F
  // def liftT[F[_]: Sync, G[_[_]]](gf: G[F]): Unapplied[G, ?] ~> F =
  //   λ[Unapplied[G, ?] ~> F](_.on(gf))

  // def pure[G[_[_]], A](a: A): Unapplied[G, A] =
  //   new Unapplied[G, A] {
  //     def on[F[_]: Sync](c: G[F]) =
  //       a.pure[F]
  //   }

  // implicit def monadAwaits[G[_[_]]]: Monad[Unapplied[G, ?]] =
  //   new Monad[Unapplied[G, ?]] {
  //     def pure[A](a: A) = Unapplied.pure(a)
  //     def flatMap[A, B](fa: Unapplied[G,A])(f: A => Unapplied[G,B]) = fa.flatMap(f)

  //     // how do we make this tail recursive?
  //     @SuppressWarnings(Array("org.wartremover.warts.Recursion"))
  //     def tailRecM[A, B](a: A)(f: A => Unapplied[G,Either[A,B]]) =
  //       f(a).flatMap {
  //         case Left(a)  => tailRecM(a)(f)
  //         case Right(b) => pure(b)
  //       }

  //   }

}
