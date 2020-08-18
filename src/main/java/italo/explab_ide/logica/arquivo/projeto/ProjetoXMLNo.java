package italo.explab_ide.logica.arquivo.projeto;

public class ProjetoXMLNo {
       
    private final String nome;
    private final String basedir;

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
    
}
