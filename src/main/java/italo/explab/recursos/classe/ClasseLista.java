package italo.explab.recursos.classe;

import java.util.ArrayList;
import java.util.List;

public class ClasseLista {
    
    private final List<Classe> classes = new ArrayList();
        
    public void criaOuAltera( Classe classe ) {
        Classe c = this.buscaLocal( classe.getNomeCompleto() );
        if ( c != null )
            classes.remove( c );
        classes.add( classe );
    }           
                
    public Classe buscaLocal( String classeNomeCompleto ) {
        for( Classe c : classes ) {            
            if ( c.getNomeCompleto().equalsIgnoreCase( classeNomeCompleto ) )
                return c;
        }
        return null;
    }
    
    public Classe ignorePacoteBuscaLocal( String classeNome ) {
        for( Classe c : classes ) {            
            if ( c.getNome().equalsIgnoreCase( classeNome ) )
                return c;
        }
        return null;
    }

    public List<Classe> getClasses() {
        return classes;
    }
        
}
