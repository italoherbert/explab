package italo.explab.arvore.exp.bool.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.instrucao.Instrucao;

public class BoolNaoExpValor extends BooleanExp {
    
    private Exp exp;

    @Override
    public Instrucao novo( ExecNo parent ) {
        BoolNaoExpValor boolNEValor = new BoolNaoExpValor();
        super.carrega( boolNEValor, parent );
        
        boolNEValor.setExp( (Exp)exp.novo( boolNEValor ) );
        
        return boolNEValor;
    }

    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        exp.setBaseParamsParente( parent );
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

}
