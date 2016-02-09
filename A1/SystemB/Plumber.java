/******************************************************************************************************************
* File:Plumber.java
* Course: 17655
* Project: Assignment 1
* Copyright: Copyright (c) 2003 Carnegie Mellon University
* Versions:
*	1.0 November 2008 - Sample Pipe and Filter code (ajl).
*
* Description:
*
* This class serves as an example to illstrate how to use the PlumberTemplate to create a main thread that
* instantiates and connects a set of filters. This example consists of three filters: a source, a middle filter
* that acts as a pass-through filter (it does nothing to the data), and a sink filter which illustrates all kinds
* of useful things that you can do with the input stream of data.
*
* Parameters: 		None
*
* Internal Methods:	None
*
******************************************************************************************************************/
public class Plumber
{
   public static void main( String argv[])
   {
		/****************************************************************************
		* Here we instantiate three filters.
		****************************************************************************/

		SourceFilter Filter1 = new SourceFilter();
//		MiddleFilter Filter2 = new MiddleFilter();
		IdentifyPressureWildPointsFilter Filter2 = new IdentifyPressureWildPointsFilter();
//		ExtrapolatePressureWildPointsFilter Filter3 = new ExtrapolatePressureWildPointsFilter();
		ExtrapolatedPressureSink Filter3 = new ExtrapolatedPressureSink();

		/****************************************************************************
		* Here we connect the filters starting with the sink filter (Filter 1) which
		* we connect to Filter2 the middle filter. Then we connect Filter2 to the
		* source filter (Filter3).
		****************************************************************************/
//		Filter4.Connect((MultiPortFilterFramework)Filter3,0); // This esstially says, "connect Filter3 input port to Filter2 output port
		Filter3.Connect((MultiPortFilterFramework)Filter2,0); // This esstially says, "connect Filter3 input port to Filter2 output port
		Filter2.Connect((MultiPortFilterFramework)Filter1,0); // This esstially says, "connect Filter3 input port to Filter2 output port

		/****************************************************************************
		* Here we start the filters up. All-in-all,... its really kind of boring.
		****************************************************************************/

		Filter1.start();
		Filter2.start();
		Filter3.start();
//		Filter4.start();

   } // main

} // Plumber