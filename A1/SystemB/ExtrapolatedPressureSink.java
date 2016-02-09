import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class ExtrapolatedPressureSink extends InstrumentationFilter {
	final int[] MEASUREMENT_IDS_TO_WRITE = { 0, 4, 5, 3 };
	Calendar TimeStamp = Calendar.getInstance();
	SimpleDateFormat TimeStampFormat = new SimpleDateFormat("yyyy MM dd::hh:mm:ss:SSS");

	public void run() {
		int bytesread = 0, numberOfBytes = 0, indexOfWildPoint = 0;
		ArrayList<InstrumentationData> currentRecord = null;
		try {

			BufferedWriter out = new BufferedWriter(new FileWriter("SystemB.txt"));
			System.out.print("\n" + "ExtrapolatedPressureSink" + "::Reading ");
			out.write("Time:\t\t\t\t\t\t Temperature (C):\t\t\t\t Altitude (m):\t\t\t\t Pressure (psi):\n");
			currentRecord = readRecord(true);
			numberOfBytes = 12 * currentRecord.size();
			indexOfWildPoint = getRecordIndexOf(currentRecord, WILDPOINT_ID);
			ArrayList<Integer> recordIndicesToWrite = getOrderedRecordIndicesToWrite(currentRecord);
			
			if (isCurrentWildPoint(currentRecord, indexOfWildPoint)) {
				writeWildRecord(currentRecord, out, recordIndicesToWrite );
				bytesread += numberOfBytes;
			} else {
				writeNormalRecord(currentRecord, out, recordIndicesToWrite);
				bytesread += numberOfBytes;
			}
			while (true) {
				try {
					currentRecord = readRecord(false);

					if (isCurrentWildPoint(currentRecord, indexOfWildPoint)) {
						writeWildRecord(currentRecord, out,recordIndicesToWrite );
						bytesread += numberOfBytes;
					} else {
						writeNormalRecord(currentRecord, out, recordIndicesToWrite);
						bytesread += numberOfBytes;
					}

				} catch (EndOfStreamException e) {
					ClosePorts();
					out.close();
					System.out.print("\nExtrapolatedPressureSink" + "Exiting; bytes read: " + bytesread);
					break;
				} // catch

			} // while
		} // try
		catch (EndOfStreamException e) {
			ClosePorts();
			System.out.print("\nExtrapolatedPressureSink" + "Exiting; bytes read: " + bytesread);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}// run

	private ArrayList<Integer> getOrderedRecordIndicesToWrite(ArrayList<InstrumentationData> currentRecord) {
		ArrayList<Integer> recordIndicesForWrite = new ArrayList<Integer>();
		for (int i = 0; i < MEASUREMENT_IDS_TO_WRITE.length; i++) {
			for (int j = 0; j < currentRecord.size(); j++) {
				if (currentRecord.get(j).id == MEASUREMENT_IDS_TO_WRITE[i]) {
					recordIndicesForWrite.add(j);
					System.out.println("\n\n\nindex:"+j);
					break;
				}
			}
		}
		return recordIndicesForWrite;
	}

	private void writeNormalRecord(ArrayList<InstrumentationData> currentRecord, BufferedWriter out, ArrayList<Integer> recordIndicesToWrite)
			throws IOException {
		for (int i = 0; i < recordIndicesToWrite.size(); i++)
			writeMeasurementToFile(currentRecord, out, recordIndicesToWrite.get(i));
	}

	private void writeWildRecord(ArrayList<InstrumentationData> currentRecord, BufferedWriter out, ArrayList<Integer> recordIndicesToWrite)
			throws IOException {
		for (int i = 0; i < recordIndicesToWrite.size(); i++) {
			int index = recordIndicesToWrite.get(i);
			System.out.println("\n\n\nindex:"+index);
			if (currentRecord.get(index).id == PRESSURE_ID) {
				out.write(Double.toString((Double.longBitsToDouble(currentRecord.get(i).measurement))) + "*" + "\t");
			} else {
				writeMeasurementToFile(currentRecord, out, index);
			}
		}
	}

	private void writeMeasurementToFile(ArrayList<InstrumentationData> currentRecord, BufferedWriter out,
			int indexInRecord) throws IOException {
		long measurement = currentRecord.get(indexInRecord).measurement;
		int id = currentRecord.get(indexInRecord).id;
		if (id == TIME_ID) {
			out.newLine();
			TimeStamp.setTimeInMillis(measurement);
			out.write(TimeStampFormat.format(measurement) + "\t");
		} else{
			out.write(Double.toString((Double.longBitsToDouble(measurement))) + "\t");
			}
	}

	private boolean isCurrentWildPoint(ArrayList<InstrumentationData> record, int wildPointId) {
		System.out.println("\n\n\nwildPointId:"+wildPointId);
		if (wildPointId == -1)
			return false;
		System.out.println("\n\nmeasurement:"+record.get(wildPointId).measurement);
		System.out.println(record.get(wildPointId).measurement == 1l);
		return record.get(wildPointId).measurement == 1l;

	}
} // ExtrapolateWildPointsFilter