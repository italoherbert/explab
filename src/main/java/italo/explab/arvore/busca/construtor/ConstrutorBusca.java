package italo.explab.arvore.busca.construtor;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.Executor;

public interface ConstrutorBusca {
    
    public ConstrutorBuscaResult busca( ExecNo no, Executor executor, int quantParametros );
    
}
