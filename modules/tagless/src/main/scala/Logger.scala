// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless

import cats.effect.Sync
import org.slf4j.{ Logger => JLogger, LoggerFactory }

// TODO: replace with Log4Cats, assuming it gives access to underlying

final case class Logger[F[_]: Sync](underlying: JLogger) {

  def trace(s: => String): F[Unit] =
    Sync[F].delay {
      if (underlying.isTraceEnabled)
        underlying.trace(s)
    }

  def info(s: => String): F[Unit] =
    Sync[F].delay {
      if (underlying.isInfoEnabled)
        underlying.info(s)
    }

  // IT WOULD BE NICE HERE IF WE COULD KEEP A PER-TRANSACTION MAP FROM OBJECT HANDLES TO ANSI
  // COLORS SO WE COULD SHOW EACH OBJECT AS ITS OWN COLOR IN THE LOG. IN PRACTICE THERE WILL
  // RARELY BE MORE THAN THREE.

}
object Logger {
  def getLogger[F[_]: Sync](name: String): Logger[F] =
    Logger(LoggerFactory.getLogger(name))
}