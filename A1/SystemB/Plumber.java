/******************************************************************************************************************
* File:Plumber.java
* Course: 17655
* Project: Assignment 1
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

		SourceFilter Filter1 = new SourceFilter(argv[0]);
		RemoveAttributesFilter RemoveFilter = new RemoveAttributesFilter();
		ConvertAltitudeFilter Filter2 = new ConvertAltitudeFilter();
		ConvertTemperatureFilter Filter3 = new ConvertTemperatureFilter();
		IdentifyPressureWildPointsFilter Filter4 = new IdentifyPressureWildPointsFilter();
		BinarySplitterFilter Filter5 = new BinarySplitterFilter();
		RejectedPressureWildPointsSink Filter6 = new RejectedPressureWildPointsSink();
		ExtrapolatePressureWildPointsFilter Filter7 = new ExtrapolatePressureWildPointsFilter();
		ExtrapolatedPressureSink Filter8 = new ExtrapolatedPressureSink();

		/****************************************************************************
		* Here we connect the filters starting with the sink filter (Filter 1) which
		* we connect to Filter2 the middle filter. Then we connect Filter2 to the
		* source filter (Filter3).
		****************************************************************************/
		Filter8.Connect(Filter7);
		Filter7.Connect(Filter5,0,0);
		Filter6.Connect(Filter5,1,0);
		Filter5.Connect(Filter4);
		Filter4.Connect(Filter3);
		Filter3.Connect(Filter2); // This esstially says, "connect Filter3 input port to Filter2 output port
		Filter2.Connect(RemoveFilter); // This esstially says, "connect Filter3 input port to Filter2 output port
		RemoveFilter.Connect(Filter1); // This esstially says, "connect Filter3 input port to Filter2 output port


		/****************************************************************************
		* Here we start the filters up. All-in-all,... its really kind of boring.
		****************************************************************************/

		Filter1.start();
		RemoveFilter.start();
		Filter2.start();
		Filter3.start();
		Filter4.start();
		Filter5.start();
		Filter6.start();
		Filter7.start();
		Filter8.start();

   } // main

} // Plumber