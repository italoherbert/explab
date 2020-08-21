package italo.explab.arvore.cmd.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.instrucao.Instrucao;

public class Lance extends Instrucao {
    
    private Exp exp;

    @Override
    public Instrucao novo( ExecNo parent ) {
        Lance lance = new Lance();
        super.carrega( lance, parent );
        
        lance.setExp( (Exp)exp.novo( lance ) ); 
        
        return lance;
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
    
}
