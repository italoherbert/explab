package italo.explab.config.ajuda.config;

import italo.explab.ErroMSGs;
import italo.explab.config.ajuda.AjudaXMLResult;
import italo.explab.config.ajuda.nos.ChaveXMLNo;
import italo.explab.config.ajuda.nos.ElementoXMLNo;
import italo.explab.msg.Erro;
import org.w3c.dom.Element;

public class ChaveXMLConfig implements ElementoXMLConfig {
        
    @Override
    public AjudaXMLResult processa( Element el, ElementoXMLNo parenteNo ) {                
        String nome = el.getAttribute( "nome" );
        String peso = el.getAttribute( "peso" );

        try {    
            int pesoInt = Integer.parseInt( peso );
            ChaveXMLNo chaveNo = new ChaveXMLNo( parenteNo, nome, pesoInt );
            
            return new AjudaXMLResult( chaveNo );
        } catch ( NumberFormatException e ) {
            Erro erro = new Erro( this.getClass(), ErroMSGs.CONFIG_XML_VALOR_NUMERICO_ESPERADO, peso );
            return new AjudaXMLResult( erro );
        }                
    }
    
}
