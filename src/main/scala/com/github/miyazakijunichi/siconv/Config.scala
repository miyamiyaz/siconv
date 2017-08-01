package com.github.miyazakijunichi.siconv

import java.io.File
import java.nio.charset.Charset

case class Config(inFiles: Seq[File] = Seq(),
                  outFile: Option[File] = None,
                  fromEncoding: Option[Charset] = None,
                  toEncoding: Option[Charset] = None)