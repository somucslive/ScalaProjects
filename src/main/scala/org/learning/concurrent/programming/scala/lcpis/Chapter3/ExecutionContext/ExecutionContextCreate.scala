package org.learning.concurrent.programming.scala.lcpis.Chapter3.ExecutionContext

import org.learning.concurrent.programming.scala.lcpis.log

import java.util.concurrent.{ExecutorService, ForkJoinPool}
import scala.concurrent.ExecutionContext

object ExecutionContextCreate extends App {

  val forkPool: ExecutorService = new ForkJoinPool(2)
  val exCntxt: ExecutionContext = ExecutionContext.fromExecutor(forkPool)
  exCntxt.execute(() =>
    log("Running on the user Created Scala specific[Not in Java] ExecutionContext"))
  forkPool.shutdown()
  //  forkPool.awaitTermination(1000,)
}
