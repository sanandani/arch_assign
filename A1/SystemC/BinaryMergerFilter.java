
/**
 * ****************************************************************************************************************
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
 *****************************************************************************************************************
 */
public class BinaryMergerFilter extends MultiPortFilterFramework {

    public static final int NO_OF_INPUT = 2;
    public static final int NO_OF_OUTPUT = 1;
    public static final int INPUT0 = 0;
    public static final int INPUT1 = 1;
    public static final int DEFAULT_OUTPUT = 0;

    public BinaryMergerFilter() {
        super(NO_OF_INPUT, NO_OF_OUTPUT);
    }

    @Override
    public void run() {

        // Next we write a message to the terminal to let the world know we are alive...
        System.out.println("\n" + this.getName() + "::BinaryMerger Reading ");

        /**
         * ***********************************************************
         * Here we read a byte and write a byte
         * ***********************************************************
         */
        for (int i = 0; i < NO_OF_INPUT; i++) {
            int bytesread = 0;					// Number of bytes read from the input file.
            int byteswritten = 0;				// Number of bytes written to the stream.
            byte databyte = 0;					// The byte of data read from the file
            while (true) {
                try {
                    databyte = ReadFilterInputPort(i);
                    bytesread++;
                   
                    WriteFilterOutputPort(DEFAULT_OUTPUT, databyte);
                    byteswritten++;
                } // try
                catch (EndOfStreamException e) {
                    CloseInputPort(i);
                    System.out.print("\n" + this.getName() + "::Binary Merger Done Reading Input" + i + "; bytes read: " + bytesread + " bytes written: " + byteswritten);
                    break;
                }
                // catch
            }
        } // while

        CloseOutputPort(DEFAULT_OUTPUT);

    } // run

} // MiddleFilter
