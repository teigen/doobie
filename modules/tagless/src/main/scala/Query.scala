// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless

import cats.Functor
import doobie.{ Fragment, Read  }

final case class Query[A](fragment: Fragment)(implicit val read: Read[A]) { outer =>
  def map[B](f: A => B): Query[B] =
    Query[B](fragment)(read.map(f))
}

object Query {

  implicit val QueryFunctor: Functor[Query] =
    new Functor[Query] {
      def map[A, B](fa: Query[A])(f: A => B): Query[B] =
        fa.map(f)
    }

}