package italo.explab.arvore.exp.atrib;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.instrucao.Instrucao;

public class Atrib extends Exp {
    
    public final static int IGUAL = 1;
    public final static int MAIS_IGUAL = 2;
    public final static int MENOS_IGUAL = 3;
    public final static int MULT_IGUAL = 4;
    public final static int DIV_IGUAL = 5;
    public final static int MOD_IGUAL = 6;
    public final static int POT_IGUAL = 7;
    public final static int ADD_AO_FIM = 8;
    
    private Exp varExp;
    private Exp valorExp;
    private int operador;
    
    @Override
    public Instrucao novo( ExecNo parent ) {
        Atrib atrib = new Atrib();
        super.carrega( atrib, parent );
        
        atrib.setVarExp( (Exp)varExp.novo( atrib ) );
        atrib.setValorExp( (Exp)valorExp.novo( atrib ) );
        atrib.setOperador( operador );
                
        return atrib;
    }

    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        varExp.setBaseParamsParente( parent );
        valorExp.setBaseParamsParente( parent ); 
    }
    
    public Exp getVarExp() {
        return varExp;
    }

    public void setVarExp(Exp varExp) {
        this.varExp = varExp;
    }

    public Exp getValorExp() {
        return valorExp;
    }

    public void setValorExp(Exp valorExp) {        
        this.valorExp = valorExp;
    }

    public int getOperador() {
        return operador;
    }

    public void setOperador(int operador) {
        this.operador = operador;
    }
            
}
