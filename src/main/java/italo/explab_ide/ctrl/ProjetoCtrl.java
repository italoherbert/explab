package italo.explab_ide.ctrl;

import italo.explab_ide.logica.arquivo.ArqArvNo;
import italo.explab_ide.ExpLabIDEAplic;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import libs.gui.arv.ArvGUITO;
import libs.gui.arv.ArvNo;
import italo.explab_ide.IDEErroMSGs;
import italo.explab_ide.config.IDEConfig;
import italo.explab_ide.gui.GUIVisivel;
<<<<<<< HEAD
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import italo.explab_ide.logica.arquivo.projeto.ProjetoXMLNo;
import italo.explab_ide.logica.arquivo.projeto.Projeto;
=======
import italo.explab_ide.logica.arquivo.projeto.Projeto;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import italo.explab_ide.logica.arquivo.projeto.ProjetoXMLNo;
>>>>>>> origin/versao-2.2

public class ProjetoCtrl {        
    
    private final ExpLabIDEAplic aplic;
            
    public ProjetoCtrl( ExpLabIDEAplic aplic ) {
        this.aplic = aplic;
    }
                        
    public void novoOuAbrir( String projPastaCaminho, GUIVisivel guivisivel, String okMSGChave ) {
        IDEConfig config = aplic.getConfig();
        String charset = config.getProjetoCharsetPadrao();
        String execScript = config.getProjetoExecScriptPadrao();
        String projConfigArq = config.getProjetoConfigArqPadrao();
        
        String basedir = projPastaCaminho.replace( "\\", "/" );
        if ( !basedir.endsWith( "/" ) )
            basedir += "/";  
        
        int len = basedir.length();
        int k = basedir.substring( 0, len-1 ).lastIndexOf( "/" );
         
        String projNome = projPastaCaminho.substring( k+1, len-1 );
        
<<<<<<< HEAD
        List<ProjetoXMLNo> projetos = aplic.getProjetosXMLLeitor().getProjetos();
=======
        List<ProjetoXMLNo> projetos = aplic.getProjetosXMLManager().getProjetos();
>>>>>>> origin/versao-2.2
        int size = projetos.size();
        boolean achou = false;
        for( int i = 0; !achou && i < size; i++ ) {
            ProjetoXMLNo proj = projetos.get( i );
            
            String projBasedir = proj.getBasedir();
            if ( !proj.getBasedir().endsWith( "/" ) )
                projBasedir += "/";
            
            if ( config.getComparador().igual( basedir, projBasedir ) )
                achou = true;
        }
        
        if ( achou ) {
            aplic.getMSGManager().mostraErro(IDEErroMSGs.PROJETO_JA_EXISTE, basedir ); 
            return;
        }
        
        File novoFile = new File( basedir );
        novoFile.mkdirs();
        
        File confFile = new File( basedir + projConfigArq );        
            
        try {        
            if ( !confFile.exists() ) {
                confFile.createNewFile();
            
                FileOutputStream fos = new FileOutputStream( confFile.getAbsolutePath() );
                try ( PrintStream out = new PrintStream( fos, false, charset ) ) {
                    out.println();
                    out.println( "principal="+execScript );
                    out.println( "charset="+charset );
                }                        
            }

            ProjetoXMLNo projXMLNo = new ProjetoXMLNo( projNome, projPastaCaminho );                
            Projeto proj = new Projeto( projXMLNo );
            this.addProj( proj ); 
                                
<<<<<<< HEAD
            boolean atualizou = this.atualizaConfigXMLArquivo();
=======
            boolean atualizou = aplic.getProjetosXMLManager().salva( aplic.getConfig().getProjetosXMLConfigCaminho() );
>>>>>>> origin/versao-2.2
            if ( atualizou ) {
                guivisivel.setVisivel( false ); 
                aplic.getMSGManager().mostraInfo( okMSGChave );                                                                               
            }
        } catch (FileNotFoundException ex) {
            aplic.getMSGManager().mostraErro( IDEErroMSGs.ARQUIVO_NAO_ENCONTRADO, projConfigArq ); 
        } catch (IOException ex) {
            aplic.getMSGManager().mostraErro( IDEErroMSGs.ARQUIVO_IO_ERRO, projConfigArq ); 
        }                           
<<<<<<< HEAD
    }
    
    public boolean atualizaConfigXMLArquivo() {
        String projsXMLConfigCaminho = aplic.getConfig().getProjetosXMLConfigCaminho();
        
        try {
            DocumentBuilder xmlBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = xmlBuilder.newDocument();                                

            Element raizEL = doc.createElement( "projetos" ); 
                                                           
            for( ProjetoXMLNo no : aplic.getProjetosXMLLeitor().getProjetos() ) {
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
            try ( PrintWriter writer = new PrintWriter( new FileOutputStream( projsXMLConfigCaminho ) ) ) {
                StreamResult streamResult = new StreamResult( writer );
                transformer.transform( source, streamResult );
            }
            return true;
        } catch ( ParserConfigurationException | TransformerException | IOException ex ) {
            aplic.getMSGManager().mostraErro( IDEErroMSGs.ARQUIVO_IO_ERRO, projsXMLConfigCaminho ); 
        }
        return false;
    }
                        
    public void carregaProjs() {
        for( ProjetoXMLNo no : aplic.getProjetosXMLLeitor().getProjetos() ) {                                    
            Projeto proj = new Projeto( no );            
            ArvNo raiz = this.geraProjArvore( proj );
            if ( raiz != null )
                aplic.getGUI().getPrincipalGUI().getProjetosGUI().addProjeto( no.getNome(), raiz );                         
        }
        aplic.getGUI().getPrincipalGUI().getProjetosGUI().carrega();
    }
    
    public ArqArvNo addProj( Projeto proj ) {
        ArqArvNo raiz = this.geraProjArvore( proj );
=======
    }    
                        
    public void carregaProjs() {                
        for( ProjetoXMLNo no : aplic.getProjetosXMLManager().getProjetos() ) {                                    
            Projeto proj = new Projeto( no );            
            ArvNo raiz = aplic.getArquivoManager().geraArvRaiz( proj );
            if ( raiz != null )
                aplic.getGUI().getPrincipalGUI().getProjetosGUI().addProjeto( no.getNome(), raiz );                                     
        }
        
        aplic.getGUI().getPrincipalGUI().getProjetosGUI().carrega();
        aplic.getProjetosXMLManager().salva( aplic.getConfig().getProjetosXMLConfigCaminho() );
    }
    
    public ArqArvNo addProj( Projeto proj ) {
        ArqArvNo raiz = aplic.getArquivoManager().geraArvRaiz( proj );
>>>>>>> origin/versao-2.2
        if ( raiz != null ) {
            String nome = proj.getNome();
            aplic.getGUI().getPrincipalGUI().getProjetosGUI().addProjeto( nome, raiz );                
            aplic.getGUI().getPrincipalGUI().getProjetosGUI().carrega();
            
<<<<<<< HEAD
            aplic.getProjetosXMLLeitor().getProjetos().add( proj.getXMLNo() );
        }
        return raiz;
    }
    
    public ArqArvNo geraProjArvore( Projeto proj ) {
        ArqArvNo raiz = aplic.getArquivoManager().geraArvRaiz( proj );
        if ( raiz != null ) {            
            return raiz;
        } else {
            aplic.getMSGManager().mostraErro( IDEErroMSGs.ARQUIVO_NAO_ENCONTRADO, proj.getBasedir() ); 
        }
        return null;
    }            
    
=======
            aplic.getProjetosXMLManager().getProjetos().add( proj.getXMLNo() );
        }
        return raiz;
    }
        
>>>>>>> origin/versao-2.2
    public ArvNo getArvNo( String caminho )  {
        ArvGUITO guiTO = aplic.getGUI().getPrincipalGUI().getProjetosGUI().getArvGUITO();
        ArvNo raiz = guiTO.getNoRaiz();
        return this.getArvNo( raiz, caminho );
    }
    
    public ArvNo getArvNo( ArvNo no, String sisarqCaminho ) {
<<<<<<< HEAD
        if ( aplic.getConfig().getComparador().igual(no.getSisArqCaminho(), sisarqCaminho ) )
=======
        if ( aplic.getConfig().getComparador().igual( no.getSisArqCaminho(), sisarqCaminho ) )
>>>>>>> origin/versao-2.2
            return no;
        
        if ( no.isPasta() && !no.isVazio() ) {
            List<ArvNo> filhos = no.getFilhos();
            for( ArvNo filho : filhos ) {
                ArvNo result = this.getArvNo(filho, sisarqCaminho );
                if ( result != null )
                    return result;
            }            
        }
            
        return null;
    }
        
}
