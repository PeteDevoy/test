package brickstream.exception;


/**
 * This exception is thrown when the SmartCameraHttpProcessor receives null data
 * over the socket.
 */
public class SmartCameraNullDataException extends Exception {
    /**
     *   Default public contructor 
     */
    public SmartCameraNullDataException() {
    		super();
    }

    /**
     *   Public contructor that takes an error message 
     */
    public SmartCameraNullDataException(String msg) {
        super(msg);
    }
}
