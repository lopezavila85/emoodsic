/**
 * 
 */
package net.unir.emoodsic.common.classes;

import org.slf4j.Logger;

import net.unir.emoodsic.common.definitions.CommonDefs;

/**
 * @author Álvaro
 *
 */
public final class LogUtils {

	/**
     * Helper class for logging.
     */
    private LogUtils() {
        super();
    }
    
    /**
     * Logs as error a Data Access Exception.
     *
     * @param log           log from caller class.
     * @param method        caller method.
     * @param daeMessage    data access exception message.
     */
    public static void logDataAccessException(final Logger log, final String method, final String daeMessage) {
        logDataAccessException(log, method, daeMessage, null);
    }
    
    /**
     * Logs as error a Data Access Exception with an additional message.
     *
     * @param log               log from caller class.
     * @param method            caller method.
     * @param additionalMessage additional message to be logged.
     * @param daeMessage        data access exception message.
     */
    public static void logDataAccessException(final Logger log, final String method, final String daeMessage,
            final String additionalMessage) {
        
    	final StringBuilder stb = new StringBuilder();
        stb.append(String.format(CommonDefs.DATA_ACCESS_EX_MESSAGE, method, daeMessage));
        if (additionalMessage != null) {
            stb.append(additionalMessage);
        }
        log.error(stb.toString());
    }
    
    /**
     * Logs as error an EmoodsicException.
     *
     * @param log           log from caller class.
     * @param dex           a EmoodsicException which occurred previously.
     */
    public static void logEmoodsicException(final Logger log, final EmoodsicException dex) {
    	logEmoodsicException(log, dex, null);
    }

    /**
     * Logs as error a EmoodsicException.
     *
     * @param log               log from caller class.
     * @param dex               a EmoodsicException which occurred previously.
     * @param additionalMessage additional message to be logged.
     */
    public static void logEmoodsicException(final Logger log, final EmoodsicException dex,
            final String additionalMessage) {
        final StringBuilder stb = new StringBuilder();
        stb.append(dex.toString());
        if (additionalMessage != null) {
            stb.append(additionalMessage);
        }
        log.error(stb.toString());
    }
    
    
    public static void logException(final Logger log, final String message) {
        log.error(message);
    }
}
