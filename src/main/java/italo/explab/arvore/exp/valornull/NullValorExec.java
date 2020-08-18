package italo.explab.arvore.exp.valornull;

import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.var.NullVar;

public class NullValorExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        return new ExecResult( new NullVar() );
    }
    
}
