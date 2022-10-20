package org.learning.concurrent.programming.scala.lcpis.Chapter3.ExecutionContext

import org.learning.concurrent.programming.scala.lcpis.log

import java.util.concurrent.{Executor, ForkJoinPool}

object ExecutorsCreate extends App {

  val executor: Executor = new ForkJoinPool(2)
  executor.execute(() => log("This task is run asynchronously"))

  Thread.sleep(5000)

}
