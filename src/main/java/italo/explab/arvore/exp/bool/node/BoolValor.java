package italo.explab.arvore.exp.bool.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.var.BooleanVar;

public class BoolValor extends BooleanExp {
    
    private BooleanVar valor;

    @Override
    public Instrucao novo( ExecNo parent ) {
        BoolValor bv = new BoolValor();
        super.carrega( bv, parent );
        
        bv.setValor( (BooleanVar)valor.nova() ); 
        
        return bv;
    }

    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        
    }
    
    public BooleanVar getValor() {
        return valor;
    }

    public void setValor( BooleanVar valor ) {
        this.valor = valor;
    }
    
}
