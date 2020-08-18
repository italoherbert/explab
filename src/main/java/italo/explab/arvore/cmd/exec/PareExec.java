package italo.explab.arvore.cmd.exec;

import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;

public class PareExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        ExecResult er = new ExecResult();
        er.setPareEncontrado( true );
        return er;
    }
    
}
