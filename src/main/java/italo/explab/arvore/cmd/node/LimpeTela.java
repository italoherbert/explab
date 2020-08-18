package italo.explab.arvore.cmd.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.instrucao.Instrucao;

public class LimpeTela extends Instrucao {

    @Override
    public Instrucao novo( ExecNo parent ) {
        LimpeTela lt = new LimpeTela();
        super.carrega( lt, parent );                
        
        return lt;
    }

    @Override
    public void setBaseParamsParente(ExecNo parent) {
        
    }
    
}
