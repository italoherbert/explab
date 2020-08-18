package italo.explab_ide.ctrl;

import italo.explab_ide.ExpLabIDEAplic;
import italo.explab_ide.IDEErroMSGs;
import italo.explab_ide.logica.arquivo.ArqArvNo;
import java.io.File;
import java.io.IOException;
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
        if ( !no.isPasta() ) {
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
                
                arvGUITO.scrollParaESelecionar( novoNoCaminho ); 
                
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
        
        DefaultMutableTreeNode pastaTreeNo = (DefaultMutableTreeNode)treeNo.getParent();
        TreePath pastaTreePath = new TreePath( pastaTreeNo.getPath() );        
        String pastaNoCaminho = arvGUITO.getCaminho( pastaTreePath );
        
        this.renomeiaArqOuPasta( arvGUITO, pastaNoCaminho, caminho ); 
    }
        
    public void renomeiaArqOuPasta( ArvGUITO arvGUITO, String pastaNoCaminho, String noCaminho ) {                
        arvGUITO.scrollParaESelecionar( noCaminho ); 
        arvGUITO.renomeia( noCaminho, (ArvGUITO guiTO, String nomeAntigo, String nomeNovo ) -> {  
            Comparador comparador = aplic.getConfig().getComparador();
            if ( comparador.igual( nomeAntigo, nomeNovo ) )
                return;            
            
            ArvNo arqNo = arvGUITO.getNoPorCaminho( noCaminho );     
            
            File file = new File( arqNo.getSisArqCaminho() );            
            String parente = file.getParent().replace( "\\", "/" );                    
            
            if ( !parente.endsWith( "/" ) )
                parente += "/";
            
            File novoFile = new File( parente + nomeNovo );
                        
            boolean renomeou = file.renameTo( novoFile );
            if ( renomeou ) {       
                arqNo.setNome( novoFile.getName() );
                arqNo.setSisArqCaminho( novoFile.getAbsolutePath() );

                ArvNo pastaNo = arvGUITO.getNoPorCaminho( pastaNoCaminho );
                guiTO.carregaNo( pastaNoCaminho, pastaNo );
            } else {
                aplic.getMSGManager().mostraErro( IDEErroMSGs.ARQUIVO_RENAME_ERRO, nomeNovo ); 
            }
        });
    }
        
    public void removeArquivoOuPasta( ArvGUITO arvGUITO, String caminho, boolean deletar ) {
       if ( caminho != null ) {
            ArvNo arvNo = arvGUITO.getNoPorCaminho( caminho );
            DefaultMutableTreeNode treeNo = arvGUITO.getTreeNoPorCaminho( caminho );                        

            if ( deletar )
                aplic.getArquivoManager().delete( arvNo.getSisArqCaminho() );            

            arvGUITO.removeNo( treeNo );        
            arvNo.getParente().getFilhos().remove( arvNo );
            
            ArqArvNo arqNo = (ArqArvNo)arvNo;
            if ( arqNo.isPastaDeProjeto() ) {
                aplic.getProjetosXMLLeitor().getProjetos().remove( arqNo.getProjeto().getXMLNo() );        
                aplic.getProjetoCtrl().atualizaConfigXMLArquivo();                
            }
        }
    }
    
}
