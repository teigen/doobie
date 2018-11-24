// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless

import cats.effect._
import cats.implicits._
import doobie.syntax.string._
import fs2.{ Stream, Sink }

// WE NEED TO THREAD THE LOGGER THROUGH READ/GET AND WRITE/PUT SO WE CAN SEE WHAT
// ACTUAL QUERY ARGS AND COLUMN READS ARE. IT'S PROBABLY OK TO ALWAYS LOG ARGS
// IF WE'RE NOT IMPLEMENTING A SINK, BUT FOR SINKS AND RESULTSETS WE CAN ONLY LOG
// AT A VERY LOW LEVEL BECAUSE IT'S THE PRIMARY PERF HOTSPOT.

@SuppressWarnings(Array("org.wartremover.warts.ToString"))
object Test extends RTS.App {
  import CountryRepo._

  def transactor[F[_]: Async: RTS]: Transactor[F] =
    Transactor(
      Interpreter.default,
      Strategy.transactional,
      Connector.fromDriverManager(
        "org.postgresql.Driver",
        "jdbc:postgresql:world",
        "postgres",
        ""
      )
    )

  def dbProgram[F[_]: Sync](c: CountryRepo[F], log: Logger[F]): F[Unit] =
    for {
      _  <- c.stream.to(c.sink).compile.drain
      _  <- log.trace(this, "Doing some work inside F.")
      cs <- c.selectByCode(Code("FRA"))
      _  <- log.trace(this, s"The answer was $cs")
    } yield ()

  def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- IO(System.setProperty(s"org.slf4j.simpleLogger.log.doobie-rts", "trace"))
      _ <- ioRTS.log.trace(this, "Starting up.")
      xa = transactor[IO]
      _ <- xa.transact(c => dbProgram(CountryRepo.fromConnection(c), ioRTS.log))
      _ <- ioRTS.log.trace(this, s"Done.")
    } yield ExitCode.Success

}

final case class Code(code: String)
final case class Country(code: Code, name: String)

trait CountryRepo[F[_]] {
  def stream: Stream[F, Country]
  def sink: Sink[F, Country]
  def selectByCode(k: Code): F[List[Country]]
}

object CountryRepo {

  def fromConnection[F[_]](c: Connection[F]): CountryRepo[F] =
    new CountryRepo[F] {

      val stream: Stream[F, Country] =
        c.stream(Statements.countries, 50)

      val sink: Sink[F, Country] =
        c.sink(Statements.up)

      def selectByCode(k: Code): F[List[Country]] =
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

}