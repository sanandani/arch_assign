

1. How to start system?
a) Open a command line prompt in this directory 
b) Compile all of the applications by typing: javac *.java
c) Type "rmic MessageManager" to build the MessageManager interface stubs.
d) Make sure that port 1099 is available on your system.
e) If you are running on a Mac, please run the shell scripts ("sh filname.sh") in sequence as indiated by file titles
   E.g. "1_StartMessageManager.sh" means this file should be run in 1st place;
   If you are running on Windows, please run the batch file ("filename.bat") in sequence as indicated by file titles
   E.g. "1_EMStart.bat" means this file should be run first;


When shutting down a MessageManager, exit the MessageManager command window and also exit the MessageManager registry command window. If you fail to remember to shut down the MessageManager registry, you will not be able to start the MessageManager again.



System B
1. How to control the system?
a) After starting up the system, the Fire is simulated randomly in atleast 2.5 seconds.
b) Once the fire is sensed, user can turn off both Fire Alarm* and Sprinkler (before it starts wihtin 10 seconds) or ignore the Sprinkler action from the Console.
c) After 10 seconds are passed from the time Fire was sensed, the sprinklers turn ON automatically and the user can anytime turn Off the Fire Alarm* and Sprinkler.

*We assume that the at the times of Fake Fire Alarm, the user would want to turn off the Fire Alarm as well. This also makes it explicit that if the User turns off the fire alarm, it would mean that the Sprinklers should also go off.