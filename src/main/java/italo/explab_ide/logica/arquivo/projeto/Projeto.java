package italo.explab_ide.logica.arquivo.projeto;

public class Projeto {
    
    private final ProjetoXMLNo xmlNo;
    
    public Projeto( ProjetoXMLNo no ) {
        this.xmlNo = no;
    }
    
    public String getNome() {
        return xmlNo.getNome();
    }

    public String getBasedir() {
        return xmlNo.getBasedir();
    }
        
    public ProjetoXMLNo getXMLNo() {
        return xmlNo;
    }
    
}
