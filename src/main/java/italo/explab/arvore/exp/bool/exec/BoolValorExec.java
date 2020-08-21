package italo.explab.arvore.exp.bool.exec;

import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.bool.node.BoolValor;
import italo.explab.var.BooleanVar;

public class BoolValorExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        BoolValor bv = (BoolValor)no;
        
        BooleanVar bvar = bv.getValor();
        if ( bv.isNot() )
            bvar = new BooleanVar( !bvar.getValor() );
            
        return new ExecResult( bvar );
    }
    
}
