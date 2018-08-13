// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless

import cats._
import cats.effect._
import cats.implicits._
import doobie.syntax.string._
import fs2.{ Stream, Sink }

// WE NEED TO THREAD THE LOGGER THROUGH READ/GET AND WRITE/PUT SO WE CAN SEE WHAT
// ACTUAL QUERY ARGS AND COLUMN READS ARE. IT'S PROBABLY OK TO ALWAYS LOG ARGS
// IF WE'RE NOT IMPLEMENTING A SINK, BUT FOR SINKS AND RESULTSETS WE CAN ONLY LOG
// AT A VERY LOW LEVEL BECAUSE IT'S THE PRIMARY PERF HOTSPOT.

@SuppressWarnings(Array("org.wartremover.warts.ToString"))
object Test {

  final case class Code(code: String)
  final case class Country(code: Code, name: String)

  final implicit class MyConnectionOps[F[_]](c: Connection[F]) {

    def countryStream(implicit ev: Bracket[F, Throwable]): Stream[F, Country] =
      c.stream(Statements.countries, 50)

    def countrySink(implicit ev: Functor[F]): Sink[F, Country] =
      c.sink(Statements.up)

    def countriesByCode(k: Code)(implicit ev: Bracket[F, Throwable]): F[List[Country]] =
      c.to[List](Statements.byCode(k))

  }

  object Statements {

    val countries: Query[Country] =
      Query(sql"select * from country")

    def up: Update[Country] =
      Update(sql"insert into country2 (code, name) values (?, ?)")

    def byCode(c: Code): Query[Country] =
      Query(sql"select code, name from country where code = $c")

  }

  def dbProgram[F[_]: Sync](log: Logger[F])(c: Connection[F]): F[Unit] =
    for {
      _  <- c.countryStream.to(c.countrySink).compile.drain
      _  <- log.info("Doing other work inside F")
      cs <- c.countriesByCode(Code("FRA"))
      _  <- log.info(cs.toString)
    } yield ()


  def mainProgram[F[_]: Sync](xa: Transactor[F]): F[Unit] =
    for {
      _ <- xa.rts.enter
      _ <- xa.log.info("Starting up.")
      _ <- xa.transact(dbProgram[F](xa.log)(_))
      _ <- xa.log.info(s"Done.")
    } yield ()

  def transactor[F[_]: Async]: Transactor[F] =
    Transactor[F](
      Interpreter(
        RTS.default,
        Logger.getLogger("test")
      ),
      Strategy.transactional,
      Connector.fromDriverManager(
        "org.postgresql.Driver",
        "jdbc:postgresql:world",
        "postgres",
        ""
      )
    )

  def main(args: Array[String]): Unit = {

    val xa = transactor[IO]

    (System.setProperty(s"org.slf4j.simpleLogger.log.${xa.log.underlying.getName}", "trace"), ())._2

    xa.log.underlying.info(s"main <enter>")
    mainProgram(xa).unsafeRunSync
    xa.log.underlying.info(s"main <exit>")
  }

}

