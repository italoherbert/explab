package italo.explab.arvore.exp.string.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.instrucao.Instrucao;

public class StrExp extends StringExp {
    
    private Exp exp1;
    private Exp exp2;

    @Override
    public Instrucao novo( ExecNo parent ) {
        StrExp strexp = new StrExp();
        super.carrega( strexp, parent );
        
        strexp.setExp1( (Exp)exp1.novo( strexp ) );
        strexp.setExp2( (Exp)exp2.novo( strexp ) ); 
        
        return strexp;
    }
    
    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        exp1.setBaseParamsParente( parent );
        exp2.setBaseParamsParente( parent ); 
    }
    
    public Exp getExp1() {
        return exp1;
    }

    public void setExp1(Exp exp1) {
        this.exp1 = exp1;
    }

    public Exp getExp2() {
        return exp2;
    }

    public void setExp2(Exp exp2) {
        this.exp2 = exp2;
    }
 
}
