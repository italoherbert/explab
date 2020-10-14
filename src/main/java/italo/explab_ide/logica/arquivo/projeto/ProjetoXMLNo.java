package italo.explab_ide.logica.arquivo.projeto;

public class ProjetoXMLNo {
       
    private String nome;
    private String basedir;

    public ProjetoXMLNo( String nome, String basedir ) {
        this.nome = nome;
        this.basedir = basedir;
    }

    public String getNome() {
        return nome;
    }

    public String getBasedir() {
        return basedir;
    }
    
    public void setNome( String nome ) {
        this.nome = nome;
    }
    
    public void setBasedir( String basedir ) {
        this.basedir = basedir;
    }
    
}
