Distributed Programming

Running the Code
Project4-bonus
The steps to run the project are as follows
1. Include all the source files in a single folder
2. Compile the file actorlog.scala uing the command scalac ActorLog.scala
3. Compile the example file using the command scalac example-bonus.scala
4. All the log files are present inside the folder Logs.

What is Logging
In this project we have implemented the logging facility for actors. 
Information about what the actor send to the other actors is logged. 
The log contains text information and the logging information is stored as one log file per actor. 
There is a primary log file that contains the results of the program execution. The log files are named in the format Log_<ActorRef>.log.
From this we can see that there is a log file for every actor.

How Logging was integrated with the actors

A singleton object of the ActorLog class was created in the LSP.scala. 
This object is referenced by al the actors. 
All the information on what is logged is processed in the member functions of the class ActorLog in the file ActorLog.scala. 
Hence there is not much logging code that has to be integrated into every actor except a call to the functions of the call.

How to interpret the log file

Each log file consists of three types of logging

1) 2013/11/22 18:24:20 Created Actor[akka://LSP/user/$a#388012808]
2) 2013/11/22 18:24:20 Recd : Result(1) from: Actor[akka://LSP/user/$a/$a/$a#-2113806183]

The first part of the log line consists of the time when the line was logged.

The second part of the log line consists of the event that the actor encountered. These events are of 3 types

1) Created – Indicates that the actor was created
2) Recd: Result(1) – Indicates that the actor received the message and also the message

The third part consists of the message keeps track of the ActorRef.
