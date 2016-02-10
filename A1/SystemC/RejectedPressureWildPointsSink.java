
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class RejectedPressureWildPointsSink extends InstrumentationFilter {

    public RejectedPressureWildPointsSink() {
        super(1, 1);
    }
    final int[] MEASUREMENT_IDS_TO_WRITE = {0, 3};

    public void run() {
        int bytesread = 0, numberOfBytesPerRecord = 0, indexOfWildPoint = 0;
        ArrayList<InstrumentationData> currentRecord = null;
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("WildPoints.dat"));
            System.out.print("\n" + "ExtrapolatedPressureSink" + "::Reading ");
            out.write("Time:\t\t\t\t\t\t Pressure (psi):\n");

            currentRecord = readRecord(true);

            numberOfBytesPerRecord = 12 * currentRecord.size();

            indexOfWildPoint = getRecordIndexOf(currentRecord, WILDPOINT_ID);

            ArrayList<Integer> recordIndicesToWrite = getOrderedRecordIndicesToWrite(currentRecord, MEASUREMENT_IDS_TO_WRITE);

            if (isRecordCurrentWildPoint(currentRecord, indexOfWildPoint)) {
                writeRecord(currentRecord, out, recordIndicesToWrite);
                bytesread += numberOfBytesPerRecord;
            } else {
                bytesread += numberOfBytesPerRecord;
            }

            while (true) {
                try {
                    currentRecord = readRecord(false);
                    if (isRecordCurrentWildPoint(currentRecord, indexOfWildPoint)) {
                        writeRecord(currentRecord, out, recordIndicesToWrite);
                        bytesread += numberOfBytesPerRecord;
                    } else {
                        bytesread += numberOfBytesPerRecord;
                    }

                } catch (EndOfStreamException e) {
                    ClosePorts();
                    out.close();
                    System.out.print("\nRejectedPressureWildPointsSink" + "Exiting; bytes read: " + bytesread);
                    break;
                } // catch
            } // while
        } // try
        catch (EndOfStreamException e) {
            ClosePorts();
            System.out.print("\nRejectedPressureWildPointsSink" + "Exiting; bytes read: " + bytesread);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }// run

    private boolean isRecordCurrentWildPoint(ArrayList<InstrumentationData> record, int wildPointId) {
        if (wildPointId == -1) {
            return false;
        }
        return record.get(wildPointId).measurement == 1l;
    }
} // ExtrapolateWildPointsFilter
