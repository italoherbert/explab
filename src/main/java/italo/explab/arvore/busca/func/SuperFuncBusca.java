package italo.explab.arvore.busca.func;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.Executor;
import italo.explab.func.Func;
import italo.explab.recursos.classe.Objeto;

public class SuperFuncBusca implements FuncBusca {
    
    @Override
    public FuncBuscaResult busca( ExecNo no, Executor executor, String nome, int quantParametros ) {
        Objeto obj = no.objeto( executor );        
        if ( obj != null ) {
            obj = obj.getSuperObjeto();
            while ( obj != null ) {
                Func f = obj.getRecursos().getFuncLista().buscaLocal( nome, quantParametros );
                if ( f != null )
                    return new FuncBuscaResult( f, obj );

                obj = obj.getSuperObjeto();
            }                    
        }                          
        return null;
    }

}
