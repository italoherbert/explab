package italo.explab.recursos;

import italo.explab.codigo.Codigo;
import italo.explab.recursos.classe.Objeto;
import italo.explab.recursos.classe.Classe;
import italo.explab.var.ObjetoVar;
import java.util.ArrayList;
import java.util.List;

public class ClasseManager {
    
    private final List<String> useArqNomes = new ArrayList();        
    
    private final List<Classe> classes = new ArrayList();
    private final List<Objeto> objetos = new ArrayList();
    private int objIDCont = 1;    
    
    public Classe novaClasse( String pacote, String classeNomeCompleto, Codigo codigo ) {
        ClasseRecursoManager recursos = new ClasseRecursoManager();
        Classe c = new Classe( pacote, classeNomeCompleto, recursos, codigo );        
        classes.add( c );
        return c;
    }
        
    public ObjetoVar novoObjeto( Classe classe ) {                        
        ObjetoRecursoManager novo = new ObjetoRecursoManager();
        classe.getClasseRecursoManager().carrega( novo ); 
                
        Objeto obj = new Objeto( classe, novo, objIDCont++ );        
        
        ObjetoVar objvar = new ObjetoVar( obj );        
        objetos.add( obj );
        
        obj.setObjVar( objvar ); 
        
        return objvar;
    }
          
    public Classe buscaClasse( String classeNome ) {
        for( Classe c : classes ) {
            if ( c.getNome().equalsIgnoreCase( classeNome ) )
                return c;
            if ( c.getNomeCompleto().equalsIgnoreCase( classeNome ) )
                return c;
        }
        return null;        
    }
    
    public Objeto buscaObjeto( int referencia ) {
        for( Objeto obj : objetos )
            if ( obj.getReferencia() == referencia )
                return obj;
        return null;
    }
    
    public boolean addUseArqNome( String useArqNome ) {
        if ( verificaSeUseJaProcessado( useArqNome ) )
            return false;
        return useArqNomes.add( useArqNome );
    }
    
    public boolean verificaSeUseJaProcessado( String useArqNome ) {
        for( String usenome : useArqNomes )
            if ( usenome.equalsIgnoreCase( useArqNome ) )
                return true;
        return false;
    }

    public List<Classe> getClasses() {
        return classes;
    }

    public List<Objeto> getObjetos() {
        return objetos;
    }
    
}
