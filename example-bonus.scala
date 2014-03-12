import akka.actor._
import akka.routing._

object Main extends App{

  var loggingActor = new ActorLog();
  override def main(args: Array[String]): Unit = {
    calculate(nbOfWorkers = 100, nbOfElements = 24, nbOfMessages = 1000)
  }

  sealed trait PiMessage

  case object Calculate extends PiMessage

  case class Work(start: Int, nbOfElements: Int,finish:Int) extends PiMessage

  case class Result(value: Int) extends PiMessage


  class Worker extends Actor {
   var workLog = new ActorLog();
    workLog.creatingLogFile("Created", self.toString)
    def receive = {
      case Work(start, nbOfElements,finish) =>
      workLog.writeToLogFile("Work("+start+","+nbOfElements+")", "Recd", sender.toString)	
       var value = calculatePiFor(start, nbOfElements)
        if(value!=0){
          //workLog.writeToLogFile("Sent:(Result(:$value)", "Sent", )
      sender ! Result(value)
        }
      else if(start == finish)
        sender ! Result(-1)
    }

    def calculatePiFor(start: Int, nbOfElements: Int): Int = {
      var sum = 0
        for (j <- start to nbOfElements){
          sum += j*j
          
        }
      //if(start < 100) println(start + " " + nbOfElements + " " + sum)
        var sRoot: Int = Math.sqrt(sum).toInt
        if ((sRoot * sRoot) == sum){
          /*println (s"for $nbOfElements starting from $start : ")
          println(s"$sRoot * $sRoot = $sum")*/
          start
        }
        else {0}
    }
  }

  class Master (
    nbOfWorkers: Int,
    nbOfMessages: Int,
    nbOfElements: Int
  ) extends Actor {

    var nbOfResults: Int = 0
    var start: Long = 0
    var loggingActor = new ActorLog();
    val router = context.actorOf(
      Props[Worker].withRouter(RoundRobinRouter(nbOfWorkers))
    )
    loggingActor.creatingLogFile("Created", self.toString)
        
        for (i <- 1 to nbOfMessages) {
          //if(i<100) println(s"Sending : $i")
          //loggingActor.writeToLogFile("Sent:", "Sent", router.toString)
          router ! Work(i, i + nbOfElements - 1,nbOfMessages)
          //context.system.shutdown()
        }

def receive = {
      case Result(value) => {
        loggingActor.writeToLogFile("Result("+value+")", "Recd", sender.toString)
        if(value > 0) {
          //println(s"Answer : $value")
        }
        else {
          context.system.shutdown()
        }
      }
    }

    override def preStart() {
      start = System.currentTimeMillis
    }

    override def postStop() {
      println(
        "\n\tCalculation time: \t%s millis"
        .format(System.currentTimeMillis - start))
    }
  }


  def calculate(nbOfWorkers: Int, nbOfElements: Int, nbOfMessages: Int) {

    val system = ActorSystem("LSP")
    val master = system.actorOf(Props(
    new Master(nbOfWorkers, nbOfMessages, nbOfElements)))
  }
}