package italo.explab.var;

public class VarNaoEncontradaException extends Exception {

    public VarNaoEncontradaException() {
    }

    public VarNaoEncontradaException(String message) {
        super(message);
    }

    public VarNaoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }

    public VarNaoEncontradaException(Throwable cause) {
        super(cause);
    }

    public VarNaoEncontradaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
