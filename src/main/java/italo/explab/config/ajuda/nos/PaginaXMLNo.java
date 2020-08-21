
package italo.explab.config.ajuda.nos;

import java.util.ArrayList;
import java.util.List;

public class PaginaXMLNo extends ElementoXMLNo {
    
    private String url = null;
    private final List<ConteudoXMLNo> conteudoNos = new ArrayList();
    private final List<ChaveXMLNo> chaveXMLNos = new ArrayList();
    
    public PaginaXMLNo( ElementoXMLNo parente ) {
        this( parente, null );
    }
    
    public PaginaXMLNo( ElementoXMLNo parente, String url ) {
        super( parente );
        this.url = url;
    }

    public void addConteudoNo( ConteudoXMLNo no ) {
        conteudoNos.add( no );
    }
    
    public void addChaveNo( ChaveXMLNo no ) {
        chaveXMLNos.add( no );
    }
    
    public String getURL() {
        return url;
    }

    public void setURL(String url) {
        this.url = url;
    }   

    public List<ChaveXMLNo> getChaveNos() {
        return chaveXMLNos;
    }
    
    public List<ConteudoXMLNo> getConteudoNos() {
        return conteudoNos;
    }

    @Override
    public int getTipo() {
        return PAGINA;
    }
        
}
