package italo.explab.arvore.exp.string.exec;

import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.string.node.StrValor;

public class StrValorExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        StrValor val = (StrValor)no;
        return new ExecResult( val.getValor() );
    }
    
}

