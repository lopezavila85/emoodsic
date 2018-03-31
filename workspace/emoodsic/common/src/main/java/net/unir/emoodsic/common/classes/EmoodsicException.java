/**
 * 
 */
package net.unir.emoodsic.common.classes;

/**
 * @author Álvaro
 *
 */
public class EmoodsicException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7450258811012021528L;

    /**
     * Exception name (if any) which threw this DoDFWException.
    */
    private final transient Exception origin;
    
    /**
     * Default constructor.
     */
    public EmoodsicException() {
        super();
        this.origin = null;
    }

    /**
     * Constructor with message.
     * @param   message Exception message
     */
    public EmoodsicException(final String message) {
        super(message);
        this.origin = null;
    }

    /**
     * Constructor with message.
     * @param   message     Exception message
     * @param   exOrigin    Exception which originated this one
     */
    public EmoodsicException(final String message, final Exception exOrigin) {
        super(message);
        this.origin = exOrigin;
    }

    /**
     * Origin exception getter.
     * @return  DoDFWException�s origin exception
    */
    public Exception getOrigin() {
        return this.origin;
    }

    @Override
    public String toString() {
        if (this.origin == null) {
            return String.format("%s", this.getMessage());
        } else {
            return String.format("%s\n[ORIG: %s]\n", this.getMessage(), this.origin.getMessage());
        }
    }
}
