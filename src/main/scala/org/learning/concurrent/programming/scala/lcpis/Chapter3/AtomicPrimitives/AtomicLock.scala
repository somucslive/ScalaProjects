package org.learning.concurrent.programming.scala.lcpis.Chapter3.AtomicPrimitives

import org.learning.concurrent.programming.scala.lcpis.log

import java.lang.Thread
import java.util.concurrent.atomic.AtomicBoolean
import scala.concurrent.ExecutionContext

object AtomicLock extends App{

  private var lock: AtomicBoolean = new AtomicBoolean(false)
  private var count = 0
  //Looks like lock free, but leads to busy wait of other threads by the thread which sets lock to true
  //thus if the acquired thread is doing some slow IO, it will slow the other threads also
  //if slowness of one thread slows down the other threads too then its not lock free, implicitly its locked
  def mySynchronized(body: => Unit): Unit = {
    if(lock.compareAndSet(false,true))
      try{
        body
      }
      finally{lock.set(false)}
     else mySynchronized(body)
  }
  (0 to 10).foreach(n => ExecutionContext.global.execute(() => mySynchronized{
    count += 1
    log(count.toString)
  }))

    Thread.sleep(5000)
}
