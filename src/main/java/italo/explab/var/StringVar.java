package italo.explab.var;

public class StringVar implements Var {

    private final String valor;
    
    public StringVar( String valor ) {
        this.valor = valor;
    }

    @Override
    public Var nova() {
        return new StringVar( valor );
    }

    @Override
    public boolean iguais(Var var) {
        if ( !(var instanceof StringVar ) )
            return false;
        return valor.equals( ((StringVar)var).getValor() );
    }
    
    public String getValor() {
        return valor;
    }
    
    @Override
    public int getTipo() {
        return STRING;
    }
                
    @Override
    public int getTipoCompativel() {
        return TC_STRING;
    }

    @Override
    public String getStringTipo() {
        return TIPO_STRING;
    }
    
}
