
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Logger {

    public static void log(String s) {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("log.txt", true)));
            out.println(s);
            out.close();
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }
}
