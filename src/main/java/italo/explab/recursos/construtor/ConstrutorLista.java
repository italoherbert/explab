package italo.explab.recursos.construtor;

import italo.explab.construtor.Construtor;
import java.util.ArrayList;
import java.util.List;

public class ConstrutorLista {

    private final List<Construtor> construtores = new ArrayList();
            
    public boolean addConstrutor( Construtor construtor ) {
        int quantParams = construtor.getParametros().length;
        
        Construtor c = this.buscaLocal( quantParams );
        if ( c != null )
            return false;
        
        construtores.add( construtor );
        return true;
    }
        
    public Construtor buscaLocal( int quantParametros ) {
        for( Construtor c : construtores )
            if ( c.getParametros().length == quantParametros )
                return c;
        return null;
    }
    
    public List<Construtor> getConstrutores() {
        return construtores;
    }
    
}
