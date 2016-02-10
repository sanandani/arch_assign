
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;

/******************************************************************************************************************
* File:RemoveAttributesFilter.java
* Course: 17655
* Project: Assignment 1
*
* Description:
*
* This class filter all Time,Temperature and Altitude measurements
*
******************************************************************************************************************/

public class RemoveAttributesFilter extends InstrumentationFilter
{
	public void run()
    {

                long measurement;                                       //To store the intermitent measurement read
                int id;                                                 //To store the intermitent id read
		int bytesread = 0;					// Number of bytes read from the input file.
		int byteswritten = 0;                                   // Number of bytes written to the stream.
                int count=0;
		// Next we write a message to the terminal to let the world know we are alive...

		System.out.print( "\n" + this.getName() + "::RemoveAttributesFilter Reading ");
		while (true)
		{
			/*************************************************************
			*   Here we filter required IDs and respective Measurements  *
			*************************************************************/

			try
			{
                                id = readId();
                                measurement = readMeasurement();
                                if(id==0||id==4||id==5)
                                {
                                    count++;
                                    if(count ==1) {
                                        System.out.println("test"+id);
                                    }
                                    writeBytes(ByteBuffer.allocate(IdLength).putInt(id).array());
                                    writeBytes(ByteBuffer.allocate(MeasurementLength).putLong(measurement).array());
                                    byteswritten+=IdLength+MeasurementLength;
                                }
                                bytesread+=IdLength+MeasurementLength;

			} // try

			catch (FilterFramework.EndOfStreamException e)
			{
				ClosePorts();
				System.out.print( "\n" + this.getName() + "::RemoveAttributesFilter Exiting; bytes read: " + bytesread + " bytes written: " + byteswritten );
				break;

			} // catch

		} // while

   } // run

} // MiddleFilter