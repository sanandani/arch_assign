

public class BinarySplitterFilter extends MultiPortFilterFramework {

    public static final int NO_OF_INPUT = 1;
    public static final int NO_OF_OUTPUT = 2;
    public static final int OUTPUT0 = 0;
    public static final int OUTPUT1 = 1;

    public BinarySplitterFilter() {
        super(NO_OF_INPUT, NO_OF_OUTPUT);
    }

    void Connect(MultiPortFilterFramework UpstreamFilter, int outputPort) {
        Connect(UpstreamFilter, outputPort, DEFAULT_INPUT);
    }// Connect

    @Override
    public void run() {

        int bytesread = 0;					// Number of bytes read from the input file.
        int byteswritten = 0;				// Number of bytes written to the stream.
        byte databyte = 0;					// The byte of data read from the file

        // Next we write a message to the terminal to let the world know we are alive...
        System.out.print("\n" + this.getName() + "::BinarySplitter Reading ");

        /**
         * ***********************************************************
         * Here we read a byte and write a byte
         * ***********************************************************
         */
        while (true) {
            try {
                databyte = ReadFilterInputPort(DEFAULT_INPUT);
                bytesread++;
                for (int i = 0; i < NO_OF_OUTPUT; i++) {
                    WriteFilterOutputPort(i, databyte);
                }
                byteswritten++;

            } // try
            catch (EndOfStreamException e) {
                CloseInputPort(DEFAULT_INPUT);
                System.out.print("\n" + this.getName() + "::BinarySplitter Exising ; bytes read: " + bytesread + " bytes written: " + byteswritten);
                break;

            } // catch
        } // while
       for (int i = 0; i < NO_OF_OUTPUT; i++) {
                CloseOutputPort(i);
            }
    } // run

} // MiddleFilter
