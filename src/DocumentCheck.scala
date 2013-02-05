import akka.actor.ActorRef
import akka.actor.PoisonPill
import akka.actor.Actor
import scala.util.Random
import scala.Array
import scala.Int

class DocumentCheck (val queues : Array[ActorRef]) extends Actor{
	private val FAILURE_RATE = 20
	private var inc = 1
  
  def receive = {

    case name : String =>
      println(name + " arrived at the document check")
	  var rand = new Random()
	  if(rand.nextInt(99)+1 > FAILURE_RATE){
		println(name + " passed the document check")
	    queues(inc) ! name
	    inc += 1
	    inc %= queues.length
	  }
	  else{
	    println(name + " failed the document check")	  
	  }
  }
	
	override def postStop = {
	  for(ref <- queues){
	    ref ! PoisonPill
	  }
	}
}