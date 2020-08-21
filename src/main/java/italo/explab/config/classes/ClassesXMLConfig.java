package italo.explab.config.classes;

import italo.explab.ErroMSGs;
import italo.explab.config.XMLConfigException;
import italo.explab.InterAplic;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ClassesXMLConfig {
    
    private final List<ClasseXMLNo> classes = new ArrayList();
    
    private final InterAplic aplic;

    public ClassesXMLConfig(InterAplic aplic) {
        this.aplic = aplic;
    }    
    
    public void carrega( String arquivo ) throws XMLConfigException {        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse( arquivo );
            
            Element classesNo = doc.getDocumentElement();
            NodeList lista = classesNo.getElementsByTagName( "classe" );
            int len = lista.getLength();
            for( int i = 0; i < len; i++ ) {
                Element classeNo = (Element)lista.item( i );
                String dir = classeNo.getAttribute( "dir" );
                String arq = classeNo.getAttribute( "arq" );
                
                classes.add( new ClasseXMLNo( dir, arq ) );
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

    public List<ClasseXMLNo> getClasses() {
        return classes;
    }        
    
}
