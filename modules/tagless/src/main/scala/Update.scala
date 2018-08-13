// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless

import cats.Contravariant
import doobie.{ Fragment, Write }

final case class Update[A](fragment: Fragment)(implicit val write: Write[A]) { outer =>
  def contramap[B](f: B => A): Update[B] =
    Update[B](fragment)(write.contramap(f))
}

object Update {

  implicit val UpdateFunctor: Contravariant[Update] =
    new Contravariant[Update] {
      def contramap[A, B](fa: Update[A])(f: B => A): Update[B] =
        fa.contramap(f)
    }

}