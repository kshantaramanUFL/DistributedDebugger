import java.io._
import java.util._
import  java.text._

class ActorLog {

  var writer: PrintWriter = null;
  var name: String = null
  val dateFormat:DateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
  val date:Date  = new Date();
  def creatingLogFile(logLine : String,a: String)
  {
    var actorname : String = a.subSequence(a.lastIndexOf("/")+1,a.length()-1).toString()
	actorname=actorname.replace("#", "_")
	var dir : File = new File("Logs")
    dir.mkdir()
	var direc : String = System.getProperty("user.dir")
	direc = direc.replace("\\", "\\\\")
    name = direc + "\\\\Logs\\\\Log_" + actorname + ".txt"
	writer = new PrintWriter(name);
	writer.println(dateFormat.format(date) + "\t" + logLine + "\t" + a)
	writer.close();
  }
	def writeToLogFile(logLine : String,flag : String,a: String){
		writer = new PrintWriter(new BufferedWriter(new FileWriter(name,true))); 
		if(flag.equals("Sent"))
		{
		  writer.println(dateFormat.format(date) + "\t" + "Sent : " + logLine + "\t" + "to : " + a)
		}
		else
		{
		  writer.println(dateFormat.format(date) + "\t" + "Recd : " + logLine + "\t" + "from: " + a)
		}
		writer.close()
	}	
}