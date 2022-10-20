package org.learning.concurrent.programming.scala.lcpis.Chapter3.AtomicPrimitives

import java.util.concurrent.atomic.{AtomicLong, AtomicReference}
import scala.concurrent.ExecutionContext
import org.learning.concurrent.programming.scala.lcpis._

import scala.annotation.tailrec

object AtomicUid extends App {

  private var uid: AtomicLong = new AtomicLong(0L)
//  private var uidRef: T

  def getUniqueId(): Long = {
    uid.incrementAndGet()
  }

  def compareAndSet(ov: Long, nv: Long): Boolean = this.synchronized{
    if(uid.get == ov) {
      uid.set(nv)
      true
    } else false
  }

  @tailrec
  def getUniqueIdAtomic(): Long = {
    val oldUid = uid.get()
    val newUid = oldUid + 1
    if(uid.compareAndSet(oldUid,newUid)) uid.get()
    else getUniqueIdAtomic()
  }

  ExecutionContext.global.execute{
    () => (0 to 5).foreach(_ => log(getUniqueId().toString))
  }

  (0 to 5).foreach(_ => log(getUniqueId().toString))
}
