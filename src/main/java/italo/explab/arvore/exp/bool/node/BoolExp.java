package italo.explab.arvore.exp.bool.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.instrucao.Instrucao;

public class BoolExp extends BooleanExp {
    
    public final static int AND = 1;
    public final static int OR = 2;
    public final static int XOR = 3;
    public final static int NOT = 4;
    public final static int VALOR_UNICO = 0;
    
    private Exp exp1;
    private Exp exp2;
    private int operador = VALOR_UNICO;
    private boolean notExp1 = false;
    private boolean notExp2 = false;

    @Override
    public Instrucao novo( ExecNo parent ) {
        BoolExp exp = new BoolExp();
        super.carrega( exp, parent );
        
        exp.setExp1( (Exp)exp1.novo( exp ) );
        exp.setExp2( (Exp)exp2.novo( exp ) );
        exp.setNotExp1( notExp1 );
        exp.setNotExp2( notExp2 );
        exp.setOperador( operador ); 
        
        return exp;
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

    public int getOperador() {
        return operador;
    }

    public void setOperador(int operador) {
        this.operador = operador;
    }

    public boolean isNotExp1() {
        return notExp1;
    }

    public void setNotExp1(boolean notExp1) {
        this.notExp1 = notExp1;
    }

    public boolean isNotExp2() {
        return notExp2;
    }

    public void setNotExp2(boolean notExp2) {
        this.notExp2 = notExp2;
    }
            
}
