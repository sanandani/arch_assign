
import java.nio.ByteBuffer;

/*
*********************************************************************
* File:MiddleFilter.java
* Course: 17655
* Project: Assignment 1
* Copyright: Copyright (c) 2003 Carnegie Mellon University
* Versions:
*	1.0 November 2008 - Sample Pipe and Filter code (ajl).
*
* Description:
*
* This class serves as an example for how to use the FilterRemplate to create a standard filter. This particular
* example is a simple "pass-through" filter that reads data from the filter's input port and writes data out the
* filter's output port.
*
* Parameters: 		None
*
* Internal Methods: None
*
******************************************************************************************************************/

public class ConvertTemperatureFilter extends InstrumentationFilter
{
	public void run()
    {
        long measurement;                                       //To store the intermitent measurement read
        byte[] bytes = new byte[MeasurementLength];             //To convert the computed double value to bytes array
        int id;                                                 //To store the intermitent id read
        Double temp;                                            //To store the intermitent computed value
		int bytesread = 0;										// Number of bytes read from the input file.
		int byteswritten = 0;                                   // Number of bytes written to the stream.

		// Next we write a message to the terminal to let the world know we are alive...

		System.out.print( "\n" + this.getName() + "::ConvertTemperatureFilter Reading ");

		while (true)
		{
			/************************************************************
			*	Here we convert temperature                         *
			************************************************************/

			try
			{
                                id = readId();
                                measurement = readMeasurement();
                                writeBytes(ByteBuffer.allocate(IdLength).putInt(id).array());
                                if (id==4){
                                    temp = Double.longBitsToDouble(measurement);
                                    temp = (5.0*(temp-32))/9;
                                    ByteBuffer.wrap(bytes).putDouble(temp);
                                    writeBytes(bytes);
                                }
                                else {
                                    writeBytes(ByteBuffer.allocate(MeasurementLength).putLong(measurement).array());
                                }
                                    
                                byteswritten+=IdLength+MeasurementLength;
                                bytesread+=IdLength+MeasurementLength;

			} // try

			catch (EndOfStreamException e)
			{
				ClosePorts();
				System.out.print( "\n" + this.getName() + "::ConvertTemperatureFilter Exiting; bytes read: " + bytesread + " bytes written: " + byteswritten );
				break;

			} // catch

		} // while

   } // run

} // MiddleFilter