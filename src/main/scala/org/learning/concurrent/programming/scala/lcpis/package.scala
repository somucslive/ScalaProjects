package org.learning.concurrent.programming.scala

import scala.concurrent.ExecutionContext

package object lcpis {

  def log(msg: String): Unit =
    println(s"[Thread ${Thread.currentThread().getName}]: $msg")

  def execute(body: => Unit): Unit = ExecutionContext.global.execute {
    () => body
  }
}
