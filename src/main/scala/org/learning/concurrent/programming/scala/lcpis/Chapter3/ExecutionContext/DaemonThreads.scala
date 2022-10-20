package org.learning.concurrent.programming.scala.lcpis.Chapter3.ExecutionContext

import cats.Traverse

import java.util.concurrent.{ExecutorService, Executors}
import scala.concurrent.{ExecutionContext, Future}

object DaemonThreads extends App {

  val threadPool: ExecutorService = Executors.newFixedThreadPool(2)
  implicit val exCntxt = ExecutionContext.fromExecutor(threadPool)

  def sleepAndReturnInput(input: Seq[Int]): Seq[Int] = {
    val sleepingTime = input.size * 1000
    println(s"Sleeping during ${sleepingTime}")
    Thread.sleep(sleepingTime)
    input
  }

  val resultAbc = Future(sleepAndReturnInput((0 to 3)))
  val resultDefgh = Future(sleepAndReturnInput((0 to 2)))
  val allFutures = Future.sequence(Seq(resultAbc, resultDefgh))

  val test = Traverse[Seq].sequence(Seq(resultAbc, resultDefgh))


  allFutures.flatMap(allFuturesResults => {
    Future(allFuturesResults.flatten)
  }).foreach(fetchedLetters => {
    println(s"Got mapped numbers=${fetchedLetters}")
  })

  threadPool.shutdown()
//  threadPool.awaitTermination(3000, 1000.Seconds)
  //  val testThread = new Thread(() => {
  //    Thread.sleep(5000)
  //    println("sleeping done")
  //  })
  //  testThread.start()
}
