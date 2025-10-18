package core.basesyntax.exceptions;

public class WorkWithFileException extends RuntimeException {
    public WorkWithFileException(String message) {
        super(message);
    }

    public WorkWithFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public WorkWithFileException(Throwable cause) {
        super(cause);
    }
}
