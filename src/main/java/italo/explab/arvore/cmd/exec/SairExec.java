package italo.explab.arvore.cmd.exec;

import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.inter.cmd.SairExecListener;

public class SairExec implements Exec {

    private SairExecListener listener;
    
    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        if ( listener != null )
            listener.sair();
        
        return new ExecResult();
    }

    public void setSairExecListener(SairExecListener listener) {
        this.listener = listener;
    }
    
}
