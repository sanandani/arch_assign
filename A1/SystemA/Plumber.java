/******************************************************************************************************************
* File:PrintContent.java
* Course: 17655
* Project: Assignment 1
* Copyright: Copyright (c) 2003 Carnegie Mellon University
* Versions:
*	1.0 November 2008 - Sample Pipe and Filter code.
*
* Description:
*
* This class serves as an example to illustrate how to use the PlumberTemplate to create a main thread that
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
        RemoveAttributesFilter Filter2 = new RemoveAttributesFilter();
        ConvertTemperatureFilter Filter3 = new ConvertTemperatureFilter();
        ConvertAltitudeFilter Filter4 = new ConvertAltitudeFilter();
		SinkFilter Filter5 = new SinkFilter();

		/****************************************************************************
		* Here we connect the filters starting with the source filter (Filter 1) which
		* we connect to Filter2 the RemoveAttributes. Then we connect Filter2 to 
		* ConvertTemperature. Then we connect Filter3 to ConvertAltitude. Then we 
		* connect Filter4 to sink filter (Filter5).
		****************************************************************************/

        Filter5.Connect(Filter4); // This esstially says, "connect Filter5 input port to Filter4 output port
        Filter4.Connect(Filter3); // This esstially says, "connect Filter4 input port to Filter3 output port
		Filter3.Connect(Filter2); // This esstially says, "connect Filter3 input port to Filter2 output port
		Filter2.Connect(Filter1); // This esstially says, "connect Filter2 input port to Filter1 output port

		/****************************************************************************
		* Here we start the filters up. All-in-all,... its really kind of boring.
		****************************************************************************/

		Filter1.start();
		Filter2.start();
		Filter3.start();
        Filter4.start();
        Filter5.start();

   } // main

} // Plumber