package italo.explab.arvore.cmd.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.instrucao.Instrucao;

public class Pare extends Instrucao {

    @Override
    public Instrucao novo( ExecNo parent ) {
        Pare pare = new Pare();
        super.carrega( pare, parent );
        
        return pare;
    }

    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        
    }
        
}
