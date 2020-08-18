package italo.explab.arvore.estruturas.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.grupo.Grupo;
import italo.explab.arvore.instrucao.Instrucao;

public class Finalize extends Grupo {
    
    @Override
    public Instrucao novo( ExecNo parent ) {
        Finalize finalize = new Finalize();
        super.carrega( finalize, parent );
        
        return finalize;
    }

    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        
    }
    
}

