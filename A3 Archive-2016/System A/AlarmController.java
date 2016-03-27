import InstrumentationPackage.MessageWindow;
import MessagePackage.Message;
import MessagePackage.MessageManagerInterface;
import MessagePackage.MessageQueue;

public class AlarmController {
	
	/***********************
	 * Private class fields
	 ***********************/
	private static MessageManagerInterface messageManager;	// Interface object to the message manager
	private static MessageWindow messageWindow;
	private static MessageQueue queue;				// Message Queue
	private static Message Msg;					// Message object
	private static boolean MotionAlarmSensorState = false;	// Motion Sensor State : false == off, true == on
	private static boolean WindowBreakAlarmState = false;	// Window Break Sensor State : false == off, true == on
	private static boolean DoorBreakSensorState = false;	// Door Break Sensor State : false == off, true == on
	private static int	Delay = 2500;					// The loop delay (2.5 seconds)
	private static boolean Done = false;				// Loop termination flag
	private static boolean ArmState = false;				// Loop termination flag
	
	/************
	 * Constants
	 ************/
	private static final int MOTION_SENSOR_ID = -110;
	private static final int DOOR_BREAK_SENSOR_ID = -111;
	private static final int WINDOW_BREAK_SENSOR_ID = -112;
	private static final String WINDOW_BREAK_SENSOR_ON = "W1";
	private static final String WINDOW_BREAK_SENSOR_OFF = "W0";
	private static final String DOOR_BREAK_SENSOR_ON = "DB1";
	private static final String DOOR_BREAK_SENSOR_OFF = "DB0";
	private static final String MOTION_SENSOR_ON = "M1";
	private static final String MOTION_SENSOR_OFF = "M0";
	
	private static final int ARM_ID = 100;
	private static final int DISARM_ID = 101;
	private static final int HALT_SECURITY_ID = 199;
	private static final int MOTION_SENSE_MSG_ID = 120;
	private static final int DOOR_BREAK_MSG_ID = 121;
	private static final int WINDOW_BREAK_MSG_ID = 122;
	
	
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
				
				if ( Msg.GetMessageId() == ARM_ID )
				{
					handleArm(); 
				}
				
				if ( Msg.GetMessageId() == DISARM_ID )
				{
					handleDisarm(); 
				}
				
				if ( Msg.GetMessageId() == HALT_SECURITY_ID )
				{
					handleExitMessage();
				}

				if ( Msg.GetMessageId() == MOTION_SENSE_MSG_ID )
				{
					handleMotionSensorMessage(Msg); 
				}
				
				if ( Msg.GetMessageId() == WINDOW_BREAK_MSG_ID )
				{
					handleWindowBreakMessage(Msg);
				}
				
				if ( Msg.GetMessageId() == DOOR_BREAK_MSG_ID )
				{
					handleDoorBreakMessage(Msg);
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

	private static void handleMotionSensorMessage(Message Msg) {
		messageWindow.WriteMessage(Msg.GetMessage());
	}

	private static void handleDoorBreakMessage(Message Msg) {
		messageWindow.WriteMessage(Msg.GetMessage());
	}

	private static void handleWindowBreakMessage(Message Msg) {
			messageWindow.WriteMessage(Msg.GetMessage());
	}

	private static void handleArm() {
			
			ArmState = true;
			MotionAlarmSensorState = true;
			DoorBreakSensorState = true;
			WindowBreakAlarmState = true;
			messageWindow.WriteMessage("Received arm message. Alarms arm." );

			// Send arm message to sensors
			sendMessageToMessageManager( messageManager, MOTION_SENSOR_ON, MOTION_SENSOR_ID );
			sendMessageToMessageManager( messageManager, DOOR_BREAK_SENSOR_ON, DOOR_BREAK_SENSOR_ID );
			sendMessageToMessageManager( messageManager, WINDOW_BREAK_SENSOR_ON, WINDOW_BREAK_SENSOR_ID );
	}
	
	private static void handleDisarm() {
		
		ArmState = false;
		MotionAlarmSensorState = false;
		DoorBreakSensorState = false;
		WindowBreakAlarmState = false;
		
		messageWindow.WriteMessage("Received disarm message. Alarms disarm." );

		// Confirm that the message was received and acted on
		sendMessageToMessageManager( messageManager, MOTION_SENSOR_OFF, MOTION_SENSOR_ID );
		sendMessageToMessageManager( messageManager, DOOR_BREAK_SENSOR_OFF, DOOR_BREAK_SENSOR_ID );
		sendMessageToMessageManager( messageManager, WINDOW_BREAK_SENSOR_OFF, WINDOW_BREAK_SENSOR_ID );
}

	private static void initializeDisplays() {
		
		System.out.println("Registered with the message manager." );
		
		// Now we create the motionDetector, windowBreakDetector and doorBreakDetector status and message panel

		float WinPosX = 0.0f; 	//This is the X position of the message window in term of a percentage of the screen height
		float WinPosY = 0.90f;	//This is the Y position of the message window in terms of a percentage of the screen height

		messageWindow = new MessageWindow("Alarm Controller Status Console", WinPosX, WinPosY);

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