
package italo.explab.config.ajuda.config;

import italo.explab.ErroMSGs;
import italo.explab.config.ajuda.AjudaXMLResult;
import italo.explab.config.ajuda.AjudaXMLManager;
import italo.explab.config.ajuda.nos.AjudaXMLNo;
import italo.explab.msg.Erro;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import italo.explab.config.ajuda.nos.ElementoXMLNo;
import italo.explab.config.ajuda.nos.PaginaXMLNo;
import italo.explab.config.ajuda.nos.PropriedadeXMLNo;

public class ConteudosXMLConfig implements ElementoXMLConfig {

    private final AjudaXMLManager manager;

    public ConteudosXMLConfig(AjudaXMLManager manager) {
        this.manager = manager;
    }
    
    @Override
    public AjudaXMLResult processa( Element el, ElementoXMLNo parenteNo ) {     
        AjudaXMLNo ajudaNo = (AjudaXMLNo)parenteNo;
        NodeList elsLista = el.getElementsByTagName( "propriedade" );
        int len = elsLista.getLength();
        for( int i = 0; i < len; i++ ) {
            Element elemento = (Element)elsLista.item( i );
            AjudaXMLResult result = manager.getPropriedadeXMLConfig().processa( elemento, ajudaNo );               
            if ( result.getErro() != null ) {         
                String nome = elemento.getAttribute( "nome" );
                Erro erro = new Erro( this.getClass(), ErroMSGs.CONFIG_XML_PROPRIEDADE_INVALIDA, nome );
                return new AjudaXMLResult( erro );
            }       
            PropriedadeXMLNo propNo = (PropriedadeXMLNo)result.getNo();
            ajudaNo.addPropriedade( propNo ); 
            
            manager.addPropriedade( propNo.getNome(), propNo.getValor() ); 
        }
                                        
        NodeList lista = el.getChildNodes();
        for( int i = 0; i < lista.getLength(); i++ ) {
            Node no = lista.item( i );
            if ( no.getNodeType() == Node.ELEMENT_NODE ) {                                               
                Element elemento = (Element)no;
                String tag = elemento.getTagName();
                AjudaXMLResult result = null;
                if ( tag.equalsIgnoreCase( "arquivo" ) ) {
                    result = manager.getArqRefXMLConfig().processa( elemento, ajudaNo );                    
                } else if ( tag.equalsIgnoreCase( "pagina" ) ) {
                    result = manager.getPaginaXMLConfig().processa( elemento, ajudaNo );
                    if ( result.getErro() != null )
                        return result;                        
                    
                    ajudaNo.addPaginaNo( (PaginaXMLNo)result.getNo() ); 
                } else if ( tag.equalsIgnoreCase( "conteudos" ) ) {
                    result = this.processa( elemento, ajudaNo );                                        
                }   
                
                if ( result != null )
                    if ( result.getErro() != null )
                        return result;
            }            
        }
        
        return new AjudaXMLResult( ajudaNo ); 
    }
     
}
