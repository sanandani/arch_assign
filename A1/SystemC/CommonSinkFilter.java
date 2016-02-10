import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CommonSinkFilter extends InstrumentationFilter {
    String filename;    
    public CommonSinkFilter(String filename) {
        super(1, 1);
        this.filename = filename;
    }
    
	final int[] MEASUREMENT_IDS_TO_WRITE = { 0, 4, 2, 3 };

	public void run() {
		int bytesread = 0, numberOfBytesPerRecord = 0;
		boolean firstRead = true;
		ArrayList<InstrumentationData> currentRecord = null;
		ArrayList<Integer> recordIndicesToWrite = null;
		try {
				BufferedWriter out = new BufferedWriter(new FileWriter(filename));
				while (true) {
					try {
						currentRecord = readRecord(firstRead);
						if(firstRead){
							numberOfBytesPerRecord = 12 * currentRecord.size();
							recordIndicesToWrite = getOrderedRecordIndicesToWrite(currentRecord, MEASUREMENT_IDS_TO_WRITE);
							System.out.print("\n" + "ExtrapolatedPressureSink" + "::Reading ");
							out.write("Time:\t\t\t\t\t\t Temperature (C):\t\t\t Altitude (ft):\t\t\t Pressure (psi):\n");
							firstRead = false;
						}
						
							writeRecord(currentRecord, out, recordIndicesToWrite);
							bytesread += numberOfBytesPerRecord;
	
					} catch (EndOfStreamException e) {
						ClosePorts();
						out.close();
						System.out.print("\nLessThan10KSink" + "Exiting; bytes read: " + bytesread);
						break;
					} // catch
				} // while
			} // try
			catch (IOException e) {
				e.printStackTrace();
			}
	}// run

} // LessThan10KSink