package italo.explab.analisador.sintatico;

import italo.explab.msg.CodigoErro;
import italo.explab.AnaliseOuInterResult;

public class AnaliseResult implements AnaliseOuInterResult {
    
    private final CodigoErro erro;
    private final int j;

    public AnaliseResult() {
        this.erro = null;
        this.j = 0;
    }
    
    public AnaliseResult( int j ) {
        this.erro = null;
        this.j = j;
    }
    
    public AnaliseResult( CodigoErro erro ) {
        this.erro = erro;
        this.j = 0;
    }

    @Override
    public CodigoErro getErro() {
        return erro;
    }

    @Override
    public int getJ() {
        return j;
    }
    
}
