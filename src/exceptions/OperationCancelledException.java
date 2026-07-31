package exceptions;

/**
 * ============================================================
 * OperationCancelledException.java
 * ============================================================
 * Indicates that the current operation was cancelled
   by the user.
 * This exception is used internally to exit a multistep operation
   gracefully.
 * ============================================================
 */
public class OperationCancelledException extends RuntimeException {

    public OperationCancelledException() {
        super();
    }
}