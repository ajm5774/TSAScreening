import akka.actor.Actor
import akka.actor.ActorRef
import scala.collection.mutable.HashMap

class SecurityStation (val jail: ActorRef, val lineNumber: Int) extends Actor{
	var passengerStatus = new HashMap[String, Boolean]
  def receive = {
    case SecurityStatus(name,baggage, status)=>
      
      if(baggage){
    	  println(name + "'s baggage arrived at the security station")  
      }
      else{
      	  println(name + " arrived at the security station") 
      }
      
      if (passengerStatus.contains(name)){
        if(passengerStatus(name) && status){
          println(name + " passed the TSA screening")
        }
        else{
          println(name + " failed the TSA screening")
          jail ! name
        }
      }
      else{
        passengerStatus + name -> status
      }
  }
}