import InstrumentationPackage.MessageWindow;
import MessagePackage.Message;
import MessagePackage.MessageManagerInterface;
import MessagePackage.MessageQueue;

public class MotionSensor {
	/***********************
	 * Private class fields
	 ***********************/
	private static MessageManagerInterface messageManager;	// Interface object to the message manager
	private static MessageWindow messageWindow;
	private static MessageQueue queue;				// Message Queue
	private static Message Msg;					// Message object
	private static boolean MotionSensorState = false;	// Door Break Sensor State : false == off, true == on
	private static int	Delay = 2500;					// The loop delay (2.5 seconds)
	private static boolean Done = false;				// Loop termination flag
	
	/************
	 * Constants
	 ************/
	
	private static final int MOTION_SENSOR_ID = -110;
	private static final String MOTION_SENSOR_ON = "M1";
	private static final String MOTION_SENSOR_OFF = "M0";
	private static final int HALT_SECURITY_ID = 199;
	private static final int MOTION_SENSOR_MSG_ID = 120;
	private static final String MOTION_DETECTED = "MOTION DETECTED";
	private static final String OK = "OK";
	private static final int MOTION_SIMULATE_ID = 160;
	private static final String SIMULATE_ON = "On";

	
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
		/**************************************************
		*  Here we start the main simulation loop that 
		*  will continuously look for control messages
		***************************************************/
		messageWindow.WriteMessage("Motion Sensor off" );
		
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
				
				if ( Msg.GetMessageId() == MOTION_SENSOR_ID)
				{
					handleMotionSensorControllerMessage(Msg);
				}
				
				if ( Msg.GetMessageId() == HALT_SECURITY_ID )
				{
					handleExitMessage();
				}
				if ( MotionSensorState && Msg.GetMessageId() == MOTION_SIMULATE_ID )
				{
					if(SIMULATE_ON.equals(Msg.GetMessage())){
						sendMessageToMessageManager(MOTION_DETECTED,MOTION_SENSOR_MSG_ID);
					}
					else{
						sendMessageToMessageManager(OK,MOTION_SENSOR_MSG_ID);
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

	private static void handleMotionSensorControllerMessage(Message Msg) {
		
		
		if (Msg.GetMessage().equalsIgnoreCase(MOTION_SENSOR_ON)) // window break Sensor on
		{
			messageWindow.WriteMessage("Motion Sensor on" );
			MotionSensorState = true;
			
		} 
		
		if (Msg.GetMessage().equalsIgnoreCase(MOTION_SENSOR_OFF)) // window break Sensor off
		{
			messageWindow.WriteMessage("Motion Sensor off" );
			MotionSensorState = false;
		}
	}

	private static void initializeDisplays() {
		
		System.out.println("Registered with the message manager." );
		
		// Now we create the window break Sensor status and message panel

		float WinPosX = 0.10f; 	//This is the X position of the message window in term of a percentage of the screen height
		float WinPosY = 0.90f;	//This is the Y position of the message window in terms of a percentage of the screen height

		messageWindow = new MessageWindow("Motion Sensor", WinPosX, WinPosY);

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

}
