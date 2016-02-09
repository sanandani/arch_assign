/******************************************************************************************************************
* File:InstrumentationFilter.java
* Course: 17655
* Project: Assignment 1
*
* Description:
*
* This subclass extends the BasicFilterFramework superclass and defines two methods to read Ids and read measurements 
* from input ports 
*
* Internal Methods:
*
*	public int readId()
*	public long readMeasurement()
*
******************************************************************************************************************/

import java.io.*;

public class InstrumentationFilter extends SinglePortFilterFramework
{
	final int IdLength = 4;				// This is the length of IDs in the byte stream 
	final int MeasurementLength = 8;	// This is the length of all measurements in bytes
	
	/*******************************************************************************
	*	The 'readId' function is used to read the id from filter input port. All 
	*	ids are read as stream of bytes and stored as integer.
	********************************************************************************/

	int readId() throws EndOfStreamException
    {
		byte databyte = 0;				// This is the data byte read from the stream

		int id = 0;						// This is the measurement id

		for (int i=0; i<IdLength; i++ )
		{
			databyte = ReadFilterInputPort();	// This is where we read the byte from the stream...

			id = id | (databyte & 0xFF);		// We append the byte on to ID...

			if (i != IdLength-1)				// If this is not the last byte, then slide the
			{									// previously appended byte to the left by one byte
				id = id << 8;					// to make room for the next byte we append to the ID

			}
		}
		return id;
	}// readId

	/*******************************************************************************
	* The 'readMeasurement' function is used to read the measurements from filter input 
	* port. All measurements are read as stream of bytes and stored as long value.
	********************************************************************************/
	long readMeasurement() throws EndOfStreamException
    {
		byte databyte = 0;				// This is the data byte read from the stream

		long measurement = 0;						// This is the measurement measurement

		for (int i=0; i<MeasurementLength; i++ )
		{
			databyte = ReadFilterInputPort();	// This is where we read the byte from the stream...

			measurement = measurement | (databyte & 0xFF);		// We append the byte on to ID...

			if (i != MeasurementLength-1)				// If this is not the last byte, then slide the
			{									// previously appended byte to the left by one byte
				measurement = measurement << 8;					// to make room for the next byte we append to the ID

			}
		}
		
		return measurement;
	}// readMeasurement
} //InstrumentationFilter