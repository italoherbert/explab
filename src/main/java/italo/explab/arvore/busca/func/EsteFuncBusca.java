package italo.explab.arvore.busca.func;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.Executor;
import italo.explab.func.Func;
import italo.explab.recursos.RecursoManager;
import italo.explab.recursos.classe.Objeto;

public class EsteFuncBusca implements FuncBusca {

    @Override
    public FuncBuscaResult busca( ExecNo no, Executor executor, String nome, int quantParametros ) {
        Objeto obj = no.objeto( executor );
        if ( obj == null && no.getParente() != null )            
            obj = no.getParente().objeto( executor );
        
        if ( obj == null )
            return null;
        
        RecursoManager recursos = obj.getRecursos();        
        Func f = recursos.getFuncLista().buscaLocal( nome, quantParametros );         
        if ( f != null )
            return new FuncBuscaResult( f, obj );
        
        return null;
    }
        
}
