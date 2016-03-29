import InstrumentationPackage.MessageWindow;
import MessagePackage.Message;
import MessagePackage.MessageManagerInterface;
import MessagePackage.MessageQueue;

public class FireAlarmController {
	
	/***********************
	 * Private class fields
	 ***********************/
	private static MessageManagerInterface messageManager;	// Interface object to the message manager
	private static MessageWindow messageWindow;
	private static MessageQueue queue;				// Message Queue
	private static Message Msg;					// Message object
	private static int	Delay = 2500;					// The loop delay (2.5 seconds)
	private static boolean Done = false;				// Loop termination flag
	private static boolean FireAlarmState = false; 
	
	/************
	 * Constants
	 ************/
        private static final int FIRE_ALARM_SENSOR_ID = -113;
        
        private static final String FIRE_ALARM_ON = "F1";
	private static final String FIRE_ALARM_OFF = "F0";
        
        private static final int FIRE_ALARM_STOP_ID = 102;
        private static final int FIRE_ALARM_MSG_ID = 9;
      
	
	public static void main(String args[])
	{
		instantiateMessageManager(args);

		// Here we check to see if registration worked. If messageManager is null then the
		// message manager interface was not properly created.

		if (messageManager != null)
		{
			initializeDisplays();
			processControllerMessages(); 

		} else {

			System.out.println("Unable to register with the message manager.\n\n" );

		} 

	}

	private static void processControllerMessages() {
		/**************************************************
		*  Here we start the main simulation loop that 
		*  will continuously look for control messages
		***************************************************/
		messageWindow.WriteMessage("Fire Alarm Controller armed." );
		while ( !Done )
		{
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
                                
                                if ( Msg.GetMessageId() == FIRE_ALARM_STOP_ID )
				{
					handleFireAlarmStop();
				}

			} 

			try
			{
				Thread.sleep( Delay );
			} 

			catch( Exception e )
			{
				System.out.println( "Sleep error:: " + e );
			} 

		}
	}

	private static void handleFireAlarmStop() {
			
			messageWindow.WriteMessage("Received fire alarm stop message. Stopping fire alarm." );
			FireAlarmState = false;

			// Send arm message to sensors
			sendMessageToMessageManager( messageManager, FIRE_ALARM_OFF, FIRE_ALARM_SENSOR_ID );
			messageWindow.WriteMessage(" FIRE ALARM TURNED OFF");
	}
        
	private static void initializeDisplays() {
		
		System.out.println("Registered with the message manager." );
		
		// Now we create the motionDetector, windowBreakDetector and doorBreakDetector status and message panel

		float WinPosX = 0.90f; 	//This is the X position of the message window in term of a percentage of the screen height
		float WinPosY = 0.90f;	//This is the Y position of the message window in terms of a percentage of the screen height

		messageWindow = new MessageWindow("Fire Alarm Controller Status Console", WinPosX, WinPosY);

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

	static private void sendMessageToMessageManager(MessageManagerInterface mi, String msg, int id)
	{

		Message message = new Message( id, msg); // Here we create the message.
		
		try
		{
			mi.SendMessage( message ); // Here we send the message to the message manager.

		}

		catch (Exception e)
		{
			System.out.println("Error Confirming Message:: " + e);

		} 

	} 

}
