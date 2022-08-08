package hello;

/**
 * Created by Bohdan on 29/12/2015.
 */

import lombok.SneakyThrows;

import java.io.IOException;
import java.util.logging.*;

public class MyLogger {
    private static FileHandler fileTxt;
    private static SimpleFormatter formatterTxt;

    @SneakyThrows
    public static void setup() {

        // get the global logger to configure it
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        logger.setLevel(Level.INFO);
        fileTxt = new FileHandler("Logging.txt");

        // create a TXT formatter
        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);

    }
}



