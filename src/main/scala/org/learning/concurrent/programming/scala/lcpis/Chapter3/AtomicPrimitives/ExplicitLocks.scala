package org.learning.concurrent.programming.scala.lcpis.Chapter3.AtomicPrimitives

import java.util.concurrent.atomic.AtomicReference
import scala.annotation.tailrec

class ExplicitLocks extends App{

  sealed trait State
  class Idle extends State
  class Creating extends State
  class Copying(val n: Int) extends State
  class Deleting extends State

  class Entry(val isDir: Boolean){
    var state: AtomicReference[State] = new AtomicReference[State](new Idle)
  }

  @tailrec
  def prepareForDelete(entry: Entry): Boolean =
    entry.state.get() match{
      case i: Idle =>
        if(entry.state.compareAndSet(i,new Deleting)) true
        else prepareForDelete(entry)
      case cr: Creating => false
      case cp: Copying => false
      case d: Deleting => false
    }

}
