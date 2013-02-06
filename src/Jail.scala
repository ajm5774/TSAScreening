import akka.actor.Actor

class Jail (val numLines : Int) extends Actor{
  
	private var patrons = List[String]()
	private var numShutdown = 0;
	
	def receive = {
	  case name : String =>
	    println(name + " has entered the jail.")
	    patrons ::= name
	  case Kill =>
	    numShutdown += 1
	    if(numShutdown == numLines){
	      self.stop()
	    }
	}
	
	override def postStop = {
	  println("Jail closed for the day")
     for(patron <- patrons){
       println(patron + " has left for a permanent detention facility.")
     }
  }
}