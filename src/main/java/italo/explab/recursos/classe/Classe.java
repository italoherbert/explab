package italo.explab.recursos.classe;

import italo.explab.arvore.exp.atrib.Atrib;
import italo.explab.codigo.Codigo;
import italo.explab.recursos.ClasseRecursoManager;
import java.util.List;

public class Classe {
    
    private final String pacote;
    private final String nome;
    private final Codigo codigo;

    private Atrib[] atribuicoes = {};    
    
    private Classe superClasse;
    private Classe containerClasse;
        
    private final ClasseRecursoManager recursos; 
    
    public Classe( String pacote, String nome, ClasseRecursoManager recursos, Codigo codigo ) {
        this.pacote = pacote;
        this.nome = nome;
        this.recursos = recursos;
        this.codigo = codigo;
    }   
    
    public Classe nova() {
        Classe nova = new Classe( pacote, nome, recursos, codigo );
        nova.setAtribuicoes( atribuicoes );  // não gera cópia
        nova.setContainerClasse( containerClasse );
        nova.setSuperClasse( superClasse );
        return nova;
    }
 
    public boolean isIgualOuFilhaNome( Classe classe ) {        
        if ( this.getNomeCompleto().equals( classe.getNomeCompleto() ) )
            return true;
        if ( superClasse != null )
            return superClasse.isIgualOuFilhaNome( classe );        
        return false;        
    }
                
    public String getNomeCompleto() {
        return ( pacote == null ? "" : pacote+"." ) + nome;
    }
    
    public String getNome() {
        return nome;
    }

    public String getPacote() {
        return pacote;
    }
        
    public ClasseRecursoManager getClasseRecursoManager() {
        return recursos;
    }

    public Codigo getCodigo() {
        return codigo;
    }

    public Classe getSuperClasse() {
        return superClasse;
    }

    public Classe getContainerClasse() {
        return containerClasse;
    }

    public void setSuperClasse(Classe superClasse) {
        this.superClasse = superClasse;
    }

    public void setContainerClasse(Classe containerClasse) {
        this.containerClasse = containerClasse;
    }

    public Atrib[] getAtribuicoes() {
        return atribuicoes;
    }

    public void setAtribuicoes(Atrib[] atribuicoes) {
        this.atribuicoes = atribuicoes;
    }
    
}
