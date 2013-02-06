import akka.actor.Actor
import akka.actor.PoisonPill
import akka.actor.ActorRef
import scala.util.Random

class BodyScan (val securityStation: ActorRef, val lineNumber: Int) extends Actor{
  private val FAILURE_RATE = 20

  def receive = {
    case (name : String) =>
      println(name + " arrived at body scan " + lineNumber)
      var rand = new Random()
	  if(rand.nextInt(99)+1 > FAILURE_RATE){
		  println(name + " passed security at body scan " + lineNumber)
		  securityStation ! SecurityStatus(name,false,true)
	  }
	  else{
		  println(name + " failed security at body scan " + lineNumber)
		  securityStation ! SecurityStatus(name,false,false)
	  }
  }
  override def postStop = {
     println("Body Scan " + lineNumber + " closed for the day")
     securityStation ! Kill
  }
}