package brickstream.exception;


/**
 * This exception is thrown when the SmartCameraHttpProcessor receives null data
 * over the socket.
 */
public class SmartCameraInternalException extends Exception {
    /**
     *   Default public contructor 
     */
    public SmartCameraInternalException() {
    		super();
    }

    /**
     *   Public contructor that takes an error message 
     */
    public SmartCameraInternalException(String msg) {
        super(msg);
    }
}
