package italo.explab.config.ajuda.config;

import italo.explab.ErroMSGs;
import italo.explab.config.ajuda.AjudaXMLManager;
import italo.explab.config.ajuda.AjudaXMLResult;
import italo.explab.config.ajuda.nos.ChaveXMLNo;
import italo.explab.config.ajuda.nos.ConteudoXMLNo;
import italo.explab.msg.Erro;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import italo.explab.config.ajuda.nos.ElementoXMLNo;

public class ConteudoXMLConfig implements ElementoXMLConfig {
        
    private final AjudaXMLManager manager;

    public ConteudoXMLConfig( AjudaXMLManager manager ) {
        this.manager = manager;
    }
    
    @Override
    public AjudaXMLResult processa( Element el, ElementoXMLNo paginaNo ) {
        ConteudoXMLNo conteudoNo = new ConteudoXMLNo( paginaNo );
        
        String id = el.getAttribute( "id" );
        if ( id == null ) {
            Erro erro = new Erro( this.getClass(), ErroMSGs.CONFIG_XML_ATRIBUTO_NAO_ENCONTRADO, "id" );
            return new AjudaXMLResult( erro );
        }                
        conteudoNo.setID( id );
    
        NodeList lista = el.getElementsByTagName( "chave" );
        int len = lista.getLength();
        for( int i = 0; i < len; i++ ) {
            Element elemento = (Element)lista.item( i );
            AjudaXMLResult chaveResult = manager.getChaveConteudoXMLConfig().processa( elemento, paginaNo );
            if ( chaveResult.getErro() != null )
                return chaveResult;
            
            ChaveXMLNo chaveNo = (ChaveXMLNo)chaveResult.getNo();
            conteudoNo.addChave( chaveNo ); 
        }
        
        return new AjudaXMLResult( conteudoNo );
    }
    
}
