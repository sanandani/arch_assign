/******************************************************************************************************************
* File:ECSConsole.java
* Course: 17655
* Project: Assignment 3
* Copyright: Copyright (c) 2009 Carnegie Mellon University
* Versions:
*   1.0 February 2009 - Initial rewrite of original assignment 3 (ajl).
*
* Description: This class is the console for the museum environmental control system. This process consists of two
* threads. The ECSSrvcMonitor object is a thread that is started that is responsible for the SrvcMonitoring and control of
* the museum environmental systems. The main thread provides a text interface for the user to change the temperature
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
        ECSrvcMaintainMonitor SrvcMonitor = null;          // The environmental control system SrvcMonitor
        

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
                System.out.println( "X: Stop System\n" );
                System.out.print( "\n>>>> " );
                Option = UserInput.KeyboardReadString();

                //////////// option 1 ////////////

                if ( Option.equals( "1" ) )
                {
                    SrvcMonitor.showRegisteredProcesses();
                    
                } // if

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
