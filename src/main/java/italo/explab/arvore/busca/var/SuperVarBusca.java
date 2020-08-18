package italo.explab.arvore.busca.var;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.Executor;
import italo.explab.recursos.RecursoManager;
import italo.explab.recursos.classe.Objeto;
import italo.explab.recursos.var.Variavel;

public class SuperVarBusca implements VarBusca {

    @Override
    public VarBuscaResult busca( ExecNo no, Executor executor, String nome ) {
        Objeto obj = no.objeto( executor );
        if ( obj != null ) {
            obj = obj.getSuperObjeto();
            while ( obj != null ) {
                RecursoManager recursos = obj.getRecursos();
                Variavel v = recursos.getVarLista().buscaLocal( nome );
                if ( v != null )
                    return new VarBuscaResult( v, recursos, obj );
                
                obj = obj.getSuperObjeto();
            }
        }                
        return null;
    }
            
    @Override
    public RecursoManager recursos( ExecNo no, Executor executor ) {
        Objeto obj = no.objeto( executor );
        if ( obj == null )
            return null;
        
        Objeto superObjeto = obj.getSuperObjeto();
        if ( superObjeto == null )
            return null;
        
        return superObjeto.getRecursos();
    }
    
}
