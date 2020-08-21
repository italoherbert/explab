package italo.explab.arvore.busca.construtor;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.Executor;
import italo.explab.construtor.Construtor;
import italo.explab.recursos.classe.Objeto;

public class SuperConstrutorBusca implements ConstrutorBusca {

    @Override
    public ConstrutorBuscaResult busca( ExecNo no, Executor executor, int quantParametros ) {
        Objeto obj = no.objeto( executor );
        if ( obj == null )
            return null;         
        
        Objeto superObjeto = obj.getSuperObjeto();
        if ( superObjeto != null ) {
            Construtor c = superObjeto.getRecursos().getConstrutorLista().buscaLocal( quantParametros );                                                 
                            
            if ( c != null )
                return new ConstrutorBuscaResult( c, superObjeto );
        }
        
        return null;
    }
    
}
