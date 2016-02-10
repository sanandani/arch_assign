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
import java.util.*;						// This class is used to interpret time words

public class SortTimeFilter extends InstrumentationFilter
{
	public void run()
    {
		/************************************************************************************
		*	TimeStamp is used to compute time using java.util's Calendar class.
		* 	TimeStampFormat is used to format the time value so that it can be easily printed
		*	to the terminal.
		*************************************************************************************/

		Calendar TimeStamp = Calendar.getInstance();
		
		int MeasurementLength = 8;		// This is the length of all measurements (including time) in bytes
		int IdLength = 4;				// This is the length of IDs in the byte stream

		int bytesread = 0;				// This is the number of bytes read from the stream

		long measurement;				// This is the word used to store all measurements - conversions are illustrated.
		int id;							// This is the measurement id
		
		int byteswritten = 0;           // Number of bytes written to the stream.

		
		/*************************************************************
		*	First we announce to the world that we are alive...
		**************************************************************/

		System.out.print( "\n" + this.getName() + "::Sink Reading ");
		
		/*************************************************************
		* Creating an Arraylist of arraylist which will help to store data and write it to the next filter
		/*************************************************************/
		
		ArrayList<instrumentationDataList> instrumentationDataCompleteArrayList = new ArrayList<instrumentationDataList>();
		ArrayList<instrumentationData> instrumentationDataArrayList = new ArrayList<instrumentationData>(); 
		
		while (true)
		{
			try
			{
				/***************************************************************************
				// We know that the first data coming to this filter is going to be an ID and
				// so we read Id first and increment bytes read by IdLength.
				****************************************************************************/
				id = readId();
				instrumentationData instrumentationDataObject = new instrumentationData();
				instrumentationDataObject.setId(id);
				bytesread += IdLength;

				/****************************************************************************
				// Here we read measurements. All measurement data is read as a stream of bytes
				// and stored as a long value. This permits us to do bitwise manipulation that
				// is neccesary to convert the byte stream into data words. Note that bitwise
				// manipulation is not permitted on any kind of floating point types in Java.
				// If the id = 0 then this is a time value and is therefore a long value - no
				// problem. However, if the id is something other than 0, then the bits in the
				// long value is really of type double and we need to convert the value using
				// Double.longBitsToDouble(long val) to do the conversion which is illustrated.
				// below.
				*****************************************************************************/

				measurement = readMeasurement();
				instrumentationDataObject.setMeasurement(measurement);
				measurement += MeasurementLength;
				bytesread += MeasurementLength;
				
				if(id == 0){
					instrumentationDataArrayList = new ArrayList<instrumentationData>();
					instrumentationDataArrayList.add(instrumentationDataObject);
				}else{
					instrumentationDataArrayList.add(instrumentationDataObject);
				}//if
				

				/****************************************************************************
				// Here we look for an ID of 0 which indicates this is a time measurement.
				// Every frame begins with an ID of 0, followed by a time stamp which correlates
				// to the time that each proceeding measurement was recorded. Time is stored
				// in milliseconds since Epoch. This allows us to use Java's calendar class to
				// retrieve time and also use text format classes to format the output into
				// a form humans can read. So this provides great flexibility in terms of
				// dealing with time arithmetically or for string display purposes. This is
				// illustrated below.
				****************************************************************************/

				if ( id == 0) // Check Non-Empty condition instrumentationDataArrayList here with and
				{
					instrumentationDataList instrumentationDataListObject = new instrumentationDataList();
					instrumentationDataListObject.setInstrumentationData(instrumentationDataArrayList);
					instrumentationDataCompleteArrayList.add(instrumentationDataListObject);
					TimeStamp.setTimeInMillis(measurement);
				} // if

				
			} // try

			/*******************************************************************************
			*	The EndOfStreamExeception below is thrown when you reach end of the input
			*	stream (duh). At this point, the filter ports are closed and a message is
			*	written letting the user know what is going on.
			********************************************************************************/

			catch (EndOfStreamException e)
			{
				Collections.sort(instrumentationDataCompleteArrayList, instrumentationDataList.someComp);
				int storedId = 0;
				long storedMeasurement = 0L;
				for(int j = 0; j < instrumentationDataCompleteArrayList.size(); j++) {   
					for(int k = 0; k < instrumentationDataCompleteArrayList.get(j).getInstrumentationData().size(); k++)
					{   
						storedId = instrumentationDataCompleteArrayList.get(j).getInstrumentationData().get(k).id;
						storedMeasurement = instrumentationDataCompleteArrayList.get(j).getInstrumentationData().get(k).measurement;
						writeBytes(ByteBuffer.allocate(IdLength).putInt(storedId).array());
						writeBytes(ByteBuffer.allocate(MeasurementLength).putLong(storedMeasurement).array());
						byteswritten+=IdLength+MeasurementLength;
						//System.out.println("Position ======="+j+"=====ID======"+instrumentationDataCompleteArrayList.get(j).getInstrumentationData().get(k).id);
						//System.out.println("Position ======="+j+"====="+instrumentationDataCompleteArrayList.get(j).getInstrumentationData().get(k).measurement);	
					} //for
				}  // for
				ClosePorts();
				System.out.print( "\n" + this.getName() + "::SortTimeFilterExecuted; bytes read: " + bytesread + " bytes written: " + byteswritten );
				break;

			} // catch
		} // while

   } // run

} // MiddleFilter

/***************************************************************************
// Storing the the data id and measurement in instrumentdata object
// This object will be stored in a ArrayList which will later used for sorting
****************************************************************************/
class instrumentationData {
	int id;
	long measurement;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getMeasurement() {
		return measurement;
	}
	public void setMeasurement(long measurement) {
		this.measurement = measurement;
	}
}

/***************************************************************************
//Storing the the data id and measurement in instrumentdata object and 
//then storing the data in ArrayList instrumentationDataList which will
// help in sorting the data 
****************************************************************************/
class instrumentationDataList {
	ArrayList<instrumentationData> instrumentationData;
	
	public ArrayList<instrumentationData> getInstrumentationData() {
		return instrumentationData;
	}

	public void setInstrumentationData(ArrayList<instrumentationData> instrumentationData) {
		this.instrumentationData = instrumentationData;
	}
		
	public static Comparator<instrumentationDataList> someComp = new Comparator<instrumentationDataList>() {
		@Override
		public int compare(instrumentationDataList o1, instrumentationDataList o2) {
			long m1 = 0L;
			long m2 = 0L;
			for(int j = 0; j < 6; j++) { 
				if(o1.getInstrumentationData().get(j).getId() == 0){
					m1 = o1.getInstrumentationData().get(j).getMeasurement();
				}
			}
			for(int k = 0; k < 6; k++) { 
				if(o2.getInstrumentationData().get(k).getId() == 0){
					m2 = o2.getInstrumentationData().get(k).getMeasurement();
				}
			}
			return (int)(m1-m2);
		}
	};
}