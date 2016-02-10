import java.util.ArrayList;
public class IdentifyPressureWildPointsFilter extends InstrumentationFilter
{
    public IdentifyPressureWildPointsFilter() {
        super(1, 1);
    }
    
	public void run()
    {
		int bytesread = 0, numberOfBytes, indexOfPressureData;
		double c,p,n;
		InstrumentationData previous = null;
		InstrumentationData current = null;
		InstrumentationData next = null;	
		ArrayList<InstrumentationData> nextRecord = null;
		ArrayList<InstrumentationData> currentRecord = null;

		/*************************************************************
		*	First we announce to the world that we are alive...
		**************************************************************/
		System.out.print( "\nIdentifyPressureWildPointsFilter" + "::Reading ");
		
		
		try
		{
			currentRecord = readRecord(true);
			numberOfBytes = 12* currentRecord.size();
			indexOfPressureData = getRecordIndexOf(currentRecord,PRESSURE_ID);
			current = currentRecord.get(indexOfPressureData);
			nextRecord = readRecord(false);
			next = nextRecord.get(indexOfPressureData);
			n = Double.longBitsToDouble(next.measurement);
			c = Double.longBitsToDouble(current.measurement);
			if(isCurrentValueAWildPoint(c,0, n,true)){
				currentRecord.add(new InstrumentationData(WILDPOINT_ID, 1l));
				writeRecordToOutputPort(currentRecord);
				bytesread+= numberOfBytes;
			}
			else{
				currentRecord.add(new InstrumentationData(WILDPOINT_ID, 0l));
				writeRecordToOutputPort(currentRecord);
				bytesread+= numberOfBytes;
			}
		}
		catch (EndOfStreamException e)
		{
			ClosePorts();
			System.out.print( "\nIdentifyPressureWildPointsFilter" + "Exiting; bytes read: " + bytesread );
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
				n = Double.longBitsToDouble(next.measurement);
				c = Double.longBitsToDouble(current.measurement);
				p = Double.longBitsToDouble(previous.measurement);
				
				if(isCurrentValueAWildPoint(c, p, n,false)){
					currentRecord.add(new InstrumentationData(WILDPOINT_ID, 1l));
					writeRecordToOutputPort(currentRecord);
					bytesread+= numberOfBytes;
				}
				else{
					currentRecord.add(new InstrumentationData(WILDPOINT_ID, 0l));
					writeRecordToOutputPort(currentRecord);
					bytesread+= numberOfBytes;
				}

			}
				catch (EndOfStreamException e)
				{
					//write nextRecord
					bytesread+= numberOfBytes;
					currentRecord.add(new InstrumentationData(WILDPOINT_ID, 0l));
					writeRecordToOutputPort(currentRecord);
					ClosePorts();
					System.out.print( "\nIdentifyPressureWildPointsFilter" + "Exiting; bytes read: " + bytesread );
					break;
				} // catch
		}// while
	}//run


	


	/*******************************************************************************
	*	Add prose
	********************************************************************************/
	
	private boolean isCurrentValueAWildPoint(double current, double previous, double next, boolean isFirstElement){
		if(current < 0) 
			{
				return true;
			}
		double c,p,n;
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
	
} // IdentifyPressureWildPointsFilter