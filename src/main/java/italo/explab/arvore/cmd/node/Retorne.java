package italo.explab.arvore.cmd.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.instrucao.Instrucao;

public class Retorne extends Instrucao {
    
    private Exp retorno;

    @Override
    public Instrucao novo( ExecNo parent ) {
        Retorne retorne = new Retorne();
        super.carrega( retorne, parent );
        
        retorne.setRetorno( (Exp)retorno.novo( retorne ) ); 
        
        return retorne;
    }
    
    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        retorno.setBaseParamsParente( parent ); 
    }
    
    public Exp getRetorno() {
        return retorno;
    }

    public void setRetorno(Exp retorno) {
        this.retorno = retorno;
    }
    
}
