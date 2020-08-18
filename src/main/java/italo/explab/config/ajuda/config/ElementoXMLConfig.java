package italo.explab.config.ajuda.config;

import italo.explab.config.ajuda.AjudaXMLResult;
import org.w3c.dom.Element;
import italo.explab.config.ajuda.nos.ElementoXMLNo;

public interface ElementoXMLConfig {
    
    public AjudaXMLResult processa( Element el, ElementoXMLNo elementoNo );
    
}
