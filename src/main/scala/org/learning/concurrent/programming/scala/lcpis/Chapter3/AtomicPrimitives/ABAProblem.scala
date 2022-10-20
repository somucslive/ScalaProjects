package org.learning.concurrent.programming.scala.lcpis.Chapter3.AtomicPrimitives

import java.util.concurrent.atomic.AtomicReference

object ABAProblem extends App{

  sealed trait State
  class Idle extends State
  class Creating extends State
  class Copying(var n: Int) extends State
  class Deleting extends State

  class Entry(val isDir: Boolean){
    var state: AtomicReference[State] = new AtomicReference[State](new Idle)
  }

  def releaseCopy(entry: Entry): Copying =
    entry.state.get() match{
      case cp: Copying =>
       val newState = if(cp.n == 1) new Idle else new Copying(cp.n -1)
       if(entry.state.compareAndSet(cp,newState)) cp
       else releaseCopy(entry)
    }

  def acquireCopy(entry: Entry,c: Copying): Entry =
    entry.state.get() match{
      case i: Idle =>
        val copy = new Copying(1)
        if(entry.state.compareAndSet(i,copy)) entry
        else acquireCopy(entry,c)
      case cp: Copying =>
        c.n = cp.n + 1
        if(entry.state.compareAndSet(cp,c)) entry
        else acquireCopy(entry,c)
    }
}
