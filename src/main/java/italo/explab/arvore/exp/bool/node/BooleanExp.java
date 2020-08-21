package italo.explab.arvore.exp.bool.node;

import italo.explab.arvore.AbstractExecNo;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;

public abstract class BooleanExp extends Exp {

    private boolean not = false;

    @Override
    protected void carrega(AbstractExecNo novo, ExecNo parent ) {
        super.carrega( novo, parent );
                
        ((BooleanExp)novo).setNot( not ); 
    }
    
    public boolean isNot() {
        return not;
    }

    public void setNot(boolean not) {
        this.not = not;
    }
    
}
