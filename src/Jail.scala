import akka.actor.Actor

class Jail (val jailSize : Int) extends Actor{
  
	private var patrons = List[String]()
	
	def receive = {
	  case name : String =>
	    println(name + " has entered the jail.")
	    patrons ::= name
	}
	
	override def postStop = {
     for(patron <- patrons){
       println(patron + " has left for a permanent detention facility.")
     }
  }
}