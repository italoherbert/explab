package italo.explab.arvore.cmd.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.instrucao.Instrucao;

public class LS extends Instrucao {

    @Override
    public Instrucao novo( ExecNo parent ) {
        LS ls = new LS();
        super.carrega( ls, parent ); 
        
        return ls;
    }

    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        
    }
    
}
