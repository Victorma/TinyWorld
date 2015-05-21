package dasi.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This utility class is used to handle log operations.
 *
 * @author Gorka Suárez
 */
public final class LogUtil {
    //****************************************************************************************************
    // Fields:
    //****************************************************************************************************

    /**
     * This flag is used to enable or not the log operations.
     */
    public final static boolean debug = true;

    //****************************************************************************************************
    // Methods:
    //****************************************************************************************************

    /**
     * Logs a message with the current time in milliseconds.
     *
     * @param message The message to write in the log.
     */
    public static void logWithMs(String message) {
        if (debug) {
            System.out.println(System.currentTimeMillis() + " " + message);
        }
    }

    /**
     * Logs an exception with the java logger type.
     *
     * @param caller The caller object.
     * @param victim The exception to write in the log.
     */
    public static void logger(Object caller, Exception victim) {
        Logger.getLogger(caller.getClass().getName()).log(Level.SEVERE, null, victim);
    }

    /**
     * Logs an exception with the java logger type.
     *
     * @param <T> The caller type.
     * @param type The caller class object.
     * @param victim The exception to write in the log.
     */
    public static <T> void logger(Class<T> type, Exception victim) {
        Logger.getLogger(type.getName()).log(Level.SEVERE, null, victim);
    }
}
