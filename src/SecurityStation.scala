import akka.actor.Actor
import akka.actor.PoisonPill
import akka.actor.ActorRef
import scala.collection.mutable.HashMap

class SecurityStation (val jail: ActorRef, val lineNumber: Int) extends Actor{
	private var passengerStatus = new HashMap[String, Boolean]
	
	private var numScansClosed = 0;
	
  def receive = {
    case SecurityStatus(name,baggage, status)=>
      
      //prints a different message if its baggage that arrives or not
      if(baggage){
    	  println(name + "'s baggage arrived at the security station")  
      }
      else{
      	  println(name + " arrived at the security station") 
      }
      
      /*
       * Checks if the baggage or passenger has come to the security station.
       *  already. If it hasnt then it records that the baggage or passenger 
       *  arrived. If the baggage or passenger arrived previously, then the
       *  security status is processed. The status is failed if either the
       *  baggage or passenger failed their corresponding scan.
       */
      if (passengerStatus.contains(name)){
        if(passengerStatus(name) && status){
          println(name + " passed the TSA screening and is leaving the security area")
        }
        else{
          println(name + " failed the TSA screening and is sent to jail")
          jail ! name
        }
      }
      else{
        passengerStatus += name -> status
      }
    
      //the kill message stops the actor if both the scans stop.
    case Kill =>
      numScansClosed += 1
      if(numScansClosed ==2){
        self.stop()
      }
  }
	
  //sends a kill message to the next actor in the line (jail)
  override def postStop = {
    println("Security Station " + lineNumber + " closed for the day")
     jail ! Kill
  }
}