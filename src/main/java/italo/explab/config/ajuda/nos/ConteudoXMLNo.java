package italo.explab.config.ajuda.nos;

import java.util.HashMap;
import java.util.Map;

public class ConteudoXMLNo extends ElementoXMLNo {
    
    private String id;
    private final Map<String, ChaveXMLNo> chavesMap = new HashMap();

    public ConteudoXMLNo( ElementoXMLNo parente ) {
        super( parente ); 
    }

    public void addChave( ChaveXMLNo chaveNo ) {
        chavesMap.put( chaveNo.getNome(), chaveNo );
    }
    
    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public Map<String, ChaveXMLNo> getChavesXMLNoMap() {
        return chavesMap;
    }

    @Override
    public ElementoXMLNo getParente() {
        return parente;
    }

    @Override
    public int getTipo() {
        return CONTEUDO;
    }
            
}
