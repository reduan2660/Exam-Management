package dev.examsmanagement;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {
//    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    static final boolean append = false;


//    Logger LOGGER;
    FileHandler handler = new FileHandler("logs.log", append);
    Logger LOGGER = Logger.getLogger(Log.class.getName());

    public Log() throws IOException {
        System.setProperty(
                "java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        LOGGER.addHandler(handler);
    }

    public void info(String _message) { LOGGER.log(Level.INFO, _message); }
    public void warning(String _message) { LOGGER.log(Level.WARNING, _message); }
    public void fine(String _message) { LOGGER.log(Level.FINE, _message); }
}
