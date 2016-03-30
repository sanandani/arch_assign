import InstrumentationPackage.MessageWindow;
import MessagePackage.Message;
import MessagePackage.MessageManagerInterface;
import MessagePackage.MessageQueue;

public class WindowBreakSensor {
	/***********************
	 * Private class fields
	 ***********************/
	private static MessageManagerInterface messageManager;	// Interface object to the message manager
	private static MessageWindow messageWindow;
	private static MessageQueue queue;				// Message Queue
	private static Message Msg;					// Message object
	private static boolean WindowBreakSensorState = false;	// Door Break Sensor State : false == off, true == on
	private static int	Delay = 2500;					// The loop delay (2.5 seconds)
	private static boolean simulate = false;	// Simulate break in  : false == off, true == on
	private static boolean Done = false;				// Loop termination flag
	
	/************
	 * Constants
	 ************/
	
	private static final int WINDOW_BREAK_SENSOR_ID = -112;
	private static final String WINDOW_BREAK_SENSOR_ON = "W1";
	private static final String WINDOW_BREAK_SENSOR_OFF = "W0";
	private static final int HALT_SECURITY_ID = 199;
	private static final int WINDOW_BREAK_MSG_ID = 122;
	private static final String WINDOW_BREAK_DETECTED = "WINDOW BREAK DETECTED";
	private static final String OK = "OK";
	private static final int WINDOW_SIMULATE_ID = 162;
	private static final String SIMULATE_ON = "On";
	private static final String STOP_ALARM = "STOP ALARM";

	
	public static void main(String args[])
	{
		instantiateMessageManager(args);

		// Here we check to see if registration worked. If messageManager is null then the
		// message manager interface was not properly created.

		if (messageManager != null)
		{
			initializeDisplays();
			performSensorprocess(); 

		} else {

			System.out.println("Unable to register with the message manager.\n\n" );

		} 

	}

	private static void performSensorprocess() {
		messageWindow.WriteMessage("Window Break Sensor off" );
		/**************************************************
		*  Here we start the main simulation loop that 
		*  will continuously look for control messages
		***************************************************/
		
		while ( !Done )
		{
			//postArmStatus
			
			try
			{
				queue = messageManager.GetMessageQueue(); //get messages from message manager
				sendHeartBeat(messageManager,"20","WindowBreakSensor Controller","This device senses whether windows are broken");
			} 

			catch( Exception e )
			{
				messageWindow.WriteMessage("Error getting message queue::" + e );
			} 

			int qlen = queue.GetSize();

			for ( int i = 0; i < qlen; i++ )
			{	
				Msg = queue.GetMessage();
				
				if ( Msg.GetMessageId() == WINDOW_BREAK_SENSOR_ID)
				{
					handleWindowBreakControllerMessage(Msg);
				}
				
				if ( Msg.GetMessageId() == HALT_SECURITY_ID )
				{
					handleExitMessage();
				}
				if ( WindowBreakSensorState){
					if(Msg.GetMessageId() == WINDOW_SIMULATE_ID )
					{
						if(SIMULATE_ON.equals(Msg.GetMessage())){
							simulate = true;
							messageWindow.WriteMessage("Send WINDOW_BREAK_DETECTED");
							sendMessageToMessageManager(WINDOW_BREAK_DETECTED,WINDOW_BREAK_MSG_ID);
						}
						else{
							simulate = false;
							messageWindow.WriteMessage("Send STOP_ALARM");
							sendMessageToMessageManager(STOP_ALARM,WINDOW_BREAK_MSG_ID);
						}
					}
				}
			}
			if ( WindowBreakSensorState){
			if(simulate)
			{
				messageWindow.WriteMessage("Send WINDOW_BREAK_DETECTED");
				sendMessageToMessageManager(WINDOW_BREAK_DETECTED,WINDOW_BREAK_MSG_ID);
			}
			else
			{
				messageWindow.WriteMessage("Send OK");
				sendMessageToMessageManager(OK,WINDOW_BREAK_MSG_ID);
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


	private static void handleExitMessage() {
		Done = true;

		try
		{
			messageManager.UnRegister();

		} 

		catch (Exception e)
		{
			messageWindow.WriteMessage("Error unregistering: " + e);

		} 

		messageWindow.WriteMessage( "\n\nSimulation Stopped. \n");

	}

	private static void handleWindowBreakControllerMessage(Message Msg) {
		
		
		if (Msg.GetMessage().equalsIgnoreCase(WINDOW_BREAK_SENSOR_ON)) // window break Sensor on
		{
			messageWindow.WriteMessage("Window Break Sensor on" );
			WindowBreakSensorState = true;
			
		} 
		
		if (Msg.GetMessage().equalsIgnoreCase(WINDOW_BREAK_SENSOR_OFF)) // window break Sensor off
		{
			messageWindow.WriteMessage("Window Break Sensor off" );
			WindowBreakSensorState = false;
		}
	}


	

	private static void initializeDisplays() {
		
		System.out.println("Registered with the message manager." );
		
		// Now we create the window break Sensor status and message panel

		float WinPosX = 0.10f; 	//This is the X position of the message window in term of a percentage of the screen height
		float WinPosY = 0.90f;	//This is the Y position of the message window in terms of a percentage of the screen height

		messageWindow = new MessageWindow("Window Break Sensor", WinPosX, WinPosY);

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
			messageWindow.WriteMessage(msg);
			messageManager.SendMessage( message ); // Here we send the message to the message manager.

		}

		catch (Exception e)
		{
			System.out.println("Error Sending Message:: " + e);

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
