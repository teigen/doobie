// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless

import cats._
import cats.effect._
import cats.implicits._
import doobie.{ Fragment, Read, Write }
import doobie.syntax.string._
import fs2._
import org.slf4j._

@SuppressWarnings(Array("org.wartremover.warts.ToString"))
object Test {

  // our query and update classes are trivial now, NICE
  final case class Query[A](fragment: Fragment, read: Read[A]) { outer =>
    def map[B](f: A => B): Query[B] =
      Query[B](fragment, read.map(f))
  }

  final case class Update[A](fragment: Fragment, read: Write[A]) { outer =>
    def contramap[B](f: B => A): Update[B] =
      Update[B](fragment, read.contramap(f))
  }




  final case class Code(code: String)
  final case class Country(code: Code, name: String)

  final implicit class MyConnectionOps[F[_]](c: Connection[F]) {

    def countryStream(implicit ev: Bracket[F, Throwable]): Stream[F, Country] =
      c.stream[Country](MyFragments.countries, 64)

    def countrySink(implicit ev: Functor[F]): Sink[F, Country] =
      c.sink[Country](MyFragments.up)

    def countriesByCode(k: Code)(implicit ev: Bracket[F, Throwable]): F[List[Country]] =
      c.to[List, Country](MyFragments.byCode(k))

  }

  object MyFragments {

    // but we need sql"...".update[A: Write] as (Fragment, Write[A]) which is checkable,
    // ans         sql"...".query[A: Read] which is also checkable

    val countries: Fragment =
      sql"select * from country"

    def up: Fragment =
      sql"insert into country2 (code, name) values (?, ?)"

    def byCode(c: Code): Fragment =
      sql"select code, name from country where code = $c"

  }

  val rts = RTS.default[IO]
  val log = LoggerFactory.getLogger("test")

  val xa = Transactor[IO](
    Interpreter(rts, log),
    Strategy.transactional,
    Connector.fromDriverManager("org.postgresql.Driver", "jdbc:postgresql:world", "postgres", "")
  )

  def go[F[_]: Sync](c: Connection[F]): F[Unit] =
    for {
      _  <- c.countryStream.to(c.countrySink).compile.drain
      _  <- Sync[F].delay(log.info("Doing other work inside F"))
      cs <- c.countriesByCode(Code("FRA"))
      _  <- Sync[F].delay(log.info(cs.toString))
    } yield ()


  val prog: IO[Unit] =
    rts.enter *>
    IO(log.info("Starting up.")) *>
    xa.transact(c => go(c)) *>
    IO(log.info(s"Done."))

  def main(args: Array[String]): Unit = {
    val a = System.setProperty(s"org.slf4j.simpleLogger.log.${log.getName}", "trace")
    log.info(s"main <enter>")
    prog.unsafeRunSync
    log.info(s"main <exit>")
  }

}

