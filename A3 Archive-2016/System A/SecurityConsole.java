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
	
			if ( Option.equals( "1" ) )
			{
				armSecuritySystem();
			} 
			////////////option 2: Disarm security system ////////////
			else if ( Option.equals( "2" ) )
			{
				disarmSecuritySystem();
			} 
			////////////option 3: Exit security system ////////////
			else if ( Option.equals( "3" ) )
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
		System.out.println( "3: Exit security system" );
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
