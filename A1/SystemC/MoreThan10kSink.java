//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class LessThan10KSink extends InstrumentationFilter {
//        
//    public LessThan10KSink() {
//        super(1, 1);
//    }
//    
//	final int[] MEASUREMENT_IDS_TO_WRITE = { 0, 4, 5, 3 };
//
//	public void run() {
//		int bytesread = 0, numberOfBytesPerRecord = 0;
//		boolean firstRead = true;
//		ArrayList<InstrumentationData> currentRecord = null;
//		ArrayList<Integer> recordIndicesToWrite = null;
//		try {
//				BufferedWriter out = new BufferedWriter(new FileWriter("LessThan10KSink.dat"));
//				while (true) {
//					try {
//						currentRecord = readRecord(firstRead);
//						if(firstRead){
//							numberOfBytesPerRecord = 12 * currentRecord.size();
//							recordIndicesToWrite = getOrderedRecordIndicesToWrite(currentRecord, MEASUREMENT_IDS_TO_WRITE);
//							System.out.print("\n" + "ExtrapolatedPressureSink" + "::Reading ");
//							out.write("Time:\t\t\t\t\t\t Temperature (C):\t\t\t Altitude (m):\t\t\t Pressure (psi):\n");
//							firstRead = false;
//						}
//						
//							writeRecord(currentRecord, out, recordIndicesToWrite);
//							bytesread += numberOfBytesPerRecord;
//	
//					} catch (EndOfStreamException e) {
//						ClosePorts();
//						out.close();
//						System.out.print("\nLessThan10KSink" + "Exiting; bytes read: " + bytesread);
//						break;
//					} // catch
//				} // while
//			} // try
//			catch (IOException e) {
//				e.printStackTrace();
//			}
//	}// run
//
//} // LessThan10KSink