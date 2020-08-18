package italo.explab.recursos.var;

public class ConstanteException extends Exception {

    private final String constnome;
    
    public ConstanteException( String constnome ) {
        this.constnome = constnome;
    }

    public String getConstNome() {
        return constnome;
    }
    
}
