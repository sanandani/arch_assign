import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ExtrapolatedPressureSink extends InstrumentationFilter {

	final int[] MEASUREMENT_IDS_TO_WRITE = { 0, 4, 5, 3 };

	public void run() {
		int bytesread = 0, numberOfBytesPerRecord = 0, indexOfWildPoint = 0;
		boolean firstRead = true;
		ArrayList<InstrumentationData> currentRecord = null;
		ArrayList<Integer> recordIndicesToWrite = null;
		try {
				BufferedWriter out = new BufferedWriter(new FileWriter("OutputB.dat"));
				while (true) {
					try {
						currentRecord = readRecord(firstRead);
						if(firstRead){
							numberOfBytesPerRecord = 12 * currentRecord.size();
							indexOfWildPoint = getRecordIndexOf(currentRecord, WILDPOINT_ID);
							recordIndicesToWrite = getOrderedRecordIndicesToWrite(currentRecord, MEASUREMENT_IDS_TO_WRITE);
							System.out.print("\n" + "ExtrapolatedPressureSink" + "::Reading ");
							out.write("Time:\t\t\t\t\t\t Temperature (C):\t\t\t Altitude (m):\t\t\t Pressure (psi):\n");
							firstRead = false;
						}
						if (isRecordCurrentWildPoint(currentRecord, indexOfWildPoint)) {
							writeWildRecord(currentRecord, out,recordIndicesToWrite );
							bytesread += numberOfBytesPerRecord;
						} else {
							writeRecord(currentRecord, out, recordIndicesToWrite);
							bytesread += numberOfBytesPerRecord;
						}
	
					} catch (EndOfStreamException e) {
						ClosePorts();
						out.close();
						System.out.print("\nExtrapolatedPressureSink" + "Exiting; bytes read: " + bytesread);
						break;
					} // catch
				} // while
			} // try
			catch (IOException e) {
				e.printStackTrace();
			}
	}// run

	private void writeWildRecord(ArrayList<InstrumentationData> currentRecord, BufferedWriter out, ArrayList<Integer> recordIndicesToWrite)
			throws IOException {
		for (int i = 0; i < recordIndicesToWrite.size(); i++) {
			int index = recordIndicesToWrite.get(i);
			if (currentRecord.get(index).id == PRESSURE_ID) {
				out.write(Double.toString((Double.longBitsToDouble(currentRecord.get(i).measurement))) + "*" + "\t");
			} else {
				writeMeasurementToFile(currentRecord, out, index);
			}
		}
	}

	private boolean isRecordCurrentWildPoint(ArrayList<InstrumentationData> record, int wildPointId) {
		if (wildPointId == -1)
			return false;
		return record.get(wildPointId).measurement == 1l;
	}
} // ExtrapolateWildPointsFilter