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

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class InstrumentationFilter extends SinglePortFilterFramework
{
	final int IdLength = 4;				// This is the length of IDs in the byte stream 
	final int MeasurementLength = 8;	// This is the length of all measurements in bytes
	final int PRESSURE_ID = 3;
	final int WILDPOINT_ID = 100;
	final int TIME_ID = 0;
	
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

	/********************************************************************************
	* The 'writeBytes' function is used to write the measurements from filter output* 
	* port. All measurements are written as stream of bytes.						*
	********************************************************************************/

	void writeBytes(byte[] datum)
	{
		for(int i =0;i<datum.length;i++) 
        {
            WriteFilterOutputPort(datum[i]);
        }
	}
	
   int getRecordIndexOf(ArrayList<InstrumentationData> record, int id) {
		for (int i = 0; i < record.size(); i++) {
			if(record.get(i).id == id)
				return i;
		} 
		return -1;
	}
	
   ArrayList<InstrumentationData> readRecord(boolean isFirstRead) throws EndOfStreamException{
		int id = 0;
		long measurement;
		ArrayList<InstrumentationData> record = new ArrayList<InstrumentationData>();
		if(!isFirstRead){
			measurement = readMeasurement();
			record.add(new InstrumentationData(id, measurement));
		
		}
		while(true)
		{
			if(isFirstRead){
				id = readId();
				measurement = readMeasurement();
				isFirstRead = false;
			}
			else{
				id = readId();
				if(id == 0)
					break;
				measurement = readMeasurement();
			}
			record.add(new InstrumentationData(id, measurement));
	}
		return record;
	}
   
   void writeRecordToOutputPort(ArrayList<InstrumentationData> record){
		for (int i = 0; i < record.size(); i++) {
			InstrumentationData data = record.get(i);
			writeBytes(ByteBuffer.allocate(IdLength).putInt(data.id).array());
			writeBytes(ByteBuffer.allocate(MeasurementLength).putLong(data.measurement).array());
			System.out.print("id:" + data.id + " :" + Double.toString((Double.longBitsToDouble(data.measurement))));
		} 
		System.out.print("\n");
	}
   
	public class InstrumentationData{
		public int id;
		public long measurement;

		public InstrumentationData(int id, long measurement){
			this.id = id;
			this.measurement = measurement;
		}
	}
} //InstrumentationFilter