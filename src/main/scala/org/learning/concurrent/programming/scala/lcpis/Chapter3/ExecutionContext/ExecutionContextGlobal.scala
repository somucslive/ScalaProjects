package org.learning.concurrent.programming.scala.lcpis.Chapter3.ExecutionContext

import org.learning.concurrent.programming.scala.lcpis.log

import java.util.concurrent.Executor
import scala.concurrent.ExecutionContext

object ExecutionContextGlobal extends App {

  val ectx: Executor = ExecutionContext.global
  ectx.execute {
    new Runnable {
      def run(): Unit =
        log("Running on the Global Scala specific[Not in Java] ExecutionContext")
    }
  }
  Thread.sleep(5000)
}
