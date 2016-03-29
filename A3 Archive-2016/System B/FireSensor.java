import java.util.Random;

import InstrumentationPackage.MessageWindow;
import MessagePackage.Message;
import MessagePackage.MessageManagerInterface;
import MessagePackage.MessageQueue;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FireSensor {
	
	/***********************
	 * Private class fields
	 ***********************/
	private static MessageManagerInterface messageManager;	// Interface object to the message manager
	private static MessageWindow messageWindow;
	private static MessageQueue queue;				// Message Queue
	private static Message Msg;					// Message object
        private static boolean FireAlarmState = false; // 
        private static boolean SprinklerState = false;
	private static int	Delay = 1500;					// Fire can randomly happen in atleast 25 sec
        private static int	Delay2 = 5000;					// Sprinkler starts in 10 sec
	private static boolean Done = false;				// Loop termination flag
	
	/************
	 * Constants
	 ************/
	
	private static final String FIRE_ALARM_ON = "F1";
	private static final String FIRE_ALARM_OFF = "F0";
        private static final String SPRINKLER_ON = "S1";
	private static final String SPRINKLER_OFF = "S0";
	private static final int FIRE_ALARM_MSG_ID = 9;
        private static final int FIRE_ALARM_ACK_ID = -9;
        private static Date timeStart = null, timeWait = null;
	
	public static void main(String args[])
	{
		instantiateMessageManager(args);

		// Here we check to see if registration worked. If messageManager is null then the
		// message manager interface was not properly created.

		if (messageManager != null)
		{
			initializeDisplays();
			performSensorProcess(); 

		} else {

			System.out.println("Unable to register with the message manager.\n\n" );

		} 

	}

	private static void performSensorProcess() {
		/**************************************************
		*  Here we start the main simulation loop that 
		*  will continuously look for control messages
		***************************************************/
		messageWindow.WriteMessage("Fire Sensor Active" );
		while ( !Done )
		{
			//postArmStatus
			
			try
			{
				queue = messageManager.GetMessageQueue(); //get messages from message manager
			} 

			catch( Exception e )
			{
				messageWindow.WriteMessage("Error getting message queue::" + e );
			} 

			int qlen = queue.GetSize();

			for ( int i = 0; i < qlen; i++ )
			{	
				Msg = queue.GetMessage();
				
				if ( Msg.GetMessageId() == FIRE_ALARM_ACK_ID )
				{
					handleFireAlarmControllerMessage(Msg);
				}
                                
                                
			} 

                        
                                if (FireAlarmState) {
                                    sendMessageToMessageManager("F1",FIRE_ALARM_ACK_ID);
                                }
                                if (SprinklerState) {
                                    sendMessageToMessageManager("S1",FIRE_ALARM_ACK_ID);
                                }
                        
			try
			{
				Thread.sleep( Delay );
                                if(!FireAlarmState && CoinToss()){
//                                    sendMessageToMessageManager("F1", FIRE_ALARM_ACK_ID ); // turn on fire alarm
                                    sendMessageToMessageManager("F1", FIRE_ALARM_MSG_ID ); // turn on fire alarm
                                    
                                    timeStart = new Date();
                                    FireAlarmState = true;
				}
                                timeWait = new Date();
                                if(timeStart!=null && !SprinklerState) {
                                    if(FireAlarmState && ((timeWait.getTime() - timeStart.getTime() )/1000 % 60) > 10 && !SprinklerState) {
//                                        sendMessageToMessageManager("S1", FIRE_ALARM_ACK_ID ); // turn off sprinkler
                                        sendMessageToMessageManager("S1", FIRE_ALARM_MSG_ID ); // turn on fire alarm
                                        SprinklerState = true;
                                    }
                                }
			} 
                        
			catch( Exception e )
			{
				System.out.println( "Sleep error:: " + e );

			} 
		}
	}

	private static void handleFireAlarmControllerMessage(Message Msg) {
		
		
		if (Msg.GetMessage().equalsIgnoreCase(FIRE_ALARM_ON)) // doorBreakSensor on
		{
			messageWindow.WriteMessage("Fire Sensed - Fire Alarm on" );
//			FireAlarmState = true;
			
		} 
		
		if (Msg.GetMessage().equalsIgnoreCase(FIRE_ALARM_OFF)) // doorBreakSensor off
		{
			messageWindow.WriteMessage("No Fire - Fire Alarm off" );
			FireAlarmState = false;
		}
                
                if (Msg.GetMessage().equalsIgnoreCase(SPRINKLER_ON)) // doorBreakSensor on
		{
			messageWindow.WriteMessage("Fire Sensed - Sprinkler on" );
//			SprinklerState = true;
		} 
		
		if (Msg.GetMessage().equalsIgnoreCase(SPRINKLER_OFF)) // doorBreakSensor off
		{
			messageWindow.WriteMessage("No Fire - Sprinkler off" );
                        timeStart = null;
                        SprinklerState = false;
		}
	}
        
	private static void initializeDisplays() {
		
		System.out.println("Registered with the message manager." );
		
		// Now we create the motionDetector, windowBreakDetector and doorBreakDetector status and message panel

		float WinPosX = 0.10f; 	//This is the X position of the message window in term of a percentage of the screen height
		float WinPosY = 0.00f;	//This is the Y position of the message window in terms of a percentage of the screen height

		messageWindow = new MessageWindow("Fire Sensor", WinPosX, WinPosY);

		// Now we put the indicators directly under the panel
		
		messageWindow.WriteMessage("Registered with the message manager." );

		try
		{
			messageWindow.WriteMessage("   Participant id: " + messageManager.GetMyId() );
			messageWindow.WriteMessage("   Registration Time: " + messageManager.GetRegistrationTime() );
		} 

		catch (Exception e)
		{
			System.out.println("Error:: " + e);
		} 
	}

	private static void instantiateMessageManager(String[] args) {

		if ( args.length == 0 ) // Checking if No IP is provided
 		{
			// message manager is on the local system

			System.out.println("\n\nAttempting to register on the local machine..." );

			try
			{
				// Here we create an message manager interface object. This assumes
				// that the message manager is on the local machine

				messageManager = new MessageManagerInterface();
			}

			catch (Exception e)
			{
				System.out.println("Error instantiating message manager interface: " + e);

			} 

		} 
		
		else {

			// message manager is not on the local system

			String MsgMgrIP = args[0];

			System.out.println("\n\nAttempting to register on the machine:: " + MsgMgrIP );

			try
			{
				// Here we create an message manager interface object. This assumes
				// that the message manager is NOT on the local machine

				messageManager = new MessageManagerInterface( MsgMgrIP );
			}

			catch (Exception e)
			{
				System.out.println("Error instantiating message manager interface: " + e);

			} 

		}
	}

	static private void sendMessageToMessageManager(String msg, int id)
	{

		Message message = new Message( id, msg); // Here we create the message.
		
		try
		{
			messageManager.SendMessage( message ); // Here we send the message to the message manager.

		}

		catch (Exception e)
		{
			System.out.println("Error Sending Message:: " + e);

		} 

	} 
	
	static private boolean CoinToss()
	{
		Random r = new Random();

		return(r.nextBoolean());

	}

}
