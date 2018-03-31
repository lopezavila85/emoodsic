/**
 * 
 */
package net.unir.emoodsic.common.definitions;

/**
 * @author Álvaro
 *
 */
public final class CommonDefs {

    /**
     * Authentication token length.
     */
    public static final int AUTHENTICATION_TOKEN_LENGTH = 16;
	
    /* LOGGING */

    /**
     * Data access exception specifying a method.
     *  **/
    public static final String DATA_ACCESS_EX = "Data access exception at %s";

    /**
     * Data access exception specifying a method and the full exception message.
     *  **/
    public static final String DATA_ACCESS_EX_MESSAGE = "Data access exception at %s\n%s";

    /**
     * Transaction result error specifying method.
     *  **/
    public static final String TRANSACTION_RESULT_ERROR = "Transaction result error at %s\n";
}
