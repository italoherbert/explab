package italo.explab.arvore.busca.func;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.Executor;

public interface FuncBusca {
    
    public FuncBuscaResult busca( ExecNo no, Executor executor, String nome, int quantParametros );
        
}
