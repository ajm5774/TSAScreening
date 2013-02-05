import akka.actor.ActorRef
import akka.actor.Actor
import akka.actor.PoisonPill

object Main {
	private final val NUM_PASSENGERS = 10
	private final val NUM_LINES = 3
	
	
  def main (args : Array[String]) : Unit = {
	//creating jail
    var jail = Actor.actorOf(new Jail(NUM_PASSENGERS)).start();
    
    //creating security lines (queues)
    var lines = new Array[ActorRef](NUM_LINES)
    for (i <- 0 until NUM_LINES){
      
      //creating security station
	  var securityStation = Actor.actorOf(new SecurityStation(jail,i)).start()
	  
	  //creating scan layer
	  var bodyScan = Actor.actorOf(new BodyScan(securityStation,i)).start()
	  var baggageScan = Actor.actorOf(new BaggageScan(securityStation,i)).start()
	  
	  //creating queue
	  var queue = Actor.actorOf(new Queue(bodyScan,baggageScan,i)).start()
	  
	  //add to the list of lines
	  lines(i) = queue
	} 
    
    //creating document check
    var documentCheck = Actor.actorOf(new DocumentCheck(lines)).start()
    
    
    //send all the passengers to the document check
    for(i <- 0 until NUM_PASSENGERS){
      documentCheck ! ("Passenger " + i)
    }
    
    //kill all actors
    documentCheck ! PoisonPill
  }
}