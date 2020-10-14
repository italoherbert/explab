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
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import libs.comparador.Comparador;
import org.w3c.dom.Node;

public class ProjetosXMLManager {
    
    private final List<ProjetoXMLNo> projetos = new ArrayList();
    
    private final ExpLabIDEAplic aplic;

    public ProjetosXMLManager(ExpLabIDEAplic aplic) {
        this.aplic = aplic;
    }    
    
    public void carrega( String arquivo ) {        
        projetos.clear();
        
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
     
                if ( new File( basedir ).exists() )
                    projetos.add( new ProjetoXMLNo( nome, basedir ) );
            }            
        } catch ( ParserConfigurationException | SAXException | IOException ex ) {
            aplic.getMSGManager().getErro( IDEErroMSGs.ARQUIVO_IO_ERRO, arquivo );
        }        
    }
        
    public boolean salva( String arquivo ) { 
        try {
            DocumentBuilder xmlBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = xmlBuilder.newDocument();                                

            Element raizEL = doc.createElement( "projetos" ); 
                                                           
            for( ProjetoXMLNo no : projetos ) {
                Element projEl = doc.createElement( "projeto" );
                projEl.setAttribute( "nome", no.getNome() ); 

                Element basedirEl = doc.createElement( "basedir");
                basedirEl.appendChild( doc.createTextNode( no.getBasedir() ) );
                projEl.appendChild( basedirEl );

                raizEL.appendChild( projEl );
            }                                                          

            TransformerFactory tfac = TransformerFactory.newInstance();
            Transformer transformer = tfac.newTransformer();                                

            DOMSource source = new DOMSource( (Node)raizEL );   
            try ( PrintWriter writer = new PrintWriter( new FileOutputStream( arquivo ) ) ) {
                StreamResult streamResult = new StreamResult( writer );
                transformer.transform( source, streamResult );
            }
            return true;
        } catch ( ParserConfigurationException | TransformerException | IOException ex ) {
            aplic.getMSGManager().mostraErro( IDEErroMSGs.ARQUIVO_IO_ERRO, arquivo ); 
        }
        return false;
    }

    public void setProjetoNome( String nome, String novoNome, Comparador comp ) {
        ProjetoXMLNo no = this.getProjeto( nome, comp );
        if ( no != null ) {
            no.setNome( novoNome );                 
            
            int i1 = no.getBasedir().lastIndexOf( nome );
            int i2 = i1 + nome.length();
            int len = no.getBasedir().length();
            no.setBasedir( no.getBasedir().substring( 0, i1 ) + novoNome + no.getBasedir().substring( i2, len ) );
        }
    }
    
    public String getBasedir( String nome, Comparador comp ) {
        ProjetoXMLNo no = this.getProjeto( nome, comp );
        if ( no != null )
            return no.getBasedir();
        
        return null;
    }
    
    public ProjetoXMLNo getProjeto( String nome, Comparador comp ) {
        for( ProjetoXMLNo no : projetos )
            if ( comp.igual( no.getNome(), nome ) )                 
                return no;        
        return null;
    }
    
    public List<ProjetoXMLNo> getProjetos() {
        return projetos;
    }        
    
}
