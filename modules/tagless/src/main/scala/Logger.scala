// Copyright (c) 2013-2018 Rob Norris and Contributors
// This software is licensed under the MIT License (MIT).
// For more information see LICENSE or https://opensource.org/licenses/MIT

package doobie.tagless

import cats.effect.Sync
import org.slf4j.{ Logger => JLogger, LoggerFactory }

@SuppressWarnings(Array("org.wartremover.warts.DefaultArguments"))
final case class Logger[F[_]: Sync](
  name:        String,
  prefixWidth: Int     = 30,
  colored:     Boolean = true
) {

  val underlying: JLogger =
    LoggerFactory.getLogger(name)

  private val colors: Array[String] = {
    import Console._
    val cs = Array(RED, GREEN, BLUE, YELLOW, MAGENTA, CYAN)
    cs ++ cs.map(BOLD + _)
  }

  private def color(subject: AnyRef): String =
    colors(System.identityHashCode(subject) % colors.length)

  private def format(subject: AnyRef, message: String): String = {
    val cname  = subject.getClass.getSimpleName // TODO: make this more robust
    val hash   = System.identityHashCode(subject).toHexString.padTo(8, ' ')
    val prefix = s"$hash $cname".padTo(prefixWidth, ' ').take(prefixWidth)
    val done   = s"$prefix $message"
    if (colored) s"${color(subject)}$done${Console.RESET}"
    else done
  }

  def trace(subject: AnyRef, message: => String): F[Unit] =
    Sync[F].delay(unsafe.trace(subject, message))

  def info(subject: AnyRef, message: => String): F[Unit] =
    Sync[F].delay(unsafe.info(subject, message))

  object unsafe {

    def trace(subject: AnyRef, message: => String): Unit =
      if (underlying.isTraceEnabled)
        underlying.trace(format(subject, message))

    def info(subject: AnyRef, message: => String): Unit =
      if (underlying.isInfoEnabled)
        underlying.info(format(subject, message))

  }

}


