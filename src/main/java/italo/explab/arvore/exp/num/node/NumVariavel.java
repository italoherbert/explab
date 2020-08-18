
package italo.explab.arvore.exp.num.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.variavel.VariavelExp;
import italo.explab.arvore.instrucao.Instrucao;

public class NumVariavel extends NumericaExp {
    
    private VariavelExp variavelExp;

    @Override
    public Instrucao novo( ExecNo parent ) {
        NumVariavel nvar = new NumVariavel();
        super.carrega( nvar, parent );
        
        nvar.setVariavelExp( (VariavelExp)variavelExp.novo( nvar ) ); 
        
        return nvar;
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
