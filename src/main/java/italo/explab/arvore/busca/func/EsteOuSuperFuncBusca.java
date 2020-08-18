package italo.explab.arvore.busca.func;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.Executor;

public class EsteOuSuperFuncBusca implements FuncBusca {
  
    private final EsteFuncBusca esteBusca = new EsteFuncBusca();
    private final SuperFuncBusca superBusca = new SuperFuncBusca();

    @Override
    public FuncBuscaResult busca( ExecNo no, Executor executor, String nome, int quantParametros ) {
        FuncBuscaResult result = esteBusca.busca( no, executor, nome, quantParametros );
        if ( result == null )
            result = superBusca.busca( no, executor, nome, quantParametros );
        
        return result;
    }
    
}
