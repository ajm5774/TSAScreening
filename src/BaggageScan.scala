import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.PoisonPill
import scala.util.Random

class BaggageScan (val securityStation: ActorRef, val lineNumber: Int) extends Actor{
  private val FAILURE_RATE = 20

  def receive = {
    case (name : String) =>
      println(name + " arrived at baggage scan " + lineNumber)
      var rand = new Random()
	  if(rand.nextInt(99)+1 > FAILURE_RATE){
		  println(name + " passed security at baggage scan " + lineNumber)
		  securityStation ! SecurityStatus(name,true,true)
	  }
	  else{
		  println(name + " failed security at baggage scan " + lineNumber)
		  securityStation ! SecurityStatus(name,true,false)
	  }
  }
  
  override def postStop = {
     securityStation ! PoisonPill
  }
}