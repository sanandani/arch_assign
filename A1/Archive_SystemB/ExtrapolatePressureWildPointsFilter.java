import java.util.ArrayList;

public class ExtrapolatePressureWildPointsFilter extends InstrumentationFilter {

	public void run() {
		int bytesread = 0, numberOfBytesPerRecord = 0, indexOfWildPoint = 0, indexOfPressureData = 0;
		InstrumentationData previous = null;
		InstrumentationData current = null;
		InstrumentationData next = null;	
		ArrayList<InstrumentationData> nextRecord = null;
		ArrayList<InstrumentationData> currentRecord = null;
		try {
				currentRecord = readRecord(true);
				
				numberOfBytesPerRecord = 12 * currentRecord.size();
				
				indexOfWildPoint = getRecordIndexOf(currentRecord, WILDPOINT_ID);
				
				indexOfPressureData = getRecordIndexOf(currentRecord,PRESSURE_ID);
				
				current = currentRecord.get(indexOfPressureData);
				
				nextRecord = readRecord(false);
				
				next = nextRecord.get(indexOfPressureData);
				
				
				if (isRecordCurrentWildPoint(currentRecord, indexOfWildPoint)) {
					current.measurement = next.measurement;
					bytesread+= numberOfBytesPerRecord;
					writeRecordToOutputPort(currentRecord);
				} else {
					writeRecordToOutputPort(currentRecord);
					bytesread+= numberOfBytesPerRecord;
				}
				
				while (true) {
					try {
						previous = current;
						current = next;
						currentRecord = nextRecord;
						nextRecord =  readRecord(false);
						next = nextRecord.get(indexOfPressureData);
						if (isRecordCurrentWildPoint(currentRecord, indexOfWildPoint)) {
							double p = Double.longBitsToDouble(previous.measurement); 
							double n = Double.longBitsToDouble(next.measurement);
							current.measurement = Double.doubleToLongBits((p + n)/2);
							writeRecordToOutputPort(currentRecord);
							bytesread+= numberOfBytesPerRecord;
						}
						else{
							//write firstRecord to stream
							writeRecordToOutputPort(currentRecord);
							bytesread+= numberOfBytesPerRecord;
						}
	
					} catch (EndOfStreamException e) {
						ClosePorts();
						System.out.print("\nExtrapolatedPressureSink" + "Exiting; bytes read: " + bytesread);
						break;
					} // catch
				} // while
			} // try
			catch (EndOfStreamException e) {
				ClosePorts();
				System.out.print("\nExtrapolatedPressureSink" + "Exiting; bytes read: " + bytesread);
	
			}
	}// run

	private boolean isRecordCurrentWildPoint(ArrayList<InstrumentationData> record, int wildPointId) {
		if (wildPointId == -1)
			return false;
		return record.get(wildPointId).measurement == 1l;
	}
} // ExtrapolateWildPointsFilter