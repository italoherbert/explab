package italo.explab.config.ajuda.config;

import italo.explab.ErroMSGs;
import italo.explab.config.ajuda.AjudaXMLResult;
import italo.explab.config.ajuda.AjudaXMLManager;
import italo.explab.config.ajuda.ValorTextoProcessado;
import italo.explab.config.ajuda.nos.AjudaXMLNo;
import italo.explab.config.ajuda.nos.ChaveXMLNo;
import italo.explab.config.ajuda.nos.ConteudoXMLNo;
import italo.explab.config.ajuda.nos.PaginaXMLNo;
import italo.explab.msg.Erro;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import italo.explab.config.ajuda.nos.ElementoXMLNo;

public class PaginaXMLConfig implements ElementoXMLConfig {
 
    private final AjudaXMLManager manager;

    public PaginaXMLConfig(AjudaXMLManager manager) {
        this.manager = manager;
    }

    @Override
    public AjudaXMLResult processa( Element el, ElementoXMLNo elementoNo ) {        
        String url = el.getAttribute( "url" );
        if ( url == null ) {
            Erro erro = new Erro( this.getClass(), ErroMSGs.CONFIG_XML_ATRIBUTO_NAO_ENCONTRADO, "url" );
            return new AjudaXMLResult( erro );
        }
        
        AjudaXMLNo ajudaNo = (AjudaXMLNo)elementoNo;
        
        ValorTextoProcessado vtp = manager.valor( ajudaNo, url );
        if ( vtp.getErro() != null )
            return new AjudaXMLResult( vtp.getErro() );
        
        PaginaXMLNo paginaNo = new PaginaXMLNo( ajudaNo );
        paginaNo.setURL( vtp.getValor() ); 
                        
        NodeList lista = el.getChildNodes();                
        int len = lista.getLength();
        for( int i = 0; i < len; i++ ) {
            Node no = lista.item( i );
            if ( no.getNodeType() == Node.ELEMENT_NODE ) {
                AjudaXMLResult result;
                
                Element elemento = (Element)no;
                String tag = elemento.getTagName();
                if ( tag.equalsIgnoreCase( "conteudo" ) ) {
                    result = manager.getConteudoXMLConfig().processa( elemento, paginaNo );                                                            
                    paginaNo.addConteudoNo( (ConteudoXMLNo)result.getNo() );
                } else if ( tag.equalsIgnoreCase( "chave" ) ) {
                    result = manager.getChaveConteudoXMLConfig().processa( elemento, paginaNo );                    
                    paginaNo.addChaveNo( (ChaveXMLNo)result.getNo() );
                } else {
                    Erro erro = new Erro( this.getClass(), ErroMSGs.CONFIG_XML_TAG_DESCONHECIDA, tag );
                    return new AjudaXMLResult( erro );
                }                                
            }
        }
        

        return new AjudaXMLResult( paginaNo );
    }
    
}
