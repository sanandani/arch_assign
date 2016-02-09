import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class PressureWildPointsSink extends InstrumentationFilter
{
		public void run()
    {
		int bytesread = 0, numberOfBytes = 0, indexOfPressureData = 0, indexOfWildPoint = 0;				// This is the number of bytes read from the stream
		ArrayList<InstrumentationData> currentRecord = null;
		try
		{
		BufferedWriter out = new BufferedWriter(new FileWriter("WildPoint.txt"));

		/*************************************************************
		*	First we announce to the world that we are alive...
		**************************************************************/
		System.out.print( "\n" + this.getName() + "::Reading ");
			
		out.write("Time:\t Pressure(PSI):");
		currentRecord = readRecord(true);
		numberOfBytes = 12* currentRecord.size();
		indexOfWildPoint = getRecordIndexOf(currentRecord,WILDPOINT_ID);
		indexOfPressureData = getRecordIndexOf(currentRecord,PRESSURE_ID);
		
		if(isCurrentWildPoint(currentRecord, indexOfWildPoint)){
			writeMeasurementToFile(currentRecord,out, TIME_ID);
			writeMeasurementToFile(currentRecord,out, indexOfPressureData);
		}
		bytesread+= numberOfBytes;
		while (true)
		{
			try{
				currentRecord =  readRecord(false);
				
				if(isCurrentWildPoint(currentRecord, indexOfWildPoint)){
					writeMeasurementToFile(currentRecord,out, TIME_ID);
					writeMeasurementToFile(currentRecord,out, indexOfPressureData);
				}
				bytesread+= numberOfBytes;

				}
				catch (EndOfStreamException e)
				{
					ClosePorts();
					out.close();
					System.out.print( "\n" + this.getName() + "::Sink Exiting; bytes read: " + bytesread );
					break;
				} // catch
			
		}// while
		}//try
		catch (EndOfStreamException e)
		{
			ClosePorts();
			System.out.print( "\n" + this.getName() + "::Sink Exiting; bytes read: " + bytesread );

		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}//run


	private void writeMeasurementToFile(ArrayList<InstrumentationData> currentRecord, BufferedWriter out, int indexOfMeasurementInRecord) throws IOException {
			long measurement = currentRecord.get(indexOfMeasurementInRecord).measurement;
			if(indexOfMeasurementInRecord == TIME_ID){
				Calendar TimeStamp = Calendar.getInstance();
				SimpleDateFormat TimeStampFormat = new SimpleDateFormat("yyyy MM dd::hh:mm:ss:SSS");
				out.newLine();
				TimeStamp.setTimeInMillis(measurement);
				out.write(TimeStampFormat.format(measurement)+"\t");
			}
				
				out.write(Double.toString((Double.longBitsToDouble(measurement)))+"\t");
		}


	private boolean isCurrentWildPoint(ArrayList<InstrumentationData> record, int wildPointId){
		if(wildPointId != WILDPOINT_ID) return false;
		return record.get(wildPointId).measurement == 1;
				
	} 
} // ExtrapolateWildPointsFilter