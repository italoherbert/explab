package italo.explab.arvore.exp.num.exec;

import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.num.node.NumRealValor;

public class NumRealValorExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        NumRealValor val = (NumRealValor)no;                                
        return new ExecResult( val.getValor() );
    }
    
}
