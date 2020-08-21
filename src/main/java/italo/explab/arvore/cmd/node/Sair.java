package italo.explab.arvore.cmd.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.instrucao.Instrucao;

public class Sair extends Instrucao {

    @Override
    public Instrucao novo( ExecNo parent ) {
        Sair sair = new Sair();
        super.carrega( sair, parent );
        
        return sair;
    }

    @Override
    public void setBaseParamsParente(ExecNo parent) {
        
    }
    
}
