package italo.explab.arvore.exp.matriz.indice;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;

public class ExpMatrizIndice {
    
    public final static int NORMAL = 1;
    public final static int VETOR = 2;
    
    private Exp exp = null;
    private int pos = 0;
    private int tipo = NORMAL;

    public ExpMatrizIndice( int pos, int tipo ) {
        this.pos = pos;
        this.tipo = tipo;
    }
    
    public ExpMatrizIndice( Exp exp, int pos, int tipo ) {
        this.exp = exp;
        this.pos = pos;
        this.tipo = tipo;
    }

    public ExpMatrizIndice novo( ExecNo parent ) {
        ExpMatrizIndice emi = new ExpMatrizIndice( pos, tipo );
        if ( exp != null )
            emi.setExp( (Exp)exp.novo( parent ) );
        
        return emi;
    }
    
    public Exp getExp() {
        return exp;
    }

    public int getPos() {
        return pos;
    }
    
    public int getTipo() {
        return tipo;
    }        

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
}
