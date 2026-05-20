package exceptions;

/**
 * Excepție personalizată pentru operații legate de înscrieri
 * Demonstrează tratarea erorilor specifice domeniului
 */
public class InscreException extends Exception {
    private static final long serialVersionUID = 1L;

    public InscreException(String message) {
        super(message);
    }

    public InscreException(String message, Throwable cause) {
        super(message, cause);
    }
}
