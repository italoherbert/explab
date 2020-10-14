package italo.explab_ide.ctrl;

import italo.explab_ide.logica.arquivo.ArqArvNo;
import italo.explab.ExpLab;
import italo.explab.InterException;
import italo.explab_ide.ExpLabIDEAplic;
import italo.explab_ide.gui.IDEGUI;
import italo.explab_ide.gui.principal.codigofonte.tppainel.CodigoFonteTPPainelGUITO;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import libs.gui.arv.ArvNo;
import italo.explab_ide.IDEErroMSGs;
import italo.explab_ide.gui.principal.codigofonte.CodigoFonteGUITO;
import italo.explab_ide.logica.arquivo.projeto.ProjetoConfig;
import italo.explab_ide.logica.arquivo.projeto.ProjetoXMLNo;
import italo.explab_ide.logica.arquivo.recursos_abertos.codigosfonte.ArquivoAberto;
import italo.explab_ide.logica.arquivo.recursos_abertos.codigosfonte.ArquivosAbertos;
import italo.explab_ide.logica.format.CodigoFonteDocumento;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import libs.gui.arv.ArvGUITO;

public class CodigoFonteCtrl {
 
    private final ExpLabIDEAplic aplic;
    private final ExpLab explab = new ExpLab();
    
    private Map<String, CodigoFonteTPPainelGUITO> tpPainelGUITOs = new HashMap();

    public CodigoFonteCtrl( ExpLabIDEAplic aplic, IDEGUI gui ) {
        this.aplic = aplic;                        
        
        try {
            explab.inicializa();
        } catch (InterException ex) {
            Logger.getLogger(CodigoFonteCtrl.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }   
    
    public void setNomePastaParaArquivosAbertos( String pastaUrl, String novaPastaUrl ) {        
        Map<String, CodigoFonteTPPainelGUITO> copia = tpPainelGUITOs;        
        tpPainelGUITOs = new HashMap();

        String purl = pastaUrl.replace( "\\", "/" );
        if ( !purl.endsWith( "/" ) )
            purl += "/"; 
        
        String novaPUrl = novaPastaUrl.replace( "\\", "/" );
        if ( !novaPUrl.endsWith( "/" ) )
            novaPUrl += "/";
        
        Set<String> keys = copia.keySet();
        for( String sisarq : keys ) {
            String caminho = sisarq.replace( "\\", "/" );            
            if ( caminho.startsWith( purl ) )
                caminho = caminho.replace( purl, novaPUrl );            
            
            tpPainelGUITOs.put( caminho, copia.get( sisarq ) );
        }
    }
    
    public void removeTodasAsTabs( CodigoFonteGUITO guiTO ) {        
        tpPainelGUITOs.clear();        
        guiTO.removeTodasAsTabs();
    }
    
    public void removeOutrasTabs( CodigoFonteGUITO guiTO, int i ) {
        CodigoFonteTPPainelGUITO tpPainelGUITO = guiTO.getTPPainelGUITO( i );        

        Set<String> keys = new HashSet( tpPainelGUITOs.keySet() );
        for( String sisarqCaminho : keys ) {
            Object valor = tpPainelGUITOs.get( sisarqCaminho );
            if ( !valor.equals( tpPainelGUITO ) )
                this.removeTPPainelGUITO( sisarqCaminho );                        
        }
        
        guiTO.removeOutrasTabs( i );
    }       
                               
    public boolean removeEstaTab( CodigoFonteGUITO guiTO, int i ) {
        CodigoFonteTPPainelGUITO tpPainelGUITO = guiTO.getTPPainelGUITO( i );        
        
        boolean removeu = this.removeTPPainelGUITO( tpPainelGUITO );
        if ( removeu )
            guiTO.removeEstaTab( i );
       
        return removeu;
    }

    public boolean removeTPPainelGUITO( CodigoFonteTPPainelGUITO tpPainelGUITO ) {                       
        Set<String> keys = tpPainelGUITOs.keySet();
        for( String sisarqCaminho : keys ) {
            Object valor = tpPainelGUITOs.get( sisarqCaminho );
            if ( valor.equals( tpPainelGUITO ) ) {
                tpPainelGUITOs.remove( sisarqCaminho );
                return true;
            }
        }        
        return false;
    }
        
    public CodigoFonteTPPainelGUITO removeTPPainelGUITO( String sisarqCaminho ) {                       
        Iterator<String> keysIT = tpPainelGUITOs.keySet().iterator();
        while( keysIT.hasNext() ) {
            String sisarqCaminho2 = keysIT.next();
            if ( sisarqCaminho.equals( sisarqCaminho2 ) ) {
                return tpPainelGUITOs.remove( sisarqCaminho2 );             
            }
        }
        return null;               
    }        
    
    public String mouseSobreTabTexto( CodigoFonteTPPainelGUITO tpPainelGUITO, int i ) {
        Set<String> keys = tpPainelGUITOs.keySet();
        for( String sisarqCaminho : keys ) {
            Object valor = tpPainelGUITOs.get( sisarqCaminho );
            if ( valor.equals( tpPainelGUITO ) )
                return sisarqCaminho;            
        }        
        return null;
    }
    
    public List<CodigoFonteTPPainelGUITO> removeArquivosDeProjeto( String projetoSysArqCaminho ) {
        Iterator<String> keysIT = tpPainelGUITOs.keySet().iterator();
        List<String> removerList = new ArrayList();
        while( keysIT.hasNext() ) {
            String sisarqCaminho2 = keysIT.next();
            if ( sisarqCaminho2.startsWith( projetoSysArqCaminho ) )
                removerList.add( sisarqCaminho2 );
        }
        
        List<CodigoFonteTPPainelGUITO> lista = new ArrayList();
        for( String key : removerList )
            lista.add( tpPainelGUITOs.remove( key ) );
            
        return lista;
    }
    
    public CodigoFonteTPPainelGUITO getTPPainelGUITO( String sisarqCaminho ) {
        Iterator<String> keysIT = tpPainelGUITOs.keySet().iterator();
        while( keysIT.hasNext() ) {
            String sisarqCaminho2 = keysIT.next();
            if ( sisarqCaminho.equals( sisarqCaminho2 ) )
                return tpPainelGUITOs.get( sisarqCaminho2 );             
        }
        return null; 
    }
    
    public String getSisArqPorTPPainelGUITO( CodigoFonteTPPainelGUITO guiTO ) {
        Iterator<String> keysIT = tpPainelGUITOs.keySet().iterator();
        while( keysIT.hasNext() ) {
            String sisarqCaminho = keysIT.next();
            CodigoFonteTPPainelGUITO valor = tpPainelGUITOs.get( sisarqCaminho );
            if ( valor.equals( guiTO ) ) 
                return sisarqCaminho;             
        }
        return null; 
    }
                        
    public boolean temArquivoNaoSalvo() {
        Set<String> keys = tpPainelGUITOs.keySet();
        for( String sisarqCaminho : keys ) {
            CodigoFonteTPPainelGUITO guiTO = tpPainelGUITOs.get( sisarqCaminho );
            if ( guiTO.isAlterado() )
                return true;
        }
        return false;
    }
    
    public void salvaArquivosModificados() {
        Set<String> keys = tpPainelGUITOs.keySet();
        for( String sisarqCaminho : keys ) {
            CodigoFonteTPPainelGUITO cfGUITO = tpPainelGUITOs.get( sisarqCaminho );
            if ( cfGUITO.isAlterado() ) {
                try {   
                    String charset = this.leProjCharset( sisarqCaminho );
                                            
                    File file = new File( sisarqCaminho );
                    if ( !file.exists() )
                        file.createNewFile();

                    try ( PrintWriter pw = new PrintWriter( file, charset ) ) {
                        pw.print( cfGUITO.getDocText() );
                    }

                    cfGUITO.arquivoSalvoEAtualizado();                    
                } catch (IOException ex) {
                    aplic.getMSGManager().mostraErro( "arquivo.nao.encontrado", sisarqCaminho ); 
                }                
            }
        }
        aplic.getGUI().getPrincipalGUI().salvoIcone();
    }
    
    public void formataArquivoModificado( CodigoFonteTPPainelGUITO guiTO ) {
        String doctexto = guiTO.getDocText();        
        
        guiTO.novoStyledDocument();        
        CodigoFonteDocumento doc = ( texto, attrSet ) -> {
            guiTO.docAppend( texto, attrSet );            
        };
        
        aplic.getCodigoFonteManager().formata( explab, doc, doctexto );
    }

    public void carregaArquivoArvNo( ArvNo no ) {
        if ( no.isPasta() )
            return;
        
        this.carregaArquivo( no.getSisArqCaminho(), no.getNome() );
    }
    
    public CodigoFonteTPPainelGUITO carregaArquivo( String sisarqCaminho, String rotulo ) {                               
        CodigoFonteTPPainelGUITO guiTO = tpPainelGUITOs.get( sisarqCaminho );
        if ( guiTO != null ) {
            guiTO.selecionaTab();
            return guiTO;
        }                        

        File file = new File( sisarqCaminho );
        if ( file.exists() ) {
            try {                    
                String charset = this.leProjCharset( sisarqCaminho );                
                String codigo = aplic.getArquivoUtil().ler( file.getAbsolutePath(), charset );                
                    
                guiTO = aplic.getGUI().getPrincipalGUI().getCodigoFonteGUI().addTabPainel( rotulo );                    
                
                guiTO.novoStyledDocument(); 
                final CodigoFonteTPPainelGUITO gto = guiTO;
                CodigoFonteDocumento doc = ( texto, attrSet ) -> {
                    gto.docAppend( texto, attrSet );            
                };
                
                aplic.getCodigoFonteManager().formata( explab, doc, codigo );
                guiTO.salva();
                
                tpPainelGUITOs.put( sisarqCaminho, guiTO );                
            } catch ( IOException ex ) {
                aplic.getMSGManager().mostraErro( IDEErroMSGs.ARQUIVO_IO_ERRO, sisarqCaminho ); 
            }            
        } else {
            aplic.getMSGManager().mostraErro( IDEErroMSGs.ARQUIVO_NAO_ENCONTRADO, sisarqCaminho );             
        }        
        return guiTO;
    }
    
    public void recuperaRecursosAbertos( ArvGUITO arvGUITO ) {                
        try {
            ArquivosAbertos arquivosAbertos = aplic.getRecursosAbertosManager().recuperaDadosArquivosAbertos();
            List<ArquivoAberto> arquivos = arquivosAbertos.getArquivos();
                    
            for( ArquivoAberto arquivo : arquivos ) {
                String arq = arquivo.getCaminho();
                File file = new File( arq );
                if ( file.exists() ) {
                    CodigoFonteTPPainelGUITO to = this.carregaArquivo( file.getAbsolutePath(), file.getName() );
                    to.setCursorPos( arquivo.getCursorPos() );                                                                                   
                }     
            }
            
            aplic.getGUI().getPrincipalGUI().getCodigoFonteGUI().getGUITO().setSelecionaTab( arquivosAbertos.getArquivoFocadoI() );                         
            
            List<String> nosCaminhosParaExpandir = aplic.getRecursosAbertosManager().recuperaNosCaminhosParaExpandir();
            for( String caminho : nosCaminhosParaExpandir )
                arvGUITO.rolarParaEOuExpande( caminho, false, true );
            
            String noSelecionadoCaminho = aplic.getRecursosAbertosManager().recuperaNoSelecionadoCaminho();
            if ( noSelecionadoCaminho != null ) {
                if ( arvGUITO.getNoPorCaminho( noSelecionadoCaminho ) != null )
                    arvGUITO.rolarParaESelecionar( noSelecionadoCaminho );                       
            }
        } catch (IOException ex) {
            Logger.getLogger(CodigoFonteCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }                                                
    }
    
    public void salvaRecursosAbertos( ArvGUITO arvGUITO ) {
        List<String> nosParaExpandir = new ArrayList();
        DefaultMutableTreeNode raizNode = arvGUITO.getTreeNoRaiz();
        this.carregaNosParaExpandir( arvGUITO, nosParaExpandir, raizNode );
        
        ArquivosAbertos arquivosAbertos = this.carregaArquivosAbertos();
        try {
            aplic.getRecursosAbertosManager().salvaDadosArquivosAbertos( arquivosAbertos );
        } catch (IOException ex) {
            
        }
        
        try {
            aplic.getRecursosAbertosManager().salvaNosCaminhosParaExpandir( nosParaExpandir );
        } catch (IOException ex) {
            
        }

        try {
            String noSelecionadoCaminho = arvGUITO.getSelecaoCaminho();
            if ( noSelecionadoCaminho != null )
                aplic.getRecursosAbertosManager().salvaNoSelecionadoCaminho( noSelecionadoCaminho );
        } catch (IOException ex) {
            
        }        
    }
            
    private ArquivosAbertos carregaArquivosAbertos() {
        CodigoFonteTPPainelGUITO selectGUITO = aplic.getGUI().getPrincipalGUI().getCodigoFonteGUI().getGUITO().getSelecionadoTPPainelGUITO();
        
        List<ArquivoAberto> arquivos = new ArrayList();
        
        int arqSelecionadoI = -1;
        Set<String> sisarqCaminhos = tpPainelGUITOs.keySet();
        int i = 0;
        for( String sisarqCaminho : sisarqCaminhos ) {
            CodigoFonteTPPainelGUITO to = tpPainelGUITOs.get( sisarqCaminho );
            int cursorPos = to.getCursorPos();
            arquivos.add( new ArquivoAberto( sisarqCaminho, cursorPos ) );
            
            if ( to.equals( selectGUITO ) )
                arqSelecionadoI = i;            
            
            i++;
        }
                        
        return new ArquivosAbertos( arquivos, arqSelecionadoI );
    }
    
    private void carregaNosParaExpandir( ArvGUITO arvGUITO, List<String> nosParaExpandir, DefaultMutableTreeNode node ) {
        JTree tree = arvGUITO.getJTree();
        TreePath treePath = new TreePath( node.getPath() );
        if ( tree.isExpanded( treePath ) ) 
            nosParaExpandir.add( arvGUITO.getCaminho( treePath ) );
        
        if ( node.getChildCount() > 0 ) {
            Enumeration<TreeNode> filhosIT = node.children();
            
            while( filhosIT.hasMoreElements() )
                this.carregaNosParaExpandir( arvGUITO, nosParaExpandir, (DefaultMutableTreeNode)filhosIT.nextElement() );
        }
    }

             
    private String leProjCharset( String sisarqCaminho ) {
        String charset = aplic.getConfig().getProjetoCharsetPadrao();
        ArvNo no = aplic.getProjetoCtrl().getArvNo( sisarqCaminho );
        if ( no != null ) {
            ProjetoXMLNo proj = ((ArqArvNo)no).getProjeto();
            ProjetoConfig config = aplic.getProjetoConfigLeitor().le( proj );
            return config.getCharset();
        }
        return charset;
    }
    
    public ExpLab getExplab() {
        return explab;
    }
                
}
