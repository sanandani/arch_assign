
import java.util.ArrayList;

/*
*********************************************************************
* File:MiddleFilter.java
* Course: 17655
* Project: Assignment 1
* Copyright: Copyright (c) 2003 Carnegie Mellon University
* Versions:
*	1.0 November 2008 - Sample Pipe and Filter code (ajl).
*
* Description:
*
* This class serves as an example for how to use the FilterRemplate to create a standard filter. This particular
* example is a simple "pass-through" filter that reads data from the filter's input port and writes data out the
* filter's output port.
*
* Parameters: 		None
*
* Internal Methods: None
*
******************************************************************************************************************/
public class LessThan10KFilter extends InstrumentationFilter {

    private int numberOfBytesPerRecord;

    public LessThan10KFilter() {
        super(1, 2);
    }

    public void run() {
        int bytesread = 0;										// Number of bytes read from the input file.
        int byteswritten = 0;                                   // Number of bytes written to the stream.
        boolean firstRead = true;								// To track the first iteration of reading row from pipes
        ArrayList<InstrumentationData> currentRecord = null;	// To temporarily store the current row of record
        int indexOfAltitudeInRecord = 0;						// To fetch the index of the altitude column
        // Next we write a message to the terminal to let the world know we are alive...

        System.out.print("\n" + this.getName() + "::LessThan10KFilter Reading ");

        while (true) {
            /**
             * **********************************************************
             * Here we remove greater than 10000 feet altitude rows *
			***********************************************************
             */
            try {
                //fetch each row 
                currentRecord = readRecord(firstRead);
                if (firstRead) {
                    numberOfBytesPerRecord = 12 * currentRecord.size();
                    indexOfAltitudeInRecord = getRecordIndexOf(currentRecord, ALTITUDE_ID);
                    firstRead = false;
                }
                Double altmeasurement = Double.longBitsToDouble(currentRecord.get(indexOfAltitudeInRecord).measurement);
                //check for less than 10k value of altitude and accordingly update it
                if (altmeasurement > 9999) {
                    //write to sink filter the >9999 values             
                    //all the columns     
                    writeRecordToOutputPort(currentRecord, 0);
                } else {
                    //otherwise pass it to next filter
                    writeRecordToOutputPort(currentRecord, 1);
                            byteswritten += numberOfBytesPerRecord;	// we only count the bytes written for the next filters other than sink filter
                }
                bytesread += numberOfBytesPerRecord;

            } // try
            catch (EndOfStreamException e) {
                ClosePorts();
                System.out.print("\n" + this.getName() + "::LessThan10KFilter Exiting; bytes read: " + bytesread + " bytes written: " + byteswritten);
                break;

            } // catch

        } // while

    } // run

} // MiddleFilter
