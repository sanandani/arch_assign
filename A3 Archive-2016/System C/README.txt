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


2. To see a list of registered systems, in the Service Maintenance ECS Monitoring Console, 
enter user option 1.

3. To simulate a not responding device, quit the Java application of that device. 

4. After 10 seconds(default value), in the Service Maintenance ECS Monitoring Console, you should be able to see messages about the non-responding device.