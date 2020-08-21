package italo.explab.arvore.cmd.exec;

import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.cmd.node.Retorne;
import italo.explab.var.Var;

public class RetorneExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        Retorne retorne = (Retorne)no;
                        
        ExecResult er = executor.exec( retorne.getRetorno() );
        if ( er.isErroOuEx() )
            return er;                        
                
        Var retornada = er.getVar();
                
        ExecResult er2 = new ExecResult();
        er2.setRetornada( retornada );
        return er2;
    }
    
}
