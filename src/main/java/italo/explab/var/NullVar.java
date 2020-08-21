package italo.explab.var;

public class NullVar extends ObjetoVar {
    
    public NullVar() {
        super( null );
    }

    @Override
    public Var nova() {
        return new NullVar();
    }
    
    public Object getValor() {
        return null;
    }
    
}
