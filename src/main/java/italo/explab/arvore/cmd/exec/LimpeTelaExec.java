package italo.explab.arvore.cmd.exec;

import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;

public class LimpeTelaExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {                
        executor.getAplic().getExpLabAplic().limpaTela();
        
        return new ExecResult();
    }
    
}
