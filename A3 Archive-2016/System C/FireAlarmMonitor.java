import InstrumentationPackage.MessageWindow;
import MessagePackage.Message;
import MessagePackage.MessageManagerInterface;
import MessagePackage.MessageQueue;

public class FireAlarmMonitor {
	
	/***********************
	 * Private class fields
	 ***********************/
	private static MessageManagerInterface messageManager;	// Interface object to the message manager
	private static MessageWindow messageWindow;
	private static MessageQueue queue;				// Message Queue
	private static Message Msg;					// Message object
	private static int	Delay = 2500;					// The loop delay (2.5 seconds)
	private static boolean Done = false;				// Loop termination flag
	private static boolean ArmState = false;				// Loop termination flag

	
	/************
	 * Constants
	 ************/
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
		messageWindow.WriteMessage("Fire Alarm Controller Active." );
                messageWindow.WriteMessage("Sprinkler Controller Active." );
		while ( !Done )
		{
			try
			{
				queue = messageManager.GetMessageQueue(); //get messages from message manager
				sendHeartBeat(messageManager,"12","FireAlarmMonitor","This device monitors the states of the fire alarm");
			} 

			catch( Exception e )
			{
				messageWindow.WriteMessage("Error getting message queue::" + e );
			} 

			int qlen = queue.GetSize();

			for ( int i = 0; i < qlen; i++ )
			{
				Msg = queue.GetMessage();
				
				if ( Msg.GetMessageId() == FIRE_ALARM_MSG_ID )
				{
                                    if(Msg.GetMessage().equalsIgnoreCase("F0")) // humidifier on
                                    {
					turnOffAlarm(); 
                                    }
                                    if(Msg.GetMessage().equalsIgnoreCase("F1")) // humidifier on
                                    {
					turnOnAlarm(); 
                                    }
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

	private static void turnOffAlarm() {
			messageWindow.WriteMessage("Received Fire Alarm Off message." );
	}
        
        private static void turnOnAlarm() {
			messageWindow.WriteMessage("Received Fire Alarm On message." );
	}
        
	private static void initializeDisplays() {
		
		System.out.println("Registered with the message manager." );
		
		// Now we create the motionDetector, windowBreakDetector and doorBreakDetector status and message panel

		float WinPosX = 0.0f; 	//This is the X position of the message window in term of a percentage of the screen height
		float WinPosY = 0.80f;	//This is the Y position of the message window in terms of a percentage of the screen height

		messageWindow = new MessageWindow("Fire Alarm Monitor Status Console", WinPosX, WinPosY);

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
	static private void sendHeartBeat(MessageManagerInterface ei, String ID,String DeviceName, String DeviceDescription){
        // Here we create the message.

        Message msg = new Message( (int) 0, ID + ":" + DeviceName + ":" + DeviceDescription);

        // Here we send the message to the message manager.

        try
        {
            ei.SendMessage( msg );

        } // try

        catch (Exception e)
        {
            System.out.println("Error Registering the Message:: " + e);

        } // catch
    }


}
