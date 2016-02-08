
/**
 * ****************************************************************************************************************
 * File:FilterFramework.java
 * Course: 17655
 * Project: Assignment 1
 * Copyright: Copyright (c) 2003 Carnegie Mellon University
 * Versions:
 *	1.0 November 2008 - Initial rewrite of original assignment 1 (ajl).
 *
 * Description:
 *
 * This superclass defines a skeletal filter framework that defines a filter in terms of the input and output
 * ports. All filters must be defined in terms of this framework - that is, filters must extend this class
 * in order to be considered valid system filters. Filters as standalone threads until the inputport no longer
 * has any data - at which point the filter finishes up any work it has to do and then terminates.
 *
 * Parameters:
 *
 * InputReadPort:	This is the filter's input port. Essentially this port is connected to another filter's piped
 *					output steam. All filters connect to other filters by connecting their input ports to other
 *					filter's output ports. This is handled by the Connect() method.
 *
 * OutputWritePort:	This the filter's output port. Essentially the filter's job is to read data from the input port,
 *					perform some operation on the data, then write the transformed data on the output port.
 *
 * FilterFramework:  This is a reference to the filter that is connected to the instance filter's input port. This
 *					reference is to determine when the upstream filter has stopped sending data along the pipe.
 *
 * Internal Methods:
 *
 *	public void Connect( FilterFramework Filter )
 *	public byte ReadFilterInputPort()
 *	public void WriteFilterOutputPort(byte datum)
 *	public boolean EndOfInputStream()
 *
 *****************************************************************************************************************
 */

public class SinglePortFilterFramework extends MultiPortFilterFramework {

    public static final int NO_OF_INPUT = 1;
    public static final int NO_OF_OUTPUT = 1;
    public static final int DEFAULT_INPUT = 0;
    public static final int DEFAULT_OUTPUT = 0;

    public SinglePortFilterFramework() {
        super(NO_OF_INPUT, NO_OF_OUTPUT);
    }

    //using default input port 0 and output port 0
    void Connect(MultiPortFilterFramework Filter, int outputPort) {

        Connect(Filter, outputPort, DEFAULT_INPUT);

    } // Connect

    /**
     * *************************************************************************
     * CONCRETE METHOD:: ReadFilterInputPort Purpose: This method reads data
     * from the input port one byte at a time.
     *
     * Arguments: void
     *
     * Returns: byte of data read from the input port of the filter.
     *
     * Exceptions: IOExecption, EndOfStreamException (rethrown)
     *
     ***************************************************************************
     */
    byte ReadFilterInputPort() throws EndOfStreamException {

        return ReadFilterInputPort(DEFAULT_INPUT);

    } // ReadFilterPort

    /**
     * *************************************************************************
     * CONCRETE METHOD:: WriteFilterOutputPort Purpose: This method writes data
     * to the output port one byte at a time.
     *
     * Arguments: byte datum - This is the byte that will be written on the
     * output port.of the filter.
     *
     * Returns: void
     *
     * Exceptions: IOException
     *
     ***************************************************************************
     */
    void WriteFilterOutputPort(byte datum) {
        WriteFilterOutputPort(DEFAULT_OUTPUT, datum);
    } // WriteFilterPort

    /**
     * *************************************************************************
     * CONCRETE METHOD:: ClosePorts Purpose: This method is used to close the
     * input and output ports of the filter. It is important that filters close
     * their ports before the filter thread exits.
     *
     * Arguments: void
     *
     * Returns: void
     *
     * Exceptions: IOExecption
     *
     ***************************************************************************
     */
    void ClosePorts() {
        CloseInputPort(DEFAULT_INPUT);
        CloseOutputPort(DEFAULT_OUTPUT);
    } // ClosePorts

} // SinglePortFilterFramework class
