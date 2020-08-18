package italo.explab.arvore.busca.var;

import italo.explab.arvore.BlocoNo;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.Executor;
import italo.explab.recursos.RecursoManager;
import italo.explab.recursos.var.Variavel;

public class LocalVarBusca implements VarBusca {

    @Override
    public VarBuscaResult busca( ExecNo no, Executor executor, String nome ) {
        BlocoNo bno = no.blocoNo();
        if ( bno == null )
            return null;
            
        RecursoManager recursos = bno.getBloco().getRecursos();
        Variavel v = recursos.getVarLista().buscaLocal( nome );
        if ( v != null ) {
            return new VarBuscaResult( v, recursos, null );
        }
        
        return null;
    }

    @Override
    public RecursoManager recursos( ExecNo no, Executor executor ) {
        BlocoNo bno = no.blocoNo();
        if ( bno == null )
            return null;
        
        return bno.getBloco().getRecursos();
    }

}
