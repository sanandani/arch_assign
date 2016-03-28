import InstrumentationPackage.Indicator;
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
	private static boolean MotionAlarmState = false;	// Motion Sensor State : false == off, true == on
	private static boolean WindowBreakAlarmState = false;	// Window Break Sensor State : false == off, true == on
	private static boolean DoorBreakAlarmState = false;	// Door Break Sensor State : false == off, true == on
	private static int	Delay = 2500;					// The loop delay (2.5 seconds)
	private static boolean Done = false;				// Loop termination flag
	private static boolean ArmState = false;				// Loop termination flag
	private static Indicator da;								// door alarm
	private static Indicator wa;								// window alarm
	private static Indicator ma;								// motion alarm

	
	/************
	 * Constants
	 ************/
	private static final int MOTION_ALARM_ACK_ID = -150;
	private static final int DOOR_BREAK_ACK_ID = -151;
	private static final int WINDOW_BREAK_ACK_ID = -152;
	private static final int MOTION_SENSOR_ID = -110;
	private static final int WINDOW_BREAK_SENSOR_ID = -112;
	private static final int DOOR_BREAK_SENSOR_ID = -111;
	private static final String WINDOW_BREAK_SENSOR_ON = "W1";
	private static final String WINDOW_BREAK_SENSOR_OFF = "W0";
	private static final String DOOR_BREAK_SENSOR_ON = "DB1";
	private static final String DOOR_BREAK_SENSOR_OFF = "DB0";
	private static final String MOTION_SENSOR_ON = "M1";
	private static final String MOTION_SENSOR_OFF = "M0";
	
	private static final int ARM_ID = 100;
	private static final int DISARM_ID = 101;
	private static final int HALT_SECURITY_ID = 199;
	
	
	private static final int MOTION_ALARM_ID = 150;
	private static final int DOOR_ALARM_ID = 151;
	private static final int WINDOW_ALARM_ID = 152;
	
	private static final String SOUND_WINDOW_ALARM = "Sound Window Alarm";
	private static final String SOUND_DOOR_ALARM = "Sound Door Alarm";
	private static final String SOUND_MOTION_ALARM = "Sound Motion Alarm";
	private static final String MOTION_DETECTED = "MOTION DETECTED";
	private static final String DOOR_BREAK_DETECTED = "DOOR BREAK DETECTED";
	private static final String WINDOW_BREAK_DETECTED = "WINDOW BREAK DETECTED";
	
	
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
		messageWindow.WriteMessage("Alarm Controller disarmed." );
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
				
				if(ArmState){

				if ( Msg.GetMessageId() == MOTION_ALARM_ID )
				{
					handleMotionSensorMessage(Msg); 
				}
				
				if ( Msg.GetMessageId() == WINDOW_ALARM_ID )
				{
					handleWindowBreakMessage(Msg);
				}
				
				if ( Msg.GetMessageId() == DOOR_ALARM_ID )
				{
					handleDoorBreakMessage(Msg);
				}
				}
				else{
					wa.SetLampColorAndMessage("Window Alarm Off", 0);
					da.SetLampColorAndMessage("Door Alarm Off", 0);
					ma.SetLampColorAndMessage("Motion Alarm Off", 0);
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
		wa.dispose();
		da.dispose();
		ma.dispose();
		messageWindow.WriteMessage( "\n\nSimulation Stopped. \n");
	}

	private static void handleMotionSensorMessage(Message Msg) {
		if(SOUND_MOTION_ALARM.equals(Msg.GetMessage()))
		{
			messageWindow.WriteMessage(Msg.GetMessage());
			wa.SetLampColorAndMessage(MOTION_DETECTED, 3);
			sendMessageToMessageManager(Msg.GetMessage(),MOTION_ALARM_ACK_ID);
		}
		else{
				messageWindow.WriteMessage(Msg.GetMessage());
				wa.SetLampColorAndMessage(SOUND_MOTION_ALARM, 1);
				sendMessageToMessageManager(Msg.GetMessage(),MOTION_ALARM_ACK_ID);
			
		}
	}

	private static void handleDoorBreakMessage(Message Msg) {
		if(SOUND_DOOR_ALARM.equals(Msg.GetMessage()))
		{
			messageWindow.WriteMessage(Msg.GetMessage());
			wa.SetLampColorAndMessage(DOOR_BREAK_DETECTED, 3);
			sendMessageToMessageManager(Msg.GetMessage(),DOOR_BREAK_ACK_ID);
		}
		else{
				messageWindow.WriteMessage(Msg.GetMessage());
				wa.SetLampColorAndMessage(SOUND_DOOR_ALARM, 1);
				sendMessageToMessageManager(Msg.GetMessage(),DOOR_BREAK_ACK_ID);
			
		}
	}

	private static void handleWindowBreakMessage(Message Msg) {
		if(SOUND_WINDOW_ALARM.equals(Msg.GetMessage()))
		{
			messageWindow.WriteMessage(Msg.GetMessage());
			wa.SetLampColorAndMessage(WINDOW_BREAK_DETECTED, 3);
			sendMessageToMessageManager(Msg.GetMessage(),WINDOW_BREAK_ACK_ID);
		}
		else{
				messageWindow.WriteMessage(Msg.GetMessage());
				wa.SetLampColorAndMessage(SOUND_WINDOW_ALARM, 1);
				sendMessageToMessageManager(Msg.GetMessage(),WINDOW_BREAK_ACK_ID);
			
		}
	}

	private static void handleArm() {
			
			ArmState = true;
			messageWindow.WriteMessage("Received arm message. Arming alarms." );
			
			MotionAlarmState = true;
			ma.SetLampColorAndMessage("Motion Alarm On", 1);
			messageWindow.WriteMessage("Motion Alarm Armed" );
			
			DoorBreakAlarmState = true;
			messageWindow.WriteMessage("Door Break Alarm Armed" );
			da.SetLampColorAndMessage("Door Alarm On", 1);
			
			WindowBreakAlarmState = true;
			messageWindow.WriteMessage(" Break Alarm Armed" );
			wa.SetLampColorAndMessage("Window Alarm On", 1);

			// Send arm message to sensors
			sendMessageToMessageManager( MOTION_SENSOR_ON, MOTION_SENSOR_ID );
			sendMessageToMessageManager( DOOR_BREAK_SENSOR_ON, DOOR_BREAK_SENSOR_ID );
			sendMessageToMessageManager( WINDOW_BREAK_SENSOR_ON, WINDOW_BREAK_SENSOR_ID );
			messageWindow.WriteMessage(" All Sensors Armed" );
			
			
	}
	
	private static void handleDisarm() {
		
		ArmState = false;
		messageWindow.WriteMessage("Received arm message. Disarming alarms." );
		
		MotionAlarmState = false;
		ma.SetLampColorAndMessage("Motion Alarm Off", 0);
		messageWindow.WriteMessage("Motion Alarm Disarmed" );
		
		DoorBreakAlarmState = false;
		da.SetLampColorAndMessage("Door Alarm Off", 0);
		messageWindow.WriteMessage("Door Break Alarm Disarmed" );
		
		WindowBreakAlarmState = false;
		wa.SetLampColorAndMessage("Window Alarm Off", 0);
		messageWindow.WriteMessage(" Break Alarm Disarmed" );

		// Confirm that the message was received and acted on
		sendMessageToMessageManager(MOTION_SENSOR_OFF, MOTION_SENSOR_ID );
		sendMessageToMessageManager(DOOR_BREAK_SENSOR_OFF, DOOR_BREAK_SENSOR_ID );
		sendMessageToMessageManager(WINDOW_BREAK_SENSOR_OFF, WINDOW_BREAK_SENSOR_ID );
		messageWindow.WriteMessage(" All Sensors Disarmed" );
	
}

	private static void initializeDisplays() {
		
		System.out.println("Registered with the message manager." );
		
		// Now we create the motionDetector, windowBreakDetector and doorBreakDetector status and message panel

		float WinPosX = 0.0f; 	//This is the X position of the message window in term of a percentage of the screen height
		float WinPosY = 0.70f;	//This is the Y position of the message window in terms of a percentage of the screen height

		messageWindow = new MessageWindow("Alarm Controller Status Console", WinPosX, WinPosY);

		messageWindow.WriteMessage("Registered with the message manager." );
		da = new Indicator ("Door Alarm UNK", messageWindow.GetX()+ messageWindow.Width(), 0);
		wa = new Indicator ("Window Alarm UNK", messageWindow.GetX()+ messageWindow.Width(), (int)(messageWindow.Height()/2));
		ma = new Indicator ("Motion Alarm UNK", messageWindow.GetX()+ messageWindow.Width(), (int)(messageWindow.Height()));

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
			System.out.println("Error Confirming Message:: " + e);

		} 

	} 

}
