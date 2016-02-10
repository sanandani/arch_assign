
import java.io.*;

public class MultiPortFilterFramework extends Thread {
    // Define filter input and output ports

    private PipedInputStream[] InputReadPorts;
    private PipedOutputStream[] OutputWritePorts;
    private MultiPortFilterFramework[] InputFilters;
    private int noOfInput = 1;
    private int noOfOutput = 1;
    static final int DEFAULT_INPUT = 0; // defaul input port is port 0;
    static final int DEFAULT_OUTPUT = 0;// defaul input port is port 0;

    // The following reference to a filter is used because java pipes are able to reliably
    // detect broken pipes on the input port of the filter. This variable will point to
    // the previous filter in the network and when it dies, we know that it has closed its
    // output pipe and will send no more data.
    public MultiPortFilterFramework(int noInput, int noOutput) {
        if (noInput < 1 || noOutput < 1) {
            throw new IllegalArgumentException("MultiPortFilterFramework: at least 1 input and 1 output is required");
        }
        noOfInput = noInput;
        noOfOutput = noOutput;
        InputReadPorts = new PipedInputStream[noOfInput];
        InputFilters = new MultiPortFilterFramework[noOfInput];
        OutputWritePorts = new PipedOutputStream[noOfOutput];
        for (int i = 0; i < noOfInput; i++) {
            InputReadPorts[i] = new PipedInputStream();
        }
        for (int i = 0; i < noOfOutput; i++) {
            OutputWritePorts[i] = new PipedOutputStream();
        }
    }

    /**
     * *************************************************************************
     * InnerClass:: EndOfStreamExeception Purpose: This
     *
     *
     *
     * Arguments: none
     *
     * Returns: none
     *
     * Exceptions: none
     *
     ***************************************************************************
     */
    class EndOfStreamException extends Exception {

        static final long serialVersionUID = 0; // the version for streaming

        EndOfStreamException() {
            super();
        }

        EndOfStreamException(String s) {
            super(s);
        }

    } // class

    /**
     * *************************************************************************
     * CONCRETE METHOD:: Connect Purpose: This method connects filters to each
     * other. All connections are through the inputport of each filter. That is
     * each filter's inputport is connected to another filter's output port
     * through this method.
     *
     * Arguments: 
     * UpstreamFilter - this is the filter that this filter will connect to. 
     * outputFromUPper - this is the port number of the output port of the upstreamFilter
     * inputPortNum- this si the port number of the input port of this filter
     * 
     * Returns: void
     *
     * Exceptions: IOException
     *
     ***************************************************************************
     */
    void Connect(MultiPortFilterFramework UpstreamFilter, int outputFromUpper, int inputPortNum) {
        try {
            // Connect this filter's input to the upstream pipe's output stream

            InputReadPorts[inputPortNum].connect(UpstreamFilter.OutputWritePorts[outputFromUpper]);
            InputFilters[inputPortNum] = UpstreamFilter;

        } // try
        catch (Exception Error) {
            System.out.println(Error.toString());
            System.out.println("\n" + this.getName() + " MultiPortFilterFramework error connecting::" + Error);

        } // catch

    } // Connect
     void Connect(MultiPortFilterFramework upstreamFilter) {
        Connect(upstreamFilter,DEFAULT_INPUT,DEFAULT_OUTPUT);

    } // Connect

   
    
    byte ReadFilterInputPort(int inputPortNum) throws EndOfStreamException {
        byte datum = 0;

        /**
         * *********************************************************************
         * Since delays are possible on upstream filters, we first wait until
         * there is data available on the input port. We check,... if no data is
         * available on the input port we wait for a quarter of a second and
         * check again. Note there is no timeout enforced here at all and if
         * upstream filters are deadlocked, then this can result in infinite
         * waits in this loop. It is necessary to check to see if we are at the
         * end of stream in the wait loop because it is possible that the
         * upstream filter completes while we are waiting. If this happens and
         * we do not check for the end of stream, then we could wait forever on
         * an upstream pipe that is long gone. Unfortunately Java pipes do not
         * throw exceptions when the input pipe is broken. So what we do here is
         * to see if the upstream filter is alive. if it is, we assume the pipe
         * is still open and sending data. If the filter is not alive, then we
         * assume the end of stream has been reached.
         * *********************************************************************
         */
        PipedInputStream input = InputReadPorts[inputPortNum];

        try {
            while (input.available() == 0) {
                if (EndOfInputStream(inputPortNum)) {
                    throw new EndOfStreamException("End of input stream reached");

                } //if

                sleep(250);

            } // while

        } // try
        catch (EndOfStreamException Error) {
            throw Error;

        } // catch
        catch (Exception Error) {
            System.out.println("\n" + this.getName() + " Error in read port wait loop::" + Error);

        } // catch

        /**
         * *********************************************************************
         * If at least one byte of data is available on the input pipe we can
         * read it. We read and write one byte to and from ports.
         * *********************************************************************
         */
        try {
            datum = (byte) input.read();
            return datum;

        } // try
        catch (Exception Error) {
            System.out.println("\n" + this.getName() + " Pipe read error::" + Error);
            return datum;

        } // catch

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
    void WriteFilterOutputPort(int outputPortNum, byte datum) {
        PipedOutputStream out = OutputWritePorts[outputPortNum];
        try {
            out.write((int) datum);
            out.flush();

        } // try
        catch (Exception Error) {
            System.out.println("\n" + this.getName() + " Pipe write error::" + Error);

        } // catch

        return;

    } // WriteFilterPort

    /**
     * *************************************************************************
     * CONCRETE METHOD:: EndOfInputStream Purpose: This method is used within
     * this framework which is why it is private It returns a true when there is
     * no more data to read on the input port of the instance filter. What it
     * really does is to check if the upstream filter is still alive. This is
     * done because Java does not reliably handle broken input pipes and will
     * often continue to read (junk) from a broken input pipe.
     *
     * Arguments: void
     *
     * Returns: A value of true if the previous filter has stopped sending data,
     * false if it is still alive and sending data.
     *
     * Exceptions: none
     *
     ***************************************************************************
     */
    private boolean EndOfInputStream(int inputPortNum) {
        if (InputFilters[inputPortNum].isAlive()) {
            return false;

        } else {

            return true;

        } // if

    } // EndOfInputStream

    
    void CloseOutputPort(int no) {
        try {

            OutputWritePorts[no].close();

        } catch (Exception Error) {
            System.out.println("\n" + this.getName() + " CloseOutputPort error::" + Error);

        } // catch

    } 

    void CloseInputPort(int no) {
        try {
            InputReadPorts[no].close();

        } catch (Exception Error) {
            System.out.println("\n" + this.getName() + " CloseInputPort error::" + Error);

        } // catch

    }
    
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
        for (int i = 0; i < noOfInput; i++) {
            CloseInputPort(i);
        }
        for (int i = 0; i < noOfOutput; i++) {
            CloseOutputPort(i);
        }

    } // Close all the ports

    /**
     * *************************************************************************
     * CONCRETE METHOD:: run Purpose: This is actually an abstract method
     * defined by Thread. It is called when the thread is started by calling the
     * Thread.start() method. In this case, the run() method should be
     * overridden by the filter programmer using this framework superclass
     *
     * Arguments: void
     *
     * Returns: void
     *
     * Exceptions: IOExecption
     *
     ***************************************************************************
     */
    public void run() {
        // The run method should be overridden by the subordinate class. Please
        // see the example applications provided for more details.

    } // run

} // FilterFramework class
