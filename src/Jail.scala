import akka.actor.ActorRef
import akka.actor.Actor


class Jail(val queues : Array[ActorRef]) extends Actor{
  
  def receive = {
    case()=>
      println("Something was sent to Jail")
  }
}
