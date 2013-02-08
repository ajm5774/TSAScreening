import akka.actor.ActorRef
import akka.actor.PoisonPill
import akka.actor.Actor

class Queue(val bodyScan: ActorRef, val baggageScan: ActorRef,
  val lineNumber: Int) extends Actor {

  def receive = {
    case name: String =>
      println(name + " arrived at queue " + lineNumber + ".")

      println("Sending " + name + " to body scan " + lineNumber + ".")
      bodyScan ! name

      println("Sending " + name + "'s baggage to baggage scan " + lineNumber + ".")
      baggageScan ! name
  }

  //sends a kill message to the next actors in the sequence (both scans)
  override def postStop = {
    println("Queue " + lineNumber + " closed for the day.")
    bodyScan ! PoisonPill
    baggageScan ! PoisonPill
  }

  override def preStart = {
    println("Queue " + lineNumber + " is started.")
  }
}