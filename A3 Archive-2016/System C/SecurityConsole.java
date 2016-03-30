import MessagePackage.Message;
import MessagePackage.MessageManagerInterface;
import TermioPackage.Termio;

public class SecurityConsole {
	
	/***********************
	 * Private class fields
	 ***********************/
	private static MessageManagerInterface messageManager;	// Interface object to the message manager
	private static boolean Done = false;				// Loop termination flag
	private static Termio UserInput = new Termio();	// Termio IO Object
	
	/************
	 * Constants
	 ************/
	private static final int ARM_ID = 100;
	private static final int DISARM_ID = 101;
	private static final int HALT_SECURITY_ID = 199;
	private static final int MOTION_SIMULATE_ID = 160;
	private static final int DOOR_SIMULATE_ID = 161;
	private static final int WINDOW_SIMULATE_ID = 162;
	private static final String SIMULATE_ON = "On";
	private static final String SIMULATE_OFF = "Off";
	private static final int FIRE_ALARM_MSG_ID = 9;
	
	public static void main(String args[])
	{
		instantiateMessageManager(args);

		if (messageManager != null)
		{
			processControllerMessages(args); 

		} else {

			System.out.println("Unable to register with the message manager.\n\n" );

		} 

	}

	private static void processControllerMessages(String args[]) {
		/*****************************************
		*  Here we start the main console that 
		*  will continuously wait for user input 
		******************************************/
		String Option = null;
		
		while (!Done)
		{
			//Print Menu
			printConsoleMenu(args);
			//Read option
			Option = UserInput.KeyboardReadString();
	
			//////////// option 1: Arm security system ////////////
			if (Option == null) continue;
			if ( Option.equals( "1" ) )
			{
				armSecuritySystem();
			} 
			////////////option 2: Disarm security system ////////////
			else if ( Option.equals( "2" ) )
			{
				disarmSecuritySystem();
			} 
			////////////option 3: Simulate Door Break ////////////
			
			else if ( Option.equals( "3" ) )
			{
				simulateDoorBreak();
			} 
			////////////option 4: Simulate Window Break ////////////
			else if ( Option.equals( "4" ) )
			{
				simulateWindowBreak();
			}
			////////////option 5: Simulate Motion detected ////////////
			else if ( Option.equals( "5" ) )
			{
				simulateMotion();
			}
			////////////option 6: Stop Simulate Door Break ////////////
			
			else if ( Option.equals( "6" ) )
			{
				stopSimulateDoorBreak();
			} 
			////////////option 7:  Stop Simulate Window Break ////////////
			else if ( Option.equals( "7" ) )
			{
				stopSimulateWindowBreak();
			}
			////////////option 8: Stop Simulate Motion detected ////////////
			else if ( Option.equals( "8" ) )
			{
				stopSimulateMotion();
			}
			////////////option 9: Turn off Fire Alarm and Sprinkler ////////////
			else if ( Option.equals( "9" ) )
			{
				turnOffAlarm();
			} 
                        ////////////option 10: Turn on sprinkler ////////////
			else if ( Option.equals( "10" ) )
			{
				turnOnSprinkler();
			} 
			
			////////////option 11: Exit security system ////////////
			else if ( Option.equals( "11" ) )
			{
				haltSecuritySystem();
			} 
			else{
				System.out.println( "Wrong Option" );
			}
			
		}
	}

	private static void printConsoleMenu(String[] args) {
		System.out.println( "\n\n\n\n" );
		System.out.println( "Security Console: \n" );
		if (args.length != 0)
			System.out.println( "Using message manger at: " + args[0] + "\n" );
		else
			System.out.println( "Using local message manger \n" );

		System.out.println( "Select an Option: \n" );
		System.out.println( "1: Arm security system" );
		System.out.println( "2: Disarm security system" );
		System.out.println( "3: Simulate Door Break" );
		System.out.println( "4: Simulate Window Break" );
		System.out.println( "5: Simulate Motion detected" );
		System.out.println( "6: Stop Simulate Door Break" );
		System.out.println( "7: Stop Simulate Window Break" );
		System.out.println( "8: Stop Simulate Motion detected" );
		System.out.println( "9: Turn off Fire Alarm and Sprinkler");
        System.out.println( "10: Turn on Sprinkler");
		System.out.println( "11: Exit security system" );
		System.out.print( "\n>>>> " );
	}

	private static void haltSecuritySystem() {
		Done = true;

		try
		{
			sendMessageToMessageManager("Halt", HALT_SECURITY_ID );
			messageManager.UnRegister();
		} 

		catch (Exception e)
		{
			System.out.println("Error unregistering: " + e);

		} 
	}
	
	private static void simulateDoorBreak() {
		try
		{
			sendMessageToMessageManager(SIMULATE_ON, DOOR_SIMULATE_ID );
		} 

		catch (Exception e)
		{
			System.out.println("Error sending message: " + e);

		} 
	}
	
	private static void simulateWindowBreak() {
		try
		{
			sendMessageToMessageManager(SIMULATE_ON, WINDOW_SIMULATE_ID );
		} 

		catch (Exception e)
		{
			System.out.println("Error sending message: " + e);

		} 
	}
	
	private static void simulateMotion() {
		try
		{
			sendMessageToMessageManager(SIMULATE_ON, MOTION_SIMULATE_ID );
		} 

		catch (Exception e)
		{
			System.out.println("Error sending message: " + e);

		} 
	}

	private static void stopSimulateDoorBreak() {
		try
		{
				sendMessageToMessageManager(SIMULATE_OFF, DOOR_SIMULATE_ID );
		} 

		catch (Exception e)
		{
			System.out.println("Error sending message: " + e);

		} 
	}
	
	private static void stopSimulateWindowBreak() {
		try
		{
				sendMessageToMessageManager(SIMULATE_OFF, WINDOW_SIMULATE_ID );
		} 

		catch (Exception e)
		{
			System.out.println("Error sending message: " + e);

		} 
	}
	
	private static void stopSimulateMotion() {
		try
		{
				sendMessageToMessageManager(SIMULATE_OFF, MOTION_SIMULATE_ID );
		} 

		catch (Exception e)
		{
			System.out.println("Error sending message: " + e);

		} 
	}
	private static void turnOnSprinkler() {
		try
		{
                        sendMessageToMessageManager("S1", FIRE_ALARM_MSG_ID ); // turn on sprinkler
		} 

		catch (Exception e)
		{
			System.out.println("Error unregistering: " + e);

		} 
	}

        private static void turnOffAlarm() {
		try
		{
			sendMessageToMessageManager("F0", FIRE_ALARM_MSG_ID ); // turn off fire alarm
                        sendMessageToMessageManager("S0", FIRE_ALARM_MSG_ID ); // turn off sprinkler
		} 

		catch (Exception e)
		{
			System.out.println("Error unregistering: " + e);

		} 
	}

	private static void armSecuritySystem() {
		try
		{
			
			sendMessageToMessageManager("Arm", ARM_ID );
		} 

		catch (Exception e)
		{
			System.out.println("Error sending message: " + e);

		} 
	}
	
	private static void disarmSecuritySystem() {
		try
		{
			sendMessageToMessageManager("Disarm", DISARM_ID );
		} 

		catch (Exception e)
		{
			System.out.println("Error sending message: " + e);

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
		System.out.println("Sending" + message.GetMessage() + message.GetMessageId());
		try
		{
			messageManager.SendMessage( message ); // Here we send the message to the message manager.

		}

		catch (Exception e)
		{
			System.out.println("Error Confirming Message:: " + e);

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
}}
