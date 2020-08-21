
package italo.explab.arvore.busca.var;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.Executor;
import italo.explab.recursos.RecursoManager;

public interface VarBusca {
    
    public VarBuscaResult busca( ExecNo no, Executor executor, String nome );
            
    public RecursoManager recursos( ExecNo no, Executor executor );
    
}
