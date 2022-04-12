package math;

/**
 * Exception thrown when a dimension mismatch occurs between vectors
 */
public class DimensionMismatchException extends RuntimeException {
    public DimensionMismatchException(String message) {
        super(message);
    }
}
