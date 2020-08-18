package italo.explab_ide.controller.ctrl;

import italo.explab_ide.ExpLabIDEAplic;
import italo.explab_ide.IDEErroMSGs;
import italo.explab_ide.IDEInfoMSGs;
import italo.explab_ide.config.IDEConfig;
import italo.explab_ide.gui.GUIVisivel;
import italo.explab_ide.gui.deletarprojeto.DeletarProjetoPNL;
import italo.explab_ide.gui.principal.PrincipalGUITO;
import italo.explab_ide.gui.principal.codigofonte.tppainel.CodigoFonteTPPainelGUITO;
import italo.explab_ide.logica.arquivo.ArqArvNo;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.TreePath;
import libs.comparador.Comparador;
import libs.gui.arv.ArvGUITO;
import libs.gui.msg.MSGGUI;

public class ProjetoOuArquivoControllerCtrl {
    
    private final ExpLabIDEAplic aplic;

    public ProjetoOuArquivoControllerCtrl( ExpLabIDEAplic aplic ) {
        this.aplic = aplic;
    }    
    
    public void novoArqELAcionado() {
        String arqext = aplic.getConfig().getScriptELExt();
        
        ArvGUITO arvGUITO = aplic.getGUI().getPrincipalGUI().getProjetosGUI().getArvGUITO();
        aplic.getArquivoCtrl().criaNovoArquivo( arvGUITO, arqext );
    }
    
    public void novoArquivoVasioAcionado() {
        ArvGUITO arvGUITO = aplic.getGUI().getPrincipalGUI().getProjetosGUI().getArvGUITO();
        aplic.getArquivoCtrl().criaNovoArquivo( arvGUITO, null );
    }
    
    public void novaPastaAcionado() {
        ArvGUITO arvGUITO = aplic.getGUI().getPrincipalGUI().getProjetosGUI().getArvGUITO();
        aplic.getArquivoCtrl().criaNovaPasta( arvGUITO );
    }
    
    public void renomearAcionado() {
        ArvGUITO arvGUITO = aplic.getGUI().getPrincipalGUI().getProjetosGUI().getArvGUITO();
        aplic.getArquivoCtrl().renomeiaArqOuPastaSelecionado( arvGUITO );
    }
    
    public void recarregarAcionado() {
        ArvGUITO arvGUITO = aplic.getGUI().getPrincipalGUI().getProjetosGUI().getArvGUITO();
        aplic.getArquivoCtrl().recarregarPastaSelecionada( arvGUITO ); 
    }
    
    public void executarProjetoAcionado() {
        aplic.getExecCtrl().executaProjeto();
    }
    
    public void executarScriptAcionado() {
        aplic.getExecCtrl().executaArquivo();
    }
    
    public void salvarTudoAcionado( PrincipalGUITO guiTO) {
        aplic.getCodigoFonteCtrl().salvaArquivosModificados();
        guiTO.salvoIcone();
    }
    
    public void novoProjetoAcionado() {
        IDEConfig config = aplic.getConfig();
        
        File file = new File( config.getProjetoBaseDirPadrao() );
        if ( !file.exists() )
            file.mkdirs();        
         
        aplic.getGUI().getNovoProjetoGUI().getGUITO().setProjCaminho( file.getAbsolutePath() );
        aplic.getGUI().getNovoProjetoGUI().setVisible( true );
    }
    
    public void abrirProjetoAcionado() {
        IDEConfig config = aplic.getConfig();
        Comparador comparador = config.getComparador();
        
        File basedirFile = new File( config.getProjetoBaseDirPadrao() );
        if ( !basedirFile.exists() )
            basedirFile.mkdirs(); 
        
        String projArqPadrao = aplic.getConfig().getProjetoConfigArqPadrao();
        String _ext = null;
        int i = projArqPadrao.lastIndexOf( "." );
        if ( i != -1 )
            _ext = projArqPadrao.substring( i );
        
        final String ext = _ext;
        
        JFileChooser fc = new JFileChooser(); 
        fc.setFileFilter( new FileFilter() { 
            @Override
            public boolean accept(File f) {   
                if ( ext != null )
                    return( f.isDirectory() || f.getName().endsWith( ext ) );                
                return true;
            }

            @Override
            public String getDescription() {
                return aplic.getMSGManager().getInfo( IDEInfoMSGs.PROJETO_ABRIR_FILTRO_DESCRICAO ); 
            }
        } );
        
        fc.setDialogTitle( aplic.getMSGManager().getInfo( IDEInfoMSGs.PROJETO_ABRIR_TITULO ) );
        fc.setAcceptAllFileFilterUsed( true );
        fc.setFileSelectionMode( JFileChooser.FILES_AND_DIRECTORIES );
        fc.setCurrentDirectory( new File( aplic.getConfig().getProjetoBaseDirPadrao() ) );
        int result = fc.showOpenDialog( null );
        if ( result == JFileChooser.APPROVE_OPTION ) {
            String projCFGArqPadrao = aplic.getConfig().getProjetoConfigArqPadrao();
            
            String projPastaCaminho = null;
            
            File file = fc.getSelectedFile();
            if ( file.isDirectory() ) {
                File[] files = file.listFiles();
                for( int j = 0; projPastaCaminho == null && j < files.length; j++ )
                    if ( comparador.igual( files[j].getName(), projCFGArqPadrao ) )
                        projPastaCaminho = file.getAbsolutePath();
            } else if ( comparador.igual( file.getName(), projCFGArqPadrao ) ) {     
                projPastaCaminho = file.getParent();
            }
                
            if ( projPastaCaminho != null ) {                                
                String okMSGChave = IDEInfoMSGs.PROJETO_ABERTO;
                
                GUIVisivel guivisivel = ( v ) -> {};
                
                aplic.getProjetoCtrl().novoOuAbrir( projPastaCaminho, guivisivel, okMSGChave ); 
            } else {
                aplic.getMSGManager().mostraErro( IDEErroMSGs.ARQ_DE_PROJ_NAO_ENCONTRADO ); 
            }
        }
    }
    
    public void deletarAcionado() {
        ArvGUITO arvGUITO = aplic.getGUI().getPrincipalGUI().getProjetosGUI().getArvGUITO();
        
        List<String> arquivosOuPastasCaminhos = new ArrayList( 1 );
        List<String> projetosCaminhos = new ArrayList( 0 );
                
        StringBuilder projetosNomes = new StringBuilder();
        
        TreePath[] paths = arvGUITO.getJTree().getSelectionPaths();
        for( TreePath path : paths ) {
            String caminho = arvGUITO.getCaminho( path );
            ArqArvNo arqNo = (ArqArvNo)arvGUITO.getNoPorCaminho( caminho );
            if ( arqNo.isPastaDeProjeto() ) {
                projetosNomes.append( "\n\t " );
                projetosNomes.append( arqNo.getNome() );
                projetosCaminhos.add( caminho );
            } else {
                arquivosOuPastasCaminhos.add( caminho );            
            }
        }
        
        if ( !arquivosOuPastasCaminhos.isEmpty() ) {            
            Collections.sort( arquivosOuPastasCaminhos, (String o1, String o2 ) -> {
                int l1 = o1.length();
                int l2 = o2.length();
                if ( l1 < l2 ) 
                    return 1;
                if ( l1 > l2 )
                    return -1;
                return 0;
            });
                        
            int result = aplic.getMSGManager().mostraPergunta( IDEInfoMSGs.PERGUNTA_DELETAR_ARQUIVOS );
            if ( result == MSGGUI.SIM ) {                                
                for( String caminho : arquivosOuPastasCaminhos ) {
                    ArqArvNo arqNo = (ArqArvNo)arvGUITO.getNoPorCaminho( caminho );                        
                    CodigoFonteTPPainelGUITO guiTO = aplic.getCodigoFonteCtrl().getTPPainelGUITO( arqNo.getSisArqCaminho() ); 
                    if ( guiTO != null )
                        guiTO.removeTab();
                    
                    aplic.getArquivoCtrl().removeArquivoOuPasta( arvGUITO, caminho, true );                                                                           
                }
            }
        }
        
        if ( !projetosCaminhos.isEmpty() ) {                                            
            DeletarProjetoPNL pnl = aplic.getGUI().criaDeletarProjetoPNL();
            
            String pergunta = aplic.getMSGManager().getInfo( IDEInfoMSGs.PERGUNTA_DELETAR_PROJETOS ).replace( "%1", projetosNomes.toString() ); 
            
            pnl.getGUITO().setMensagem( pergunta );
            pnl.getGUITO().setRemoverProjArqsRotulo( aplic.getMSGManager().getInfo( IDEInfoMSGs.PERGUNTA_DELETAR_PROJETOS_ARQS ) ); 
            int result = aplic.getMSGManager().mostraPergunta( pnl );
            if ( result == MSGGUI.SIM ) {
                boolean removerArqs = pnl.getGUITO().isRemoverProjArqsSelecionado();
                for( String caminho : projetosCaminhos ) {
                    if ( removerArqs ) {                    
                        aplic.getArquivoCtrl().removeArquivoOuPasta( arvGUITO, caminho, true );
                    } else {
                        aplic.getArquivoCtrl().removeArquivoOuPasta( arvGUITO, caminho, false );
                    }
                }
            }
        }
    }
    
}
