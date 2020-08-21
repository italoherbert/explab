package italo.explab.arvore.cmd.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.instrucao.Instrucao;

public class Exiba extends Instrucao {

    private Exp exp;
    private boolean pularLinha = true;

    @Override
    public Instrucao novo( ExecNo parent ) {
        Exiba exiba = new Exiba();
        super.carrega( exiba, parent );
        
        exiba.setPularLinha( pularLinha );
        exiba.setExp( (Exp)exp.novo( exiba ) );
        
        return exiba;
    }        

    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        exp.setBaseParamsParente( parent );
    }
    
    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    public boolean isPularLinha() {
        return pularLinha;
    }

    public void setPularLinha(boolean pularLinha) {
        this.pularLinha = pularLinha;
    }
    
}
