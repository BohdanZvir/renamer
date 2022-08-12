package renamer;

import lombok.SneakyThrows;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {

    @SneakyThrows
    public static void setup() {
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        logger.setLevel(Level.INFO);
        FileHandler fileTxt = new FileHandler("Logging.txt");

        SimpleFormatter formatter = new SimpleFormatter();
        fileTxt.setFormatter(formatter);
        logger.addHandler(fileTxt);
    }
}
