package italo.explab.config.ajuda.config;

import italo.explab.ErroMSGs;
import italo.explab.config.ajuda.AjudaXMLResult;
import italo.explab.config.ajuda.AjudaXMLManager;
import italo.explab.config.ajuda.ValorTextoProcessado;
import italo.explab.config.ajuda.nos.AjudaXMLNo;
import italo.explab.msg.Erro;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import italo.explab.config.ajuda.nos.ElementoXMLNo;

public class ArqRefXMLConfig implements ElementoXMLConfig {

    private final AjudaXMLManager manager;

    public ArqRefXMLConfig(AjudaXMLManager manager) {
        this.manager = manager;
    }
    
    @Override
    public AjudaXMLResult processa( Element el, ElementoXMLNo parenteNo ) {        
        String arquivo = el.getFirstChild().getNodeValue();
        if ( arquivo == null ) {
            Erro erro = new Erro( this.getClass(), ErroMSGs.CONFIG_XML_ATRIBUTO_NAO_ENCONTRADO, el.getTagName(), "nome" );
            return new AjudaXMLResult( erro );
        }
        
        AjudaXMLNo ajudaNo = parenteNo.ajudaNo();
        ValorTextoProcessado vtp = manager.valor( ajudaNo, arquivo );
        if ( vtp.getErro() != null )
            return new AjudaXMLResult( vtp.getErro() );
        
        arquivo = vtp.getValor();
                        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse( arquivo );            
                        
            Element raizNo = doc.getDocumentElement();
        
            return manager.getConteudosXMLConfig().processa( raizNo, ajudaNo );                               
        } catch ( ParserConfigurationException ex ) {           
            Erro erro = new Erro( this.getClass(), ErroMSGs.CONFIG_XML_INTER_ERRO, arquivo );
            return new AjudaXMLResult( erro );
        } catch (SAXException ex) {
            Erro erro = new Erro( this.getClass(), ErroMSGs.CONFIG_XML_PROC_ERRO, arquivo );
            return new AjudaXMLResult( erro );
        } catch (IOException ex) {
            Erro erro = new Erro( this.getClass(), ErroMSGs.CONFIG_XML_LEITURA_ERRO, arquivo );
            return new AjudaXMLResult( erro );
        }       
    }
            
}
