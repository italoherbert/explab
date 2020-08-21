package italo.explab;

public class InterException extends Exception {

    public InterException() {
    }

    public InterException(String message) {
        super(message);
    }

    public InterException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterException(Throwable cause) {
        super(cause);
    }

    public InterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
