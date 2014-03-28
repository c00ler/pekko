/**
 * Copyright (C) 2014 Typesafe Inc. <http://www.typesafe.com>
 */
package akka.stream

import akka.testkit.AkkaSpec
import akka.stream.testkit.ScriptedTest
import scala.collection.immutable
import scala.concurrent.forkjoin.ThreadLocalRandom.{ current ⇒ random }

class StreamGroupedSpec extends AkkaSpec with ScriptedTest {

  val genSettings = GeneratorSettings(
    initialInputBufferSize = 2,
    maximumInputBufferSize = 16,
    initialFanOutBufferSize = 1,
    maxFanOutBufferSize = 16)

  "A Grouped" must {

    "group" in {
      def script = Script((1 to 20) map { _ ⇒ val x, y, z = random.nextInt(); Seq(x, y, z) -> Seq(immutable.Seq(x, y, z)) }: _*)
      (1 to 50) foreach (_ ⇒ runScript(script, genSettings)(_.grouped(3)))
    }

  }

}