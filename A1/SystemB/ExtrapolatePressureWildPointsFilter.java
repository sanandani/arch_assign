import java.util.ArrayList;

public class ExtrapolatePressureWildPointsFilter extends InstrumentationFilter
{
		public void run()
    {
		int bytesread = 0, numberOfBytes, indexOfPressureData, indexOfWildPoint;				// This is the number of bytes read from the stream
		InstrumentationData previous = null;
		InstrumentationData current = null;
		InstrumentationData next = null;	
		ArrayList<InstrumentationData> nextRecord = null;
		ArrayList<InstrumentationData> currentRecord = null;

		/*************************************************************
		*	First we announce to the world that we are alive...
		**************************************************************/
		System.out.print( "\n" + this.getName() + "::Reading ");
		try
		{
			currentRecord = readRecord(true);
			numberOfBytes = 12* currentRecord.size();
			indexOfWildPoint = getRecordIndexOf(currentRecord,WILDPOINT_ID);
			indexOfPressureData = getRecordIndexOf(currentRecord,PRESSURE_ID);
			current = currentRecord.get(indexOfPressureData);
			nextRecord = readRecord(false);
			next = nextRecord.get(indexOfPressureData);
			
			if(isCurrentWildPoint(currentRecord, indexOfWildPoint)){
				current.measurement = next.measurement;
				bytesread+= numberOfBytes;
				writeRecordToOutputPort(currentRecord);
				//write to stream
			}
			else{
				//write firstRecord to stream
				writeRecordToOutputPort(currentRecord);
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
				next = nextRecord.get(indexOfPressureData);
				
				if(isCurrentWildPoint(currentRecord, indexOfWildPoint)){
					current.measurement = (previous.measurement + next.measurement)/2;
					//write to stream
					writeRecordToOutputPort(currentRecord);
					bytesread+= numberOfBytes;
				}
				else{
					//write firstRecord to stream
					writeRecordToOutputPort(currentRecord);
					bytesread+= numberOfBytes;
				}

			}
				catch (EndOfStreamException e)
				{
					ClosePorts();
					//write nextRecord
					bytesread+= numberOfBytes;
					writeRecordToOutputPort(currentRecord);
					System.out.print( "\n" + this.getName() + "::Sink Exiting; bytes read: " + bytesread );
					break;
				} // catch
		}// while
	}//run


	private boolean isCurrentWildPoint(ArrayList<InstrumentationData> record, int wildPointId){
		if(wildPointId != WILDPOINT_ID) return false;
		return record.get(wildPointId).measurement == 1;
				
	} 
} // ExtrapolateWildPointsFilter