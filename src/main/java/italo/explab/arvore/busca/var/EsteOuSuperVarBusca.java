package italo.explab.arvore.busca.var;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.Executor;
import italo.explab.recursos.RecursoManager;

public class EsteOuSuperVarBusca implements VarBusca {
  
    private final EsteVarBusca esteBusca = new EsteVarBusca();
    private final SuperVarBusca superBusca = new SuperVarBusca();

    @Override
    public VarBuscaResult busca( ExecNo no, Executor executor, String nome ) {
        VarBuscaResult v = esteBusca.busca( no, executor, nome );
        if ( v == null )
            v = superBusca.busca( no, executor, nome );        
        return v;
    }

    @Override
    public RecursoManager recursos( ExecNo no, Executor executor ) {
        RecursoManager recursos = esteBusca.recursos( no, executor );
        if ( recursos == null )
            recursos = superBusca.recursos( no, executor );
        
        return recursos;
    }
    
}

