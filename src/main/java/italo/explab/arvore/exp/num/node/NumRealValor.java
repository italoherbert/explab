package italo.explab.arvore.exp.num.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.var.num.NumericaVar;

public class NumRealValor extends NumericaExp {
    
    private NumericaVar valor;

    @Override
    public Instrucao novo( ExecNo parent ) {
        NumRealValor nrvalor = new NumRealValor();
        super.carrega( nrvalor, parent );
        
        nrvalor.setValor( (NumericaVar)valor.nova() ); 
        
        return nrvalor;
    }
    
    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        
    }
    
    public NumericaVar getValor() {
        return valor;
    }

    public void setValor(NumericaVar valor) {
        this.valor = valor;
    }        
    
}
