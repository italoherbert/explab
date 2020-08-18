package italo.explab_ide.logica.arquivo.projeto;

import italo.explab_ide.ExpLabIDEAplic;
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
import italo.explab_ide.IDEErroMSGs;

public class ProjetosXMLLeitor {
    
    private final List<ProjetoXMLNo> projetos = new ArrayList();
    
    private final ExpLabIDEAplic aplic;

    public ProjetosXMLLeitor(ExpLabIDEAplic aplic) {
        this.aplic = aplic;
    }    
    
    public void carrega( String arquivo ) {        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse( arquivo );
            
            Element classesNo = doc.getDocumentElement();
            NodeList lista = classesNo.getElementsByTagName( "projeto" );
            int len = lista.getLength();
            for( int i = 0; i < len; i++ ) {
                Element classeNo = (Element)lista.item( i );
                String nome = classeNo.getAttribute( "nome" );
                
                Element basedirNo = (Element)classeNo.getElementsByTagName( "basedir" ).item( 0 );
                String basedir = basedirNo.getFirstChild().getNodeValue();
                
                projetos.add( new ProjetoXMLNo( nome, basedir ) );
            }            
        } catch ( ParserConfigurationException | SAXException | IOException ex ) {
            aplic.getMSGManager().getErro( IDEErroMSGs.ARQUIVO_IO_ERRO, arquivo );
        }        
    }

    public List<ProjetoXMLNo> getProjetos() {
        return projetos;
    }        
    
}
