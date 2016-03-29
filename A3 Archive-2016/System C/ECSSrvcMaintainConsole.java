/******************************************************************************************************************
* File:ECSSrvcMaintainConsole.java
* Course: 17655
* Project: Assignment 3
* Copyright: Copyright (c) 2009 Carnegie Mellon University
* Versions:
*   1.0 February 2009 - Initial rewrite of original assignment 3 (ajl).
*
* Description: This class is the maintenance console for the museum environmental control system. This process consists of two
* threads. The ECSrvcMaintainMonitor object is a thread that is started that is responsible for the SrvcMonitoring and control of
* the museum environmental systems. The main thread provides a text interface for the user to:
*  - View the registered equipments
*  - View the last seen of all the registered components
*  - Change the time of the heartbeat
* and humidity ranges, as well as shut down the system.
*
* Parameters: None
*
* Internal Methods: None
*
******************************************************************************************************************/
import TermioPackage.*;
import MessagePackage.*;

public class ECSSrvcMaintainConsole
{
    public static void main(String args[])
    {
        Termio UserInput = new Termio();    // Termio IO Object
        boolean Done = false;               // Main loop flag
        String Option = null;               // Menu choice from user
        Message Msg = null;                 // Message object
        ECSrvcMaintainMonitor SrvcMonitor = null;          // The environmental control system ECSrvcMaintainMonitor
        

        /////////////////////////////////////////////////////////////////////////////////
        // Get the IP address of the message manager
        /////////////////////////////////////////////////////////////////////////////////

        if ( args.length != 0 )
        {
            // message manager is not on the local system

            SrvcMonitor = new ECSrvcMaintainMonitor(args[0]);

        } else {

            SrvcMonitor = new ECSrvcMaintainMonitor();

        } // if


        // Here we check to see if registration worked. If ef is null then the
        // message manager interface was not properly created.

        if (SrvcMonitor.IsRegistered() )
        {
            SrvcMonitor.start(); // Here we start the SrvcMonitoring and control thread

            while (!Done)
            {
                // Here, the main thread continues and provides the main menu

                System.out.println( "\n\n" );
                System.out.println( "Service Maintenance Control System (ECS) Command Console: \n" );

                if (args.length != 0)
                    System.out.println( "Using message manager at: " + args[0] + "\n" );
                else
                    System.out.println( "Using local message manager \n" );

                System.out.println( "Select an Option: \n" );
                System.out.println( "1: Show all the registered equipments" );
                System.out.println( "2: Show last seen of all registered equipments" );
                System.out.println( "3: Change the waiting time of heartbeat (Time after which device should be assumed not responding)" );
                System.out.println( "X: Stop System\n" );
                System.out.print( "\n>>>> " );
                Option = UserInput.KeyboardReadString();

                //////////// option 1 ////////////
                // If the option 1 is selected it will display all the devices registered in the musuem
                if ( Option.equals( "1" ) )
                {
                    SrvcMonitor.showRegisteredProcesses();
                    
                } // if
                
                //////////// Option 2 ////////////
                // If the option 2 is selected it will display last seen for all the devices registered in the musuem
                if ( Option.equals( "2" ) )
                {
                    SrvcMonitor.showLastSeenOfDevices();
                    
                } // if
                
                //////////// Option 3 ////////////
                // If the option 3 is selected it will display last seen for all the devices registered in the musuem
                if ( Option.equals( "3" ) ){
                    boolean Error = true;
                    // Here we get the high humidity range

                    while (Error)
                    {
                        // Asking the user for the heartbeat time
                        System.out.print( "\nEnter the time in seconds>>>  " );
                        Option = UserInput.KeyboardReadString();

                        if (UserInput.IsNumber(Option))
                        {
                            Error = false;
                            // Setting the new waiting time as overriden by the user
                            ECSrvcMaintainMonitor.waitingTime = Long.valueOf(Option).longValue() * 1000;
                        } else {

                            System.out.println( "Not a number, please try again..." );

                        } // if

                    } // while
                }
                

                //////////// option X ////////////

                if ( Option.equalsIgnoreCase( "X" ) )
                {
                    // Here the user is done, so we set the Done flag and halt
                    // the environmental control system. The SrvcMonitor provides a method
                    // to do this. Its important to have processes release their queues
                    // with the message manager. If these queues are not released these
                    // become dead queues and they collect messages and will eventually
                    // cause problems for the message manager.

                    SrvcMonitor.Halt();
                    Done = true;
                    System.out.println( "\nConsole Stopped... Exit SrvcMonitor mindow to return to command prompt." );
                    SrvcMonitor.Halt();

                } // if

            } // while

        } else {

            System.out.println("\n\nUnable start the SrvcMonitor.\n\n" );

        } // if

    } // main

} // ECSConsole
