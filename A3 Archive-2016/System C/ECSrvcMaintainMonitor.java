/******************************************************************************************************************
* File:ECSMonitor.java
* Course: 17655
* Project: Assignment A3
* Copyright: Copyright (c) 2009 Carnegie Mellon University
* Versions:
*   1.0 March 2009 - Initial rewrite of original assignment 3 (ajl).
*
* Description:
*
* This class monitors the environmental control systems that control museum temperature and humidity. In addition to
* monitoring the temperature and humidity, the ECSMonitor also allows a user to set the humidity and temperature
* ranges to be maintained. If temperatures exceed those limits over/under alarm indicators are triggered.
*
* Parameters: IP address of the message manager (on command line). If blank, it is assumed that the message manager is
* on the local machine.
*
* Internal Methods:
*   static private void Heater(MessageManagerInterface ei, boolean ON )
*   static private void Chiller(MessageManagerInterface ei, boolean ON )
*   static private void Humidifier(MessageManagerInterface ei, boolean ON )
*   static private void Dehumidifier(MessageManagerInterface ei, boolean ON )
*
******************************************************************************************************************/
import InstrumentationPackage.*;
import MessagePackage.*;

import java.text.SimpleDateFormat;
import java.util.*;

class ECSrvcMaintainMonitor extends Thread
{
    private static HashMap<String,String> registeredDevices = new HashMap<String,String>();
    private static HashMap<String,Long> deviceslastSeenMap = new HashMap<String,Long>();
    public static long waitingTime = 10000;    
    private MessageManagerInterface em = null;  // Interface object to the message manager
    private String MsgMgrIP = null;             // Message Manager IP address
    boolean Registered = true;                  // Signifies that this class is registered with an message manager.
    MessageWindow mw = null;                    // This is the message window
    Indicator ti;                               // Temperature indicator
    Indicator hi;                               // Humidity indicator

    public ECSrvcMaintainMonitor()
    {
        // message manager is on the local system

        try
        {
            // Here we create an message manager interface object. This assumes
            // that the message manager is on the local machine

            em = new MessageManagerInterface();

        }

        catch (Exception e)
        {
            System.out.println("ECSMonitor::Error instantiating message manager interface: " + e);
            Registered = false;

        } // catch

    } //Constructor

    public ECSrvcMaintainMonitor( String MsgIpAddress )
    {
        // message manager is not on the local system

        MsgMgrIP = MsgIpAddress;

        try
        {
            // Here we create an message manager interface object. This assumes
            // that the message manager is NOT on the local machine

            em = new MessageManagerInterface( MsgMgrIP );
        }

        catch (Exception e)
        {
            System.out.println("ECSMonitor::Error instantiating message manager interface: " + e);
            Registered = false;

        } // catch

    } // Constructor

    public void run()
    {
        Message Msg = null;             // Message object
        MessageQueue eq = null;         // Message Queue
        int MsgId = 0;                  // User specified message ID
        int Delay = 200;               // The loop delay (1/2 second)
        boolean Done = false;           // Loop termination flag

        if (em != null)
        {
            // Now we create the ECS status and message panel
            // Note that we set up two indicators that are initially yellow. This is
            // because we do not know if the temperature/humidity is high/low.
            // This panel is placed in the upper left hand corner and the status
            // indicators are placed directly to the right, one on top of the other

            mw = new MessageWindow("Service Maintainence ECS Monitoring Console", 0, 0);
            

            mw.WriteMessage( "Registered with the message manager." );

            try
            {
                mw.WriteMessage("   Participant id: " + em.GetMyId() );
                mw.WriteMessage("   Registration Time: " + em.GetRegistrationTime() );

            } // try

            catch (Exception e)
            {
                System.out.println("Error:: " + e);

            } // catch

            /********************************************************************
            ** Here we start the main simulation loop
            *********************************************************************/

            while ( !Done )
            {
                // Here we get our message queue from the message manager

                try
                {
                    eq = em.GetMessageQueue();

                } // try

                catch( Exception e )
                {
                    mw.WriteMessage("Error getting message queue::" + e );

                } // catch

                // If there are messages in the queue, we read through them.
                // We are looking for MessageIDs = 1 or 2. Message IDs of 1 are temperature
                // readings from the temperature sensor; message IDs of 2 are humidity sensor
                // readings. Note that we get all the messages at once... there is a 1
                // second delay between samples,.. so the assumption is that there should
                // only be a message at most. If there are more, it is the last message
                // that will effect the status of the temperature and humidity controllers
                // as it would in reality.

                int qlen = eq.GetSize();

                for ( int i = 0; i < qlen; i++ )
                {
                    Msg = eq.GetMessage();
                    Calendar TimeStamp = Calendar.getInstance();
                    SimpleDateFormat TimeStampFormat = new SimpleDateFormat("yyyy MM dd::hh:mm:ss:SSS");
                    Long currentTimeInMilliseconds =  (Long) System.currentTimeMillis();
                    if(Msg.GetMessageId() == 0){
                        String[] messageSplitter = Msg.GetMessage().split(":");
                        String deviceID = messageSplitter[0];
                        String deviceName = messageSplitter[1];
                        String deviceDescription = messageSplitter[2];
                        if (registeredDevices.containsKey(deviceID)) {
                            deviceslastSeenMap.put(deviceID,currentTimeInMilliseconds);
                        } else {
                            ECSrvcMaintainMonitor.registeredDevices.put(deviceID,deviceName+":"+deviceDescription);
                        }
                    } // if
                    
                    for (Map.Entry<String,Long> entry : deviceslastSeenMap.entrySet()) {
                        if(currentTimeInMilliseconds > (entry.getValue() + waitingTime)){
                            String[] messageSplitter = registeredDevices.get(entry.getKey()).split(":");
                            String deviceName = messageSplitter[0];
                            String deviceDescription = messageSplitter[1];
                            mw.WriteMessage("Device ID - " + entry.getKey() + "-" + deviceName + " has not responded for more than " + waitingTime +" ms");
                            mw.WriteMessage(deviceName + " : " + deviceDescription);
                        }
                    }
                    
                    if(Msg.GetMessageId() == -99){
                        //ECSrvcMaintainMonitor.registeredDevices.remove(Msg.GetMessage());
                    } // if
                    
                    // If the message ID == 99 then this is a signal that the simulation
                    // is to end. At this point, the loop termination flag is set to
                    // true and this process unregisters from the message manager.

                    if ( Msg.GetMessageId() == 99 )
                    {
                        Done = true;

                        try
                        {
                            em.UnRegister();

                        } // try

                        catch (Exception e)
                        {
                            mw.WriteMessage("Error unregistering: " + e);

                        } // catch

                        mw.WriteMessage( "\n\nSimulation Stopped. \n");

                        // Get rid of the indicators. The message panel is left for the
                        // user to exit so they can see the last message posted.

                    } // if

                } // for

                // This delay slows down the sample rate to Delay milliseconds

                try
                {
                    Thread.sleep( Delay );

                } // try

                catch( Exception e )
                {
                    System.out.println( "Sleep error:: " + e );

                } // catch

            } // while

        } else {

            System.out.println("Unable to register with the message manager.\n\n" );

        } // if

    } // main

    /***************************************************************************
    * CONCRETE METHOD:: IsRegistered
    * Purpose: This method returns the registered status
    *
    * Arguments: none
    *
    * Returns: boolean true if registered, false if not registered
    *
    * Exceptions: None
    *
    ***************************************************************************/

    public boolean IsRegistered()
    {
        return( Registered );

    } // SetTemperatureRange

    /***************************************************************************
    * CONCRETE METHOD:: Halt
    * Purpose: This method posts an message that stops the environmental control
    *          system.
    *
    * Arguments: none
    *
    * Returns: none
    *
    * Exceptions: Posting to message manager exception
    *
    ***************************************************************************/

    public void Halt()
    {
        mw.WriteMessage( "***HALT MESSAGE RECEIVED - SHUTTING DOWN SYSTEM***" );

        // Here we create the stop message.

        Message msg;

        msg = new Message( (int) 99, "XXX" );

        // Here we send the message to the message manager.

        try
        {
            em.SendMessage( msg );

        } // try

        catch (Exception e)
        {
            System.out.println("Error sending halt message:: " + e);

        } // catch

    } // Halt

    /**
     * 
     */
    public void showRegisteredProcesses() {
        // TODO Auto-generated method stub
        System.out.println("===================================");
        int i = 0;
        for (Map.Entry<String,String> entry : registeredDevices.entrySet()) {
            String[] messageSplitter = entry.getValue().split(":");
            String deviceName = messageSplitter[0];
            String deviceDescription = messageSplitter[1];
            System.out.println("S No.               : " + ++i);
            System.out.println("Equipment ID        : " + entry.getKey());
            System.out.println("Device Name         : " + deviceName);
            System.out.println("Device Information  : " + deviceDescription);
            System.out.println("===================================");
            // do stuff
         }
        
    }
    
    public void showLastSeenOfDevices() {
     // TODO Auto-generated method stub
        Calendar TimeStamp = Calendar.getInstance();
        SimpleDateFormat TimeStampFormat = new SimpleDateFormat("yyyy MM dd::hh:mm:ss:SSS");
        System.out.println("===================================");
        for (Map.Entry<String,Long> entry : deviceslastSeenMap.entrySet()) {
            Calendar cal = Calendar.getInstance();
            TimeStamp.setTimeInMillis(entry.getValue());
            System.out.println("  Equipment ID  : " + entry.getKey() + "  Last seen  : " + TimeStampFormat.format(TimeStamp.getTime()) );
        }
        System.out.println("===================================");
    }
} // ECSServiceMonitor