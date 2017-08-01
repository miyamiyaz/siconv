package com.github.miyazakijunichi.siconv

import java.io._
import java.nio.charset.Charset
import java.nio.file.Files

import scopt.OptionParser

import scala.util.Try

object Main extends App {
  val parser = new OptionParser[Config]("siconv") {
    head("siconv", "v0.0.1")

    opt[String]('f', "from-encoding")
      .required()
      .action((x, c) => c.copy(fromEncoding = Option(x).flatMap(v => Try(Charset.forName(v)).toOption)))
      .text("from encoding")
    opt[String]('t', "to-encoding")
      .required()
      .action((x, c) => c.copy(toEncoding = Option(x).flatMap(v => Try(Charset.forName(v)).toOption)))
      .text("to encoding")

    help("help").text("prints this usage text")
    version("version").text("prints version")

    opt[File]('o', "output")
      .valueName("<file>")
      .action((x, c) => c.copy(outFile = Option(x)))
      .text("output file name")
    arg[File]("<file>...").unbounded().optional().action( (x, c) =>
      c.copy(inFiles = c.inFiles :+ x) ).text("optional input files")
  }

  // parser.parse returns Option[C]
  parser.parse(args, Config()) match {
    case Some(config) =>
      if (config.fromEncoding.isEmpty) {
        print("invalid from encoding")
        sys.exit(1)
      }
      if (config.toEncoding.isEmpty) {
        print("invalid to encoding")
        sys.exit(1)
      }
      val in: Seq[BufferedReader] = config.inFiles.map(file => Files.newBufferedReader(file.toPath, config.fromEncoding.get)) match {
        case s if s.length == 0 => Seq(new BufferedReader(new InputStreamReader(System.in, config.fromEncoding.get)))
        case s if s.length != 0 => s
      }
      val out: BufferedWriter = config.outFile match {
        case Some(file) => Files.newBufferedWriter(file.toPath, config.toEncoding.get)
        case None => new BufferedWriter(new OutputStreamWriter(System.out, config.toEncoding.get))
      }

      in.foreach(e => {
        Iterator
          .continually(e.read())
          .takeWhile(_ != -1)
          .foreach(out.write(_))
        e.close()
      })
      out.close()

    case None =>
    // arguments are bad, error message will have been displayed
  }
}
