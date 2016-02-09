import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class ExtrapolatedPressureSink extends InstrumentationFilter {
	final int [] IDS_TO_WRITE = {0,1,2,3};
	public void run() {
		int bytesread = 0, numberOfBytes = 0, indexOfWildPoint = 0;
		ArrayList<InstrumentationData> currentRecord = null;
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("SystemB.txt"));
			
			System.out.print("\n" + "ExtrapolatedPressureSink" + "::Reading ");
			out.write("Time:\t\t Temperature (C):\t\t Altitude (m):\t\t Pressure (psi):\n");
			currentRecord = readRecord(true);
			numberOfBytes = 12 * currentRecord.size();
			indexOfWildPoint = getRecordIndexOf(currentRecord, WILDPOINT_ID);
			
			if (isCurrentWildPoint(currentRecord, indexOfWildPoint)) {
				writeWildRecord(currentRecord, indexOfWildPoint, out);
				bytesread += numberOfBytes;
			} else {
				writeNormalRecord(currentRecord, out);
				bytesread += numberOfBytes;
			}
			while (true) {
				try {
					currentRecord = readRecord(false);

					if (isCurrentWildPoint(currentRecord, indexOfWildPoint)) {
						writeWildRecord(currentRecord, indexOfWildPoint, out);
						bytesread += numberOfBytes;
					} else {
						writeNormalRecord(currentRecord, out);
						bytesread += numberOfBytes;
					}

				} catch (EndOfStreamException e) {
					ClosePorts();
					out.close();
					System.out.print( "\nExtrapolatedPressureSink" + "Exiting; bytes read: " + bytesread );
					break;
				} // catch

			} // while
		} // try
		catch (EndOfStreamException e) {
			ClosePorts();
			System.out.print( "\nExtrapolatedPressureSink" + "Exiting; bytes read: " + bytesread );

		} catch (IOException e) {
			e.printStackTrace();
		}
	}// run

	private void writeNormalRecord(ArrayList<InstrumentationData> currentRecord, BufferedWriter out)
			throws IOException {
		for (int i = 0; i < currentRecord.size(); i++)
			writeMeasurementToFile(currentRecord, out, i);

	}

	private void writeWildRecord(ArrayList<InstrumentationData> currentRecord, int indexOfWildPoint, BufferedWriter out)
			throws IOException {
//		System.out.println("\n\nWild******");
		for (int i = 0; i < currentRecord.size(); i++) {
			if (currentRecord.get(i).id == PRESSURE_ID) {
//				System.out.println(Double.toString((Double.longBitsToDouble(currentRecord.get(i).measurement))) + "*"+"\t");
				out.write(Double.toString((Double.longBitsToDouble(currentRecord.get(i).measurement))) + "*"+"\t");
			} else {
				writeMeasurementToFile(currentRecord, out, i);
			}
		}
	}

	private void writeMeasurementToFile(ArrayList<InstrumentationData> currentRecord, BufferedWriter out,
			int indexOfMeasurementInRecord) throws IOException {
		long measurement = currentRecord.get(indexOfMeasurementInRecord).measurement;
		if (indexOfMeasurementInRecord == TIME_ID) {
			Calendar TimeStamp = Calendar.getInstance();
			SimpleDateFormat TimeStampFormat = new SimpleDateFormat("yyyy MM dd::hh:mm:ss:SSS");
			out.newLine();
			TimeStamp.setTimeInMillis(measurement);
			out.write(TimeStampFormat.format(measurement) + "\t");
		}
		else if(Arrays.asList(IDS_TO_WRITE).contains(indexOfMeasurementInRecord))
			out.write(Double.toString((Double.longBitsToDouble(measurement))) + "\t");
	}

	private boolean isCurrentWildPoint(ArrayList<InstrumentationData> record, int wildPointId) {
//		System.out.println("\n\nWildID;******;" +wildPointId);
		if (wildPointId == -1)
			return false;
		return record.get(wildPointId).measurement == 1l;

	}
} // ExtrapolateWildPointsFilter