// /******************************************************************************************************************
// * File:Utility.java
// * Course: 17655
// * Project: Assignment 1
// * Copyright: Copyright (c) 2003 Carnegie Mellon University
// * Versions:
// *	1.0 November 2008 - Sample Pipe and Filter code (ajl).
// *
// * Description:
// *
// * This class is a utility that helps in reading 
// *
// *	1) It parses the input stream and "decommutates" the measurement ID
// *	2) It parses the input steam for measurments and "decommutates" measurements, storing the bits in a long word.
// *
// * This filter illustrates how to convert the byte stream data from the upstream filterinto useable data found in
// * the stream: namely time (long type) and measurements (double type).
// *
// *
// * Parameters: 	None
// *
// * Internal Methods: None
// *
// ******************************************************************************************************************/
// import java.util.*;						// This class is used to interpret time words
// import java.text.SimpleDateFormat;		// This class is used to format and write time in a string format.

// public class Utility 
// {
// 	final int IdLength = 4;				// This is the length of IDs in the byte stream 
// 	final int MeasurementLength = 8;	// This is the length of all measurements (including time) in bytes

// 	public int readId()
//     {
// 		byte databyte = 0;				// This is the data byte read from the stream

// 		int id = 0;						// This is the measurement id

// 		for (int i=0; i<IdLength; i++ )
// 		{
// 			databyte = ReadFilterInputPort();	// This is where we read the byte from the stream...

// 			id = id | (databyte & 0xFF);		// We append the byte on to ID...

// 			if (i != IdLength-1)				// If this is not the last byte, then slide the
// 			{									// previously appended byte to the left by one byte
// 				id = id << 8;					// to make room for the next byte we append to the ID

// 			}
// 		}
// 		return id;
// 	}// readId

// 	public long readMeasurement()
//     {
// 		byte databyte = 0;				// This is the data byte read from the stream

// 		long measurement = 0;						// This is the measurement measurement

// 		for (int i=0; i<MeasurementLength; i++ )
// 		{
// 			databyte = ReadFilterInputPort();	// This is where we read the byte from the stream...

// 			measurement = measurement | (databyte & 0xFF);		// We append the byte on to ID...

// 			if (i != MeasurementLength-1)				// If this is not the last byte, then slide the
// 			{									// previously appended byte to the left by one byte
// 				measurement = measurement << 8;					// to make room for the next byte we append to the ID

// 			}
// 		}
		
// 		return measurement;
// 	}// readMeasurement

// } // SingFilter


// // public void write(byte[] b, int off, int len)
// // Parameters
// // b -- the data.

// // off -- the start offset in the data.

// // len -- the number of bytes to write.
// //byte[] b = {'h', 'e', 'l', 'l', 'o'};
// //out.write(b, 0, 3);