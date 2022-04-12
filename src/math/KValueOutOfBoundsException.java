package math;

/**
 * Exception thrown when k is outside the bounds of the matrix it's assiciated
 * with
 */
public class KValueOutOfBoundsException extends RuntimeException {
    KValueOutOfBoundsException(String message) {
        super(message);
    }
}
