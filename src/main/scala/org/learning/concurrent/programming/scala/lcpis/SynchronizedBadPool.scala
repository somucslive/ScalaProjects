package org.learning.concurrent.programming.scala.lcpis

import scala.collection.mutable

//Leads to busy waiting of worker thread
object SynchronizedBadPool extends App {
  val pool = mutable.Queue[() => Unit]()
  val worker = new Thread {
    def poll(): Option[() => Unit] = pool.synchronized {
      if (pool.nonEmpty) Some(pool.dequeue())
      else None
    }

    override def run(): Unit = while (true)
      poll() match {
        case Some(method) => method()
        case None =>
      }
  }

  worker.setName("Worker")
  worker.setDaemon(true)
  worker.start()

  def asynchronous(fn: () => Unit): Unit = pool.synchronized {
    pool.enqueue(fn)
  }

  asynchronous(() => println("Hello "))
  asynchronous(() => println("World"))
  Thread.sleep(5000)
}
