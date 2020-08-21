package italo.explab.arvore.exp.num.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.atrib.Atrib;
import italo.explab.arvore.instrucao.Instrucao;

public class NumAtrib extends NumericaExp {

    private Atrib atrib;
    
    @Override
    public Instrucao novo( ExecNo parent ) {
        NumAtrib numAtrib = new NumAtrib();
        super.carrega(numAtrib, parent );
        
        numAtrib.setAtrib( (Atrib)atrib.novo( numAtrib ) );
        
        return numAtrib;
    }

    @Override
    public void setBaseParamsParente(ExecNo parent) {
        atrib.setBaseParamsParente( parent );
    }     

    public Atrib getAtrib() {
        return atrib;
    }

    public void setAtrib(Atrib atrib) {
        this.atrib = atrib;
    }
    
}
