import akka.actor.ActorRef
import akka.actor.Actor

class Queue (val bodyScan: ActorRef, val baggageScan: ActorRef,
    val lineNumber: Int) extends Actor{
	
	def receive = {
	  case name: String =>
	  	println(name + "Arrived at queue " + lineNumber)
	  	
	  	println("Sending " + name + " to body scan " + lineNumber)
	  	bodyScan ! name
	  	
	  	println("Sending " + name + "'s baggage to baggage scan " + lineNumber)
	  	baggageScan ! name
	}
}