package italo.explab.config.ajuda.config;

import italo.explab.ErroMSGs;
import italo.explab.config.ajuda.AjudaXMLResult;
import italo.explab.config.ajuda.AjudaXMLManager;
import italo.explab.config.ajuda.ValorTextoProcessado;
import italo.explab.config.ajuda.nos.AjudaXMLNo;
import italo.explab.msg.Erro;
import org.w3c.dom.Element;
import italo.explab.config.ajuda.nos.ElementoXMLNo;
import italo.explab.config.ajuda.nos.PropriedadeXMLNo;

public class PropriedadesXMLConfig implements ElementoXMLConfig {
    
    private final AjudaXMLManager manager;

    public PropriedadesXMLConfig(AjudaXMLManager manager) {
        this.manager = manager;
    }

    @Override
    public AjudaXMLResult processa( Element el, ElementoXMLNo ajudaNo ) {        
        String nome = el.getAttribute( "nome" );
        if ( nome == null ) {
            Erro erro = new Erro( this.getClass(), ErroMSGs.CONFIG_XML_ATRIBUTO_NAO_ENCONTRADO, "nome" );
            return new AjudaXMLResult( erro );
        }
        
        String valor = el.getFirstChild().getNodeValue();
        ValorTextoProcessado vtp = manager.valor( (AjudaXMLNo)ajudaNo, valor );

        if ( vtp.getErro() != null )
            return new AjudaXMLResult( vtp.getErro() );
        
        PropriedadeXMLNo propNo = new PropriedadeXMLNo( ajudaNo, nome, vtp.getValor() );        
        return new AjudaXMLResult( propNo );       
    }
    
}
