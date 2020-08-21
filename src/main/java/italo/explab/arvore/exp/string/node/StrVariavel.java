package italo.explab.arvore.exp.string.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.variavel.VariavelExp;
import italo.explab.arvore.instrucao.Instrucao;

public class StrVariavel extends StringExp {
    
    private VariavelExp variavelExp;

    @Override
    public Instrucao novo( ExecNo parent ) {
        StrVariavel svar = new StrVariavel();
        super.carrega( svar, parent );
        
        svar.setVariavelExp( (VariavelExp)variavelExp.novo( svar ) );
        
        return svar;
    }
    
    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        variavelExp.setBaseParamsParente( parent ); 
    }
    
    public VariavelExp getVariavelExp() {
        return variavelExp;
    }

    public void setVariavelExp(VariavelExp variavelExp) {
        this.variavelExp = variavelExp;
    }

}

