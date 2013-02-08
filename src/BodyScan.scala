import akka.actor.Actor
import akka.actor.PoisonPill
import akka.actor.ActorRef
import scala.util.Random

class BodyScan (val securityStation: ActorRef, val lineNumber: Int) extends Actor{
  private val FAILURE_RATE = 20

  def receive = {
    case (name : String) =>
      println(name + " arrived at body scan " + lineNumber)
      
      //decides if the patron fails inspection. fail rate of FAILURE_RATE
      var rand = new Random()
	  if(rand.nextInt(99)+1 > FAILURE_RATE){
		  println(name + " passed security at body scan " + lineNumber+
		      " and is sent to the security station")
		  securityStation ! SecurityStatus(name,false,true)
	  }
	  else{
		  println(name + " failed security at body scan " + lineNumber+
		      " and is sent to the security station")
		  securityStation ! SecurityStatus(name,false,false)
	  }
  }
  
  //Sends a kill message to the next actor in the sequence (security station)
  override def postStop = {
     println("Body Scan " + lineNumber + " closed for the day")
     securityStation ! Kill
  }
}