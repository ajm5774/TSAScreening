import akka.actor.ActorRef
import akka.actor.PoisonPill
import akka.actor.Actor
import scala.util.Random
import scala.Array
import scala.Int

class DocumentCheck(val queues: Array[ActorRef]) extends Actor {
  private val FAILURE_RATE = 20
  private var inc = 0

  def receive = {

    case name: String =>
      println(name + " arrived at the document check.")

      //decides if the patron fails document check. fail rate of FAILURE_RATE
      var rand = new Random()
      if (rand.nextInt(99) + 1 > FAILURE_RATE) {
        println(name + " passed the document check and is sent to queue " + inc + ".")
        queues(inc) ! name
        inc += 1
        inc %= queues.length
      } else {
        println(name + " failed the document check and is not passed to a queue.")
      }
  }

  //Sends a kill message to the next actors in the sequence (all queues)
  override def postStop = {
    println("Document Check closed for the day.")
    for (ref <- queues) {
      ref ! PoisonPill
    }
  }

  override def preStart = {
    println("Document Check is started.");
  }
}