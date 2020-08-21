package italo.explab.config.msg;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.config.XMLConfigException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MSGConfig {
    
    private final Map<String, MSGErro> erros = new HashMap();
    private final Map<String, String> infos = new HashMap();
    
    private String exceptionClasseNome;
    private String runtimeExceptionClasseNome; 
    
    private final InterAplic aplic;

    public MSGConfig( InterAplic aplic ) {
        this.aplic = aplic;
    }
    
    public void carrega( String infoPropArq, String erroPropArq, String exceptionXMLArq ) throws XMLConfigException, IOException {
        this.carregaInfos( infoPropArq );
        this.carregaErros( erroPropArq );
        this.carregaClasseExceptions( exceptionXMLArq ); 
    }

    private void carregaClasseExceptions( String arquivo ) throws XMLConfigException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse( arquivo );
            
            Element exceptionsNo = doc.getDocumentElement();
            
            Element pacoteNo = (Element)exceptionsNo.getElementsByTagName( "pacote" ).item( 0 ); 
            Element exClasseNomeNo = (Element)exceptionsNo.getElementsByTagName( "exception-classe-nome" ).item( 0 ); 
            Element runtimeEXClasseNomeNo = (Element)exceptionsNo.getElementsByTagName( "runtime-exception-classe-nome" ).item( 0 ); 

            String pacote = pacoteNo.getFirstChild().getNodeValue();
            
            exceptionClasseNome = pacote+"."+exClasseNomeNo.getFirstChild().getNodeValue();
            runtimeExceptionClasseNome = pacote+"."+runtimeEXClasseNomeNo.getFirstChild().getNodeValue();
            
            NodeList lista = exceptionsNo.getElementsByTagName( "exception" );
            int len = lista.getLength();
            for( int i = 0; i < len; i++ ) {
                Element classeNo = (Element)lista.item( i );
                String classeNome = pacote+"."+classeNo.getAttribute( "classeNome" );
                
                NodeList chaves = classeNo.getElementsByTagName( "chave" );
                int len2 = chaves.getLength();
                for( int j = 0; j < len2; j++ ) {
                    Element chaveNo = (Element)chaves.item( j );
                    String chave = chaveNo.getFirstChild().getNodeValue();
                                        
                    MSGErro e = erros.get( chave );
                    if ( e != null )
                        e.setExceptionClasseNome( classeNome );                                     
                }                
            }                        
        } catch ( ParserConfigurationException ex ) {
            String erro = aplic.getMSGManager().getErroMSG( ErroMSGs.CONFIG_XML_INTER_ERRO, arquivo );
            throw new XMLConfigException( erro, ex );
        } catch (SAXException ex) {
            String erro = aplic.getMSGManager().getErroMSG( ErroMSGs.CONFIG_XML_PROC_ERRO, arquivo );
            throw new XMLConfigException( erro, ex );
        } catch (IOException ex) {
            String erro = aplic.getMSGManager().getErroMSG( ErroMSGs.CONFIG_XML_LEITURA_ERRO, arquivo );
            throw new XMLConfigException( erro, ex );
        } 
    }
        
    private void carregaErros( String arquivo ) throws IOException {
        Properties p = new Properties();
        p.load( new FileInputStream( arquivo ) );
        
        Set<Object> keys = p.keySet();
        for( Object key : keys ) {
            String chave = String.valueOf( key );
                        
            erros.put( chave, new MSGErro( chave, p.getProperty( chave ) ) );
        }
    }
    
    private void carregaInfos( String arquivo ) throws IOException {
        Properties p = new Properties();
        p.load( new FileInputStream( arquivo ) );
        
        Set<Object> keys = p.keySet();
        for( Object key : keys ) {
            String chave = String.valueOf( key );
            infos.put( chave, p.getProperty( chave ) );
        }
    }
    
    public Map<String, MSGErro> getErros() {
        return erros;
    }

    public Map<String, String> getInfos() {
        return infos;
    }

    public String getExceptionClasseNome() {
        return exceptionClasseNome;
    }

    public String getRuntimeExceptionClasseNome() {
        return runtimeExceptionClasseNome;
    }
    
}
