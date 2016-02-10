
/**
 * ****************************************************************************************************************
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
 *****************************************************************************************************************
 */
public class Plumber {

    public static void main(String argv[]) {
        /**
         * **************************************************************************
         * Here we instantiate three filters.
		***************************************************************************
         */

        SourceFilter sourceA = new SourceFilter(argv[0]);
        SourceFilter sourceB = new SourceFilter(argv[1]);
        SortByTimeFilter sort = new SortByTimeFilter();

        BinaryMergerFilter merger = new BinaryMergerFilter();
        BinarySplitterFilter splitter1 = new BinarySplitterFilter();
        BinarySplitterFilter splitter2 = new BinarySplitterFilter();

        LessThan10KFilter removeLessThan10k = new LessThan10KFilter();

        IdentifyPressureWildPointsFilter identifyWildPoint = new IdentifyPressureWildPointsFilter();

        RejectedPressureWildPointsSink sink1 = new RejectedPressureWildPointsSink();
        ExtrapolatePressureWildPointsFilter extrapolate = new ExtrapolatePressureWildPointsFilter();
        ExtrapolatedPressureSink sink2 = new ExtrapolatedPressureSink();
        GeneralSinkFilter sink3 = new GeneralSinkFilter();
        GeneralSinkFilter sink4 = new GeneralSinkFilter();

        /**
         * **************************************************************************
         * Here we connect the filters starting with the sink filter (Filter 1)
         * which we connect to Filter2 the middle filter. Then we connect
         * Filter2 to the source filter (Filter3).
		***************************************************************************
         */
        /*sink2.Connect(extrapolate);
        extrapolate.Connect(splitter2, 1, 0);
        sink1.Connect(splitter2, 0, 0);
        splitter2.Connect(identifyWildPoint);
        identifyWildPoint.Connect(removeLessThan10k, 0, 0);
        removeLessThan10k.Connect(splitter1, 0, 0);
        sink3.Connect(removeLessThan10k,1,0);//write to LessThan10K.dat
        sink4.Connect(splitter1,1,0);//write to outputC.dat
        splitter1.Connect(sort);*/
        sink1.Connect(sort);
        sort.Connect(merger);
        merger.Connect(sourceA, 0, 0);
        merger.Connect(sourceB, 0, 1); // This esstially says, "connect Filter3 input port to Filter2 output port
        //Filter2.Connect(Filter1); // This esstially says, "connect Filter3 input port to Filter2 output port

        /**
         * **************************************************************************
         * Here we start the filters up. All-in-all,... its really kind of
         * boring.
		***************************************************************************
         */
        sourceA.start();
        sourceB.start();
        merger.start();
        sort.start();
        /*splitter1.start();
        removeLessThan10k.start();
        identifyWildPoint.start();
        splitter2.start();
        extrapolate.start();
        sink1.start();
        sink2.start();
        sink3.start();
        sink4.start();*/
        sink1.start();

    } // main

} // Plumber
