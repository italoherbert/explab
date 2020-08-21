package italo.explab.arvore.exp.bool.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.instrucao.Instrucao;

public class BoolComp extends BooleanExp {
    
    public final static int IGUAL = 1;
    public final static int DIFERENTE = 2;
    public final static int MAIOR = 3;
    public final static int MAIOR_OU_IGUAL = 4;
    public final static int MENOR = 5;
    public final static int MENOR_OU_IGUAL = 6;
    public final static int INSTANCIADE = 7;
    
    private Exp exp1;
    private Exp exp2;
    private boolean notExp1 = false;
    private boolean notExp2 = false;
    private int operador = IGUAL;

    @Override
    public Instrucao novo( ExecNo parent ) {
        BoolComp bc = new BoolComp();
        super.carrega( bc, parent );
        
        bc.setExp1( (Exp)exp1.novo( bc ) );
        bc.setExp2( (Exp)exp2.novo( bc ) );
        bc.setNotExp1( notExp1 );
        bc.setNotExp2( notExp2 );
        bc.setOperador( operador ); 
        
        return bc;
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
