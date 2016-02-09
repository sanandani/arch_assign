import java.util.ArrayList;

/******************************************************************************************************************
* File:WildPointFilter.java
* Course: 17655
* Project: Assignment 1
* Copyright: Copyright (c) 2003 Carnegie Mellon University
* Versions:
*	1.0 November 2008 - Sample Pipe and Filter code (ajl).
*
* Description:
*
*	1) It parses the input stream and "decommutates" the measurement ID
*	2) It parses the input steam for measurments and "decommutates" measurements, storing the bits in a long word.
*
* This filter illustrates how to convert the byte stream data from the upstream filterinto useable data found in
* the stream: namely time (long type) and measurements (double type).
*
*
* Parameters: 	None
*
* Internal Methods: None
*
******************************************************************************************************************/

public class WildPointFilter extends InstrumentationFilter
{
	private static final int PRESSURE_ID = 3;
	public void run()
    {
		int bytesread = 0, numberOfBytes, indexOfPressureData;				// This is the number of bytes read from the stream
		InstrumentationData previous = null;
		InstrumentationData current = null;
		InstrumentationData next = null;	
		ArrayList<InstrumentationData> nextRecord = null;
		ArrayList<InstrumentationData> currentRecord = null;

		/*************************************************************
		*	First we announce to the world that we are alive...
		**************************************************************/
		System.out.print( "\n" + this.getName() + "::Readingbla ");
		try
		{
			currentRecord = readRecord(true);
			numberOfBytes = 12* currentRecord.size();
			indexOfPressureData = getIndexOfPressureData(currentRecord);
			current = currentRecord.get(indexOfPressureData);
			nextRecord = readRecord(false);
			next = nextRecord.get(indexOfPressureData);
			
			if(isCurrentWildPoint(current.measurement,0, next.measurement,true)){
//				current.measurement = next.measurement;
				currentRecord.add(new InstrumentationData(currentRecord.size(), 1));
				bytesread+= numberOfBytes;
				//write to stream
			}
			else{
				currentRecord.add(new InstrumentationData(currentRecord.size(), 0));
				//write firstRecord to stream
				bytesread+= numberOfBytes;
			}
		}
		catch (EndOfStreamException e)
		{
			ClosePorts();
			System.out.print( "\n" + this.getName() + "::Sink Exiting; bytes read: " + bytesread );
			return;

		} 
		
		while (true)
		{
			try{
				previous = current;
				current = next;
				currentRecord = nextRecord;
				nextRecord =  readRecord(false);
				
				if(isCurrentWildPoint(current.measurement, previous.measurement, next.measurement,false)){
//					current.measurement = (previous.measurement + next.measurement)/2;
					currentRecord.add(new InstrumentationData(currentRecord.size(), 1));
					//write to stream
					printData(currentRecord);
					bytesread+= numberOfBytes;
				}
				else{
					currentRecord.add(new InstrumentationData(currentRecord.size(), 0));
					//write firstRecord to stream
					printData(currentRecord);
					bytesread+= numberOfBytes;
				}

			}
			/****************************************************************************
			Add prose
			*****************************************************************************/
	
				catch (EndOfStreamException e)
				{
					ClosePorts();
					//write nextRecord
					printData(currentRecord);
					bytesread+= numberOfBytes;
					System.out.print( "\n" + this.getName() + "::Sink Exiting; bytes read: " + bytesread );
				} // catch
		}// while
	}//run
		
		
	
	private int getIndexOfPressureData(ArrayList<InstrumentationData> firstRecord) {
		for (int i = 0; i < firstRecord.size(); i++) {
			if(firstRecord.get(i).id == PRESSURE_ID)
				return i;
		} 
		return 0;
	}
	
	private void printData(ArrayList<InstrumentationData> record){
		for (int i = 0; i < record.size(); i++) {
			InstrumentationData data = record.get(i);
			System.out.print("id:" + data.id + " :" + data.measurement);
		} 
		System.out.print("\n");
	}

	//function to read a record of data
	private ArrayList<InstrumentationData> readRecord(boolean isFirstRead) throws EndOfStreamException{
		int id = -1;
		long measurement;
		ArrayList<InstrumentationData> record = new ArrayList<InstrumentationData>();
		if(!isFirstRead){
			measurement = readMeasurement();
			record.add(new InstrumentationData(id, measurement));
		
		}
		
			while(true){
				if(isFirstRead){
					id = 0;
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

	/*******************************************************************************
	*	Add prose
	********************************************************************************/
	private boolean isCurrentWildPoint(long current, long previous, long next, boolean isFirstElement){
		if(current < 0) 
			{
			return true;
			}
		long c,p,n;
		c = Math.abs(current);
		n = Math.abs(next);
		if(!isFirstElement)
		{
			p = Math.abs(previous);
			if(c > p && Math.abs(c - p) > 10) 
				{
				return true;
				}
		} 
		
		if(c > n && Math.abs(c - n) > 10) 
			{
			return true;
			}
		return false;
	}
	
	public class InstrumentationData{
		private int id;
		private long measurement;

		public InstrumentationData(int id, long measurement){
			this.id = id;
			this.measurement = measurement;
		}
	} // InstrumentationData
} // WildPointFilter