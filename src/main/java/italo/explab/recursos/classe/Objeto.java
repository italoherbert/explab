package italo.explab.recursos.classe;

import italo.explab.recursos.ObjetoRecursoManager;
import italo.explab.var.ObjetoVar;

public class Objeto {

    private final ObjetoRecursoManager recursos;
    private final Classe classe;
    private Objeto superObjeto;
    private Objeto containerObjeto;
    private int referencia;
    private ObjetoVar objvar;
        
    public Objeto( Classe classe, ObjetoRecursoManager recursos, int referencia ) {
        this.classe = classe;
        this.recursos = recursos;
        this.referencia = referencia;
    }
    
    public boolean instanciaDe( String classeNome ) {
        if ( classe.getNomeCompleto().equalsIgnoreCase( classeNome ) )
            return true;         
        if ( classe.getNome().equalsIgnoreCase( classeNome ) )
            return true;
        
        Objeto o = superObjeto;
        while( o != null ) {
            if ( o.getClasse().getNomeCompleto().equalsIgnoreCase( classeNome ) )
                return true;
            if ( o.getClasse().getNome().equalsIgnoreCase( classeNome ) )
                return true;
            
            o = o.getSuperObjeto();
        }
        
        return false;
    }
       
    public ObjetoRecursoManager getRecursos() {
        return recursos;
    }
    
    public Classe getClasse() {
        return classe;
    }

    public Objeto getSuperObjeto() {
        return superObjeto;
    }

    public void setSuperObjeto(Objeto superObjeto) {
        this.superObjeto = superObjeto;
    }        

    public Objeto getContainerObjeto() {
        return containerObjeto;
    }

    public void setContainerObjeto(Objeto containerObjeto) {
        this.containerObjeto = containerObjeto;
    }

    public int getReferencia() {
        return referencia;
    }

    public void setReferencia(int referencia) {
        this.referencia = referencia;
    }

    public ObjetoVar getObjVar() {
        return objvar;
    }

    public void setObjVar(ObjetoVar objvar) {
        this.objvar = objvar;
    }
    
}
