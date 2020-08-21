package italo.explab.arvore.exp.valornull;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.instrucao.Instrucao;

public class NullValor extends Exp {

    @Override
    public Instrucao novo( ExecNo parent ) {
        NullValor nullValor = new NullValor();
        super.carrega( nullValor, parent );
        
        return nullValor;
    }
    
    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        
    }
    
}
