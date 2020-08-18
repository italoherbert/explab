package italo.explab.matriz;

import italo.explab.msg.CodigoErro;
import italo.explab.var.Var;

public class MatrizELResult {
    
    private Var el;
    private CodigoErro erro;

    public MatrizELResult( Var el ) {
        this.el = el;
    }
    
    public MatrizELResult( CodigoErro erro ) {
        this.erro = erro;
    }

    public Var getEL() {
        return el;
    }

    public CodigoErro getErro() {
        return erro;
    }
    
}
