package italo.explab.arvore.cmd.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.instrucao.Instrucao;

public class Continue extends Instrucao {

    @Override
    public Instrucao novo( ExecNo parent ) {
        Continue c = new Continue();
        super.carrega( c, parent );
        
        return c;
    }

    @Override
    public void setBaseParamsParente(ExecNo parent) {
        
    }
    
}
