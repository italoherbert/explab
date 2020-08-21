package italo.explab.arvore.exp.string.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.var.StringVar;

public class StrValor extends StringExp {
    
    private StringVar valor;

    @Override
    public Instrucao novo( ExecNo parent ) {
        StrValor svalor = new StrValor();
        super.carrega( svalor, parent );
        
        svalor.setValor( (StringVar)valor.nova() ); 
        
        return svalor;
    }
    
    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        
    }
    
    public StringVar getValor() {
        return valor;
    }

    public void setValor( StringVar valor ) {
        this.valor = valor;
    }
    
}