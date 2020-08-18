package italo.explab;

import italo.explab.var.Var;

public class MetodoParam {

    private final Var var;
    private final int pos;

    public MetodoParam( Var var ) {
        this( var, 0 );
    }
    
    public MetodoParam( Var var, int pos ) {
        this.var = var;
        this.pos = pos;
    }

    public Var getVar() {
        return var;
    }

    public int getPos() {
        return pos;
    }
    
}
