package italo.explab.var;

public class BooleanVar implements Var {
    
    private boolean valor = true;
    
    public BooleanVar( boolean valor ) {
        this.valor = valor;
    }
    
    public boolean getValor() {
        return valor;
    }    
    
    @Override
    public Var nova() {
        return new BooleanVar( valor );
    }
    
    @Override
    public boolean iguais(Var var) {
        if ( !(var instanceof BooleanVar ) )
            return false;
        return valor == ((BooleanVar)var).getValor();
    }

    @Override
    public int getTipo() {
        return BOOLEAN;
    }
    
    @Override
    public int getTipoCompativel() {
        return TC_BOOLEAN;
    }

    @Override
    public String getStringTipo() {
        return TIPO_BOOLEAN;
    }
    
}
