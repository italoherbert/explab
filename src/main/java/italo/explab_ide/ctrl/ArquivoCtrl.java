package italo.explab_ide.ctrl;

import italo.explab_ide.ExpLabIDEAplic;
import italo.explab_ide.IDEErroMSGs;
import italo.explab_ide.gui.principal.codigofonte.tppainel.CodigoFonteTPPainelGUITO;
import italo.explab_ide.logica.arquivo.ArqArvNo;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import libs.comparador.Comparador;
import libs.gui.arv.ArvGUITO;
import libs.gui.arv.ArvNo;

public class ArquivoCtrl {
    
    private final ExpLabIDEAplic aplic;
            
    public ArquivoCtrl( ExpLabIDEAplic aplic ) {
        this.aplic = aplic;
    }

    public void criaNovoArquivo( ArvGUITO arvGUITO, String arqext ) {
        this.criaNovoArquivoOuPasta( arvGUITO, false, "novoarquivo", arqext ); 
    }
    
    public void criaNovaPasta( ArvGUITO arvGUITO ) {   
        this.criaNovoArquivoOuPasta( arvGUITO, true, "novapasta", null );
    }     
    
    public void criaNovoArquivoOuPasta( ArvGUITO arvGUITO, boolean ehpasta, String rotulo, String arqext ) {        
        Comparador comparador = aplic.getConfig().getComparador();
        String noCaminho = arvGUITO.getCaminhoNoSelecionado();
        ArvNo no = arvGUITO.getNoPorCaminho( noCaminho );
        DefaultMutableTreeNode treeNo = arvGUITO.getTreeNoPorCaminho( noCaminho );
    
        ArvNo pstNo = no;
        DefaultMutableTreeNode pstTreeNo = treeNo;
        if ( !no.isEhPasta() ) {
            pstNo = no.getParente();
            pstTreeNo = (DefaultMutableTreeNode)treeNo.getParent();
        }
        
        final ArvNo pastaNo = pstNo;
        final DefaultMutableTreeNode pastaTreeNo = pstTreeNo;
                
        String pasta = pastaNo.getSisArqCaminho().replace( "\\", "/" );
        if ( !pasta.endsWith( "/" ) )
            pasta += "/";
        
        String ext = ( arqext != null ? arqext : "" );        
        File novoFile = new File( pasta + rotulo+ext );
        try {
            int k = 0;
            boolean fim = pastaNo.getFilhos() == null;
            while ( !fim ) {
                int size = pastaNo.getFilhos().size();
                fim = true;
                for( int i = 0; fim && i < size; i++ ) {
                    String nome = pastaNo.getFilhos().get( i ).getNome();
                    if ( comparador.igual( nome, novoFile.getName() ) )
                        fim = false;
                }
                
                if ( !fim )
                    novoFile = new File( pasta + rotulo+"_"+(++k)+ext );                
            }

            boolean criado;
            if ( ehpasta ) {
                criado = novoFile.mkdir();
            } else {
                criado = novoFile.createNewFile();
            }
            
            if ( criado ) {                
                ArqArvNo novoNo = new ArqArvNo();
                novoNo.setProjeto( ((ArqArvNo)pastaNo).getProjeto() ); 
                novoNo.setEhPastaDeProjeto( false );
                novoNo.setNome( novoFile.getName() );
                novoNo.setSisArqCaminho( novoFile.getAbsolutePath() );
                novoNo.setEhPasta( ehpasta );
                novoNo.setEhVazio( true ); 
                pastaNo.addNoFilho( novoNo ); 
                
                DefaultMutableTreeNode novoTreeNo = arvGUITO.addNo( pastaTreeNo, novoNo );                                
                TreePath treePath = new TreePath( novoTreeNo.getPath() );
                String novoNoCaminho = arvGUITO.getCaminho( treePath );
                
                arvGUITO.rolarParaESelecionar( novoNoCaminho ); 
                
                TreePath pastaTreePath = new TreePath( pastaTreeNo.getPath() );
                String pastaNoCaminho = arvGUITO.getCaminho( pastaTreePath );
                this.renomeiaArqOuPasta( arvGUITO, pastaNoCaminho, novoNoCaminho );                
            } else {
                aplic.getMSGManager().mostraErro( IDEErroMSGs.ARQUIVO_CRIACAO_ERRO, novoFile.getName() );
            }           
        } catch (IOException ex) {
            aplic.getMSGManager().mostraErro( IDEErroMSGs.ARQUIVO_CRIACAO_ERRO, novoFile.getName() );
        }                
    }
    
    public void recarregarPastaSelecionada( ArvGUITO arvGUITO ) {
        String caminho = arvGUITO.getSelecaoCaminho();
        ArvNo no = arvGUITO.getNoPorCaminho( caminho );
        
        aplic.getArquivoManager().carregaSubArv( (ArqArvNo)no ); 
        arvGUITO.carregaNo( caminho, no );
        arvGUITO.rolarParaEOuExpande( caminho, true ); 
    }

    public void renomeiaArqOuPastaSelecionado( ArvGUITO arvGUITO ) {
        String caminho = arvGUITO.getSelecaoCaminho();
        DefaultMutableTreeNode treeNo = arvGUITO.getTreeNoPorCaminho( caminho );
        
        DefaultMutableTreeNode parenteTreeNo = (DefaultMutableTreeNode)treeNo.getParent();
        TreePath parenteTreePath = new TreePath( parenteTreeNo.getPath() );        
        String parenteNoCaminho = arvGUITO.getCaminho(parenteTreePath );
                         
        this.renomeiaArqOuPasta( arvGUITO, parenteNoCaminho, caminho ); 
    }
        
    public void renomeiaArqOuPasta( ArvGUITO arvGUITO, String parenteNoCaminho, String noCaminho ) {                
        aplic.getCodigoFonteCtrl().salvaArquivosModificados();
        
        arvGUITO.rolarParaESelecionar( noCaminho ); 
        arvGUITO.renomeia(noCaminho, (ArvGUITO guiTO, String nomeAntigo, String nomeNovo ) -> {  
            Comparador comparador = aplic.getConfig().getComparador();
            if ( comparador.igual( nomeAntigo, nomeNovo ) )
                return;            
            
            ArvNo arqNo = arvGUITO.getNoPorCaminho( noCaminho );                 
            String url = arqNo.getSisArqCaminho();
            
            File file = new File( arqNo.getSisArqCaminho() );            
            
            String parente = file.getParent().replace( "\\", "/" );                                
            if ( !parente.endsWith( "/" ) )
                parente += "/";
            
            File novoFile = new File( parente + nomeNovo );
                                             
            boolean renomeou = file.renameTo( novoFile );
            if ( renomeou ) {       
                arqNo.setNome( novoFile.getName() );
                arqNo.setSisArqCaminho( novoFile.getAbsolutePath() );
                                
                if ( ((ArqArvNo)arqNo).isPastaDeProjeto() ) {
                    aplic.getProjetosXMLManager().setProjetoNome( nomeAntigo, nomeNovo, comparador );
                    aplic.getProjetosXMLManager().salva( aplic.getConfig().getProjetosXMLConfigCaminho() );               
                    aplic.getProjetosXMLManager().carrega( aplic.getConfig().getProjetosXMLConfigCaminho() );                     
                    ((ArqArvNo)arqNo).setProjeto( aplic.getProjetosXMLManager().getProjeto( nomeNovo, comparador ) ); 
                }
                
                if ( arqNo.isEhPasta() ) {
                    if ( arqNo.getFilhos() != null )
                        arqNo.getFilhos().clear();
                    
                    aplic.getArquivoManager().carregaSubArv( (ArqArvNo)arqNo );
                    
                    String pastaUrl = url;
                    String novaPastaUrl = novoFile.getAbsolutePath();
                    
                    aplic.getCodigoFonteCtrl().setNomePastaParaArquivosAbertos( pastaUrl, novaPastaUrl );
                }
                                                
                ArvNo pastaNo = arvGUITO.getNoPorCaminho( parenteNoCaminho );
                guiTO.carregaNo( parenteNoCaminho, pastaNo );                
                
                String novoNoCaminho = parenteNoCaminho + "/" + nomeNovo;
                
                guiTO.rolarParaEOuExpande( novoNoCaminho, true, true );
            } else {
                aplic.getMSGManager().mostraErro( IDEErroMSGs.ARQUIVO_RENAME_ERRO, nomeNovo ); 
            }
        });
    }
        
    public void removeArquivoOuPasta( ArvGUITO arvGUITO, String caminho, boolean deletar ) {
        aplic.getCodigoFonteCtrl().salvaArquivosModificados();
                
        if ( caminho != null ) {
            ArvNo arvNo = arvGUITO.getNoPorCaminho( caminho );
            DefaultMutableTreeNode treeNo = arvGUITO.getTreeNoPorCaminho( caminho );                        

            if ( deletar )
                aplic.getArquivoManager().delete( arvNo.getSisArqCaminho() );            

            arvGUITO.removeNo( treeNo );        
            arvNo.getParente().getFilhos().remove( arvNo );
            
            ArqArvNo arqNo = (ArqArvNo)arvNo;
            if ( arqNo.isPastaDeProjeto() ) {
                aplic.getProjetosXMLManager().getProjetos().remove( arqNo.getProjeto() );        
                aplic.getProjetosXMLManager().salva( aplic.getConfig().getProjetosXMLConfigCaminho() );
                
                List<CodigoFonteTPPainelGUITO> lista = aplic.getCodigoFonteCtrl().removeArquivosDeProjeto( arvNo.getSisArqCaminho() ); 
                for( CodigoFonteTPPainelGUITO tpTO : lista )                
                    aplic.getGUI().getPrincipalGUI().getCodigoFonteGUI().getGUITO().removeTab( tpTO.getCodigoFonteTPPainelGUI() ); 
            }
        }
    }
    
}
