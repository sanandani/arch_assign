import java.util.ArrayDeque;
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

		int bytesread = 0,indexOfPressureData;				// This is the number of bytes read from the stream
		long measurement;				// This is the word used to store all measurements - conversions are illustrated.
		int id;							// This is the measurement id
		InstrumentationData previous;
		InstrumentationData current;
		InstrumentationData next;	
		ArrayDeque <ArrayList<InstrumentationData>> queue = new ArrayDeque <ArrayList<InstrumentationData>>();
		
		ArrayList<InstrumentationData> firstRecord = readRecord(false);
		queue.add(firstRecord);
		indexOfPressureData = getIndexOfPressureData(firstRecord);
		
		/*************************************************************
		*	First we announce to the world that we are alive...
		**************************************************************/
		System.out.print( "\n" + this.getName() + "::Reading ");
		while (true)
		{
			try
			{
				/****************************************************************************
				Add prose
				*****************************************************************************/
				id = readId();
				bytesread += IdLength;

				/****************************************************************************
				Add prose
				*****************************************************************************/
				measurement = readMeasurement();
				measurement += MeasurementLength;

			} // try


			/****************************************************************************
			Add prose
			*****************************************************************************/
	
			catch (EndOfStreamException e)
			{
				ClosePorts();
				System.out.print( "\n" + this.getName() + "::Sink Exiting; bytes read: " + bytesread );
				break;

			} // catch

		} // while
   } // run
	
	private int getIndexOfPressureData(ArrayList<InstrumentationData> firstRecord) {
		for (int i = 0; i < firstRecord.size(); i++) {
			if(firstRecord.get(i).getId() == PRESSURE_ID)
				return i;
		} 
		return 0;
	}

	//function to read a record of data
	private ArrayList<InstrumentationData> readRecord(boolean isFirstRead) {
		int id = -1;
		long measurement;
		ArrayList<InstrumentationData> record = new ArrayList<InstrumentationData>();
		try{
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
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return record;
	}

	/*******************************************************************************
	*	Add prose
	********************************************************************************/
	public class InstrumentationData{
		private int id;
		private long measurement;

	public InstrumentationData(int id, long measurement){
		this.id = id;
		this.measurement = measurement;
	}

	public int getId(){
		return this.id;
	}

	public long getMeasurement(){
		return this.measurement;
	}
} // InstrumentationData
} // SingFilter