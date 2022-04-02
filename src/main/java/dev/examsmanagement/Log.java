package dev.examsmanagement;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {
    static final boolean append = true;

//    Logger LOGGER;
    static FileHandler handler;

    {
        try {
            handler = new FileHandler("logs.log", append);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
//    static Logger LOGGER = Logger.getLogger(Log.class.getName());

    public static void info(String _message) {
        LOGGER.addHandler(handler);
        LOGGER.log(Level.INFO, _message);
    }
    public static void warning(String _message) {
        LOGGER.addHandler(handler);
        LOGGER.log(Level.WARNING, _message);
    }
    public static void fine(String _message) {
        LOGGER.addHandler(handler);
        LOGGER.log(Level.FINE, _message);
    }
}
