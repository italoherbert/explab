package italo.explab.config.ajuda;

import italo.explab.ErroMSGs;
import italo.explab.config.ajuda.config.ArqRefXMLConfig;
import italo.explab.config.ajuda.config.ChaveXMLConfig;
import italo.explab.config.ajuda.config.PropriedadesXMLConfig;
import italo.explab.config.ajuda.config.PaginaXMLConfig;
import italo.explab.config.ajuda.config.ConteudoXMLConfig;
import italo.explab.config.ajuda.config.ConteudosXMLConfig;
import italo.explab.config.ajuda.nos.AjudaXMLNo;
import italo.explab.config.ajuda.nos.ChaveXMLNo;
import italo.explab.config.ajuda.nos.ConteudoXMLNo;
import italo.explab.config.ajuda.nos.PaginaXMLNo;
import italo.explab.config.ajuda.nos.PropriedadeXMLNo;
import italo.explab.msg.Erro;
import italo.explab.util.ContadorUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class AjudaXMLManager {

    private final ArqRefXMLConfig arqRefXMLConfig = new ArqRefXMLConfig( this );
    private final ConteudosXMLConfig conteudosXMLConfig = new ConteudosXMLConfig( this );
    private final PaginaXMLConfig paginaXMLConfig = new PaginaXMLConfig( this );
    private final ConteudoXMLConfig conteudoXMLConfig = new ConteudoXMLConfig( this );
    private final PropriedadesXMLConfig propriedadesXMLConfig = new PropriedadesXMLConfig( this );
    private final ChaveXMLConfig chaveConteudoXMLConfig = new ChaveXMLConfig();
    
    private final Map<String, String> propriedades = new HashMap();
    
    private AjudaXMLNo ajudaNo = null;
    
    private final ContadorUtil contUtil; 
    
    public AjudaXMLManager( ContadorUtil contUtil ) {
        this.contUtil = contUtil;
    }                
    
    public String getDocURL() {
        return propriedades.get( "doc.url" );
    }
    
    public AjudaURLParams buscaAjuda( String[] termos ) {
        String pagina = null;
        String ancora = null;
                        
        List<PaginaXMLNo> paginas = ajudaNo.getPaginaNos();                    
        for( PaginaXMLNo paginaNo : paginas ) {
            int paginaPeso = 0;
            String pagina2 = null;
            String ancora2 = null;
            List<ConteudoXMLNo> conteudoNos = paginaNo.getConteudoNos();
            for( ConteudoXMLNo conteudoNo : conteudoNos ) {                    
                Iterator<String> chavesIT = conteudoNo.getChavesXMLNoMap().keySet().iterator();                                                                        
                int conteudoPeso = 0;
                while( chavesIT.hasNext() ) {
                    String chave = chavesIT.next();
                    for( String termo : termos ) {            
                        if ( chave.equalsIgnoreCase( termo ) ) {                                                                                  
                            ChaveXMLNo chaveNo = conteudoNo.getChavesXMLNoMap().get( chave );
                            
                            ancora2 = conteudoNo.getID();
                            pagina2 = paginaNo.getURL();
                            conteudoPeso += chaveNo.getPeso();                            
                        }
                    }
                }                                
                List<ChaveXMLNo> chaveNos = paginaNo.getChaveNos();
                for( ChaveXMLNo chaveNo : chaveNos ) {
                    for( String termo : termos ) {            
                        if ( chaveNo.getNome().equalsIgnoreCase( termo ) ) {                                                                                                          
                            ancora2 = chaveNo.getNome();
                            pagina2 = paginaNo.getURL();
                            conteudoPeso += chaveNo.getPeso();                            
                        }
                    }             
                }
                if ( conteudoPeso > paginaPeso ) {
                    pagina = pagina2;
                    ancora = ancora2;
                    paginaPeso = conteudoPeso;                    
                }
            }                          
        }
               
        return new AjudaURLParams( pagina, ancora );
    }
    
    public AjudaXMLResult carrega( String arquivo ) {                         
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse( arquivo );            
                        
            Element raizNo = doc.getDocumentElement();
                    
            ajudaNo = new AjudaXMLNo( null );
            
            propriedades.clear();
            return conteudosXMLConfig.processa( raizNo, ajudaNo );                                                                   
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
    
    public ValorTextoProcessado valor( AjudaXMLNo ajudaNo, String valor ) {
        StringBuilder sb = new StringBuilder();
        int len = valor.length();
        int j = 0;
        while ( j < len ) {
            char ch = valor.charAt( j );
            switch( ch ) {
                case '$':
                    ValorTextoProcessado result = this.valorAtributoOuConteudo( ajudaNo, valor, j );
                    if ( result.getErro() != null )
                        return result;
                    
                    j += result.getJ();
                    sb.append( result.getValor() );
                    break;
                default:                   
                    sb.append( ch );
                    j++;
                    break;
            }
        }

        return new ValorTextoProcessado( sb.toString(), j );
    }
            
    private ValorTextoProcessado valorAtributoOuConteudo( AjudaXMLNo ajudaNo, String valor, int i ) {       
        int k = valor.indexOf( "$", i );
        int len = valor.length();
        if ( k >= i && k < len ) { 
            StringBuilder sb = new StringBuilder();
            
            boolean fim = false;
            int j = 0;
            j++;
            j += contUtil.contaEsps( valor, i+j );
            if ( valor.charAt( i+j ) == '{' ) {                    
                j++;          

                while( !fim && j < len ) {
                    char ch = valor.charAt( i+j );
                    switch( ch ) {
                        case '$':                                                        
                            ValorTextoProcessado result = this.valorAtributoOuConteudo( ajudaNo, valor, i+j );
                            if ( result.getErro() != null )
                                return result;
                            
                            sb.append( result.getValor() );

                            j += result.getJ();
                            break;
                        case '}':
                            String chave = sb.toString();
                            PropriedadeXMLNo propNo = ajudaNo.getPropriedadeNos().get( chave );
                            if ( propNo == null ) {
                                Erro erro = new Erro( this.getClass(), ErroMSGs.CONFIG_XML_PROPRIEDADE_NAO_ENCONTRADA, chave );
                                return new ValorTextoProcessado( erro );
                            }
                            return new ValorTextoProcessado( propNo.getValor(), j+1 );
                        default:
                            sb.append( ch );
                            j++;
                            break;
                    }
                } 
                                
                Erro erro = new Erro( this.getClass(), ErroMSGs.FECHA_CHAVES_ESPERADO );
                return new ValorTextoProcessado( erro ); 
            } else {
                Erro erro = new Erro( this.getClass(), ErroMSGs.ABRE_CHAVES_ESPERADO );
                return new ValorTextoProcessado( erro );
            }
        } else {
            Erro erro = new Erro( this.getClass(), ErroMSGs.INDICE_FORA_DA_FAIXA );
            return new ValorTextoProcessado( erro );
        }
    }
    
    public void addPropriedade( String nome, String valor ) {
        propriedades.put( nome, valor );
    }

    public Map<String, String> getPropriedades() {
        return propriedades;
    }

    public AjudaXMLNo getAjudaNo() {
        return ajudaNo;
    }

    public ArqRefXMLConfig getArqRefXMLConfig() {
        return arqRefXMLConfig;
    }

    public PropriedadesXMLConfig getPropriedadeXMLConfig() {
        return propriedadesXMLConfig;
    }

    public ConteudosXMLConfig getConteudosXMLConfig() {
        return conteudosXMLConfig;
    }

    public ConteudoXMLConfig getConteudoXMLConfig() {
        return conteudoXMLConfig;
    }

    public ChaveXMLConfig getChaveConteudoXMLConfig() {
        return chaveConteudoXMLConfig;
    }

    public PaginaXMLConfig getPaginaXMLConfig() {
        return paginaXMLConfig;
    }
    
}
