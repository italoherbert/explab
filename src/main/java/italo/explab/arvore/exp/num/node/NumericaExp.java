package italo.explab.arvore.exp.num.node;

import italo.explab.arvore.AbstractExecNo;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;

public abstract class NumericaExp extends Exp {
    
    private boolean transposta = false;

    @Override
    protected void carrega( AbstractExecNo novo, ExecNo parent ) {
        super.carrega( novo, parent );
        
        ((NumericaExp)novo).setTransposta( transposta ); 
    }
    
    public boolean isTransposta() {
        return transposta;
    }

    public void setTransposta(boolean transposta) {
        this.transposta = transposta;
    }
    
}
