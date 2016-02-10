
/**
 * ****************************************************************************************************************
 * File:InstrumentationFilter.java
 * Course: 17655
 * Project: Assignment 1
 *
 * Description:
 *
 * This subclass extends the BasicFilterFramework superclass and defines two methods to read Ids and read measurements
 * from input ports
 *
 * Internal Methods:
 *
 *	public int readId()
 *	public long readMeasurement()
 *
 *****************************************************************************************************************
 */

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class InstrumentationFilter extends MultiPortFilterFramework {

    final int IdLength = 4;				// This is the length of IDs in the byte stream 
    final int MeasurementLength = 8;	// This is the length of all measurements in bytes
    final int PRESSURE_ID = 3;
    final int WILDPOINT_ID = 100;
    final int ALTITUDE_ID = 2;
    final int TIME_ID = 0;
    protected static final int DEFAULT_INPUT=0;
    protected static final int DEFAULT_OUTPUT=0;
    
    

    Calendar TimeStamp = Calendar.getInstance();
    SimpleDateFormat TimeStampFormat = new SimpleDateFormat("yyyy MM dd::hh:mm:ss:SSS");

    public InstrumentationFilter(int noOfInput, int noOfOuput) {
        super(noOfInput, noOfOuput);
    }

    /**
     * *****************************************************************************
     * The 'readId' function is used to read the id from filter input port. All
     * ids are read as stream of bytes and stored as integer.
	*******************************************************************************
     */

    int readId() throws EndOfStreamException {
        return readIdFromPort(DEFAULT_INPUT);
    }// readId
    
    int readId(int portNum) throws EndOfStreamException {
        return readIdFromPort(portNum);
    }// readId
    
     int readIdFromPort(int portNum) throws EndOfStreamException {
        byte databyte = 0;				// This is the data byte read from the stream

        int id = 0;						// This is the measurement id

        for (int i = 0; i < IdLength; i++) {
            databyte = ReadFilterInputPort(portNum);	// This is where we read the byte from the stream...

            id = id | (databyte & 0xFF);		// We append the byte on to ID...

            if (i != IdLength - 1) // If this is not the last byte, then slide the
            {									// previously appended byte to the left by one byte
                id = id << 8;					// to make room for the next byte we append to the ID

            }
        }
        return id;
    }// readId

    /**
     * *****************************************************************************
     * The 'readMeasurement' function is used to read the measurements from
     * filter input port. All measurements are read as stream of bytes and
     * stored as long value.
	*******************************************************************************
     */
    long readMeasurement() throws EndOfStreamException {
        return readMeasurementFromPort(DEFAULT_INPUT);
    }// readMeasurement
    long readMeasurement(int portNum) throws EndOfStreamException {
        return readMeasurementFromPort(portNum);
    }// readMeasurement
    
    long readMeasurementFromPort(int portNum) throws EndOfStreamException {
        byte databyte = 0;				// This is the data byte read from the stream

        long measurement = 0;						// This is the measurement measurement

        for (int i = 0; i < MeasurementLength; i++) {
            databyte = ReadFilterInputPort(portNum);	// This is where we read the byte from the stream...

            measurement = measurement | (databyte & 0xFF);		// We append the byte on to ID...

            if (i != MeasurementLength - 1) // If this is not the last byte, then slide the
            {									// previously appended byte to the left by one byte
                measurement = measurement << 8;					// to make room for the next byte we append to the ID

            }
        }

        return measurement;
    }// readMeasurement

    
    
    /**
     * ******************************************************************************
     * The 'writeBytes' function is used to write the measurements from filter
     * output* port. All measurements are written as stream of bytes.	*
	*******************************************************************************
     */
    void writeBytes(byte[] datum) {
        for (int i = 0; i < datum.length; i++) {
            WriteFilterOutputPort(DEFAULT_OUTPUT,datum[i]);
        }
    }
    
    void writeBytes(int outputport, byte[] datum) {
        for (int i = 0; i < datum.length; i++) {
            WriteFilterOutputPort(outputport,datum[i]);
        }
    }

    int getRecordIndexOf(ArrayList<InstrumentationData> record, int id) {
        for (int i = 0; i < record.size(); i++) {
            if (record.get(i).id == id) {
                return i;
            }
        }
        return -1;
    }

    ArrayList<InstrumentationData> readRecord(boolean isFirstRead) throws EndOfStreamException {
        int id = 0;
        long measurement;
        ArrayList<InstrumentationData> record = new ArrayList<InstrumentationData>();
        if (!isFirstRead) {
            measurement = readMeasurement();
            record.add(new InstrumentationData(id, measurement));

        }
        while (true) {
            if (isFirstRead) {
                id = readId();
                measurement = readMeasurement();
                isFirstRead = false;
            } else {
                id = readId();
                if (id == 0) {
                    break;
                }
                measurement = readMeasurement();
            }
            record.add(new InstrumentationData(id, measurement));
        }
        return record;
    }

    void writeRecordToOutputPort(ArrayList<InstrumentationData> record) {
        for (int i = 0; i < record.size(); i++) {
            InstrumentationData data = record.get(i);
            writeBytes(ByteBuffer.allocate(IdLength).putInt(data.id).array());
            writeBytes(ByteBuffer.allocate(MeasurementLength).putLong(data.measurement).array());
        }
    }
     void writeRecordToOutputPort(ArrayList<InstrumentationData> record, int portNum) {
        for (int i = 0; i < record.size(); i++) {
            InstrumentationData data = record.get(i);
            writeBytes(portNum,ByteBuffer.allocate(IdLength).putInt(data.id).array());
            writeBytes(portNum,ByteBuffer.allocate(MeasurementLength).putLong(data.measurement).array());
        }
    }

    public class InstrumentationData {

        public int id;
        public long measurement;

        public InstrumentationData(int id, long measurement) {
            this.id = id;
            this.measurement = measurement;
        }
    }

    void writeMeasurementToFile(ArrayList<InstrumentationData> currentRecord, BufferedWriter out,
            int indexInRecord) throws IOException {
        long measurement = currentRecord.get(indexInRecord).measurement;
        int id = currentRecord.get(indexInRecord).id;
        if (id == TIME_ID) {
            out.newLine();
            TimeStamp.setTimeInMillis(measurement);
            out.write(TimeStampFormat.format(measurement) + "\t");
        } else {
            out.write(Double.toString((Double.longBitsToDouble(measurement))) + "\t");
        }
    }

    void writeRecord(ArrayList<InstrumentationData> currentRecord, BufferedWriter out, ArrayList<Integer> recordIndicesToWrite)
            throws IOException {
        for (int i = 0; i < recordIndicesToWrite.size(); i++) {
            writeMeasurementToFile(currentRecord, out, recordIndicesToWrite.get(i));
        }
    }

    ArrayList<Integer> getOrderedRecordIndicesToWrite(ArrayList<InstrumentationData> currentRecord, int[] idsToWrite) {
        ArrayList<Integer> recordIndicesForWrite = new ArrayList<Integer>();
        for (int i = 0; i < idsToWrite.length; i++) {
            for (int j = 0; j < currentRecord.size(); j++) {
                if (currentRecord.get(j).id == idsToWrite[i]) {
                    recordIndicesForWrite.add(j);
                    break;
                }
            }
        }
        return recordIndicesForWrite;
    }
} //InstrumentationFilter
