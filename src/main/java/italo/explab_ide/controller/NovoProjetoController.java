package italo.explab_ide.controller;

import italo.explab_ide.ExpLabIDEAplic;
import italo.explab_ide.IDEErroMSGs;
import italo.explab_ide.gui.novoprojeto.NovoProjetoGUIListener;
import italo.explab_ide.gui.novoprojeto.NovoProjetoGUITO;
import italo.explab_ide.IDEInfoMSGs;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class NovoProjetoController implements NovoProjetoGUIListener{   
    
    private final ExpLabIDEAplic aplic;
    
    public NovoProjetoController( ExpLabIDEAplic aplic ) {
        this.aplic = aplic;
    }
    
    @Override
    public void criarBTAcionado( NovoProjetoGUITO guiTO ) {
        String nome = guiTO.getProjNome();
        if ( nome.isEmpty() ) {
            aplic.getMSGManager().mostraErro( IDEErroMSGs.PROJETO_NOME_VASIO ); 
            return;
        }
        
        String projPastaCaminho = guiTO.getProjCaminho();
        String okMSGChave = IDEInfoMSGs.PROJETO_CRIADO;
        
        if ( !projPastaCaminho.endsWith( File.separator ) )
            projPastaCaminho += File.separator;
        projPastaCaminho += nome;
        
        aplic.getProjetoCtrl().novoOuAbrir( projPastaCaminho, guiTO, okMSGChave );
    }
    
    @Override
    public void procurarBTAcionado( NovoProjetoGUITO guiTO ) {        
        File basedirFile = new File( guiTO.getProjCaminho() );
        if ( !basedirFile.exists() )
            basedirFile.mkdirs(); 
                
        JFileChooser fc = new JFileChooser(); 
        fc.setFileFilter( new FileFilter() { 
            @Override
            public boolean accept(File f) {   
                return f.isDirectory();                
            }

            @Override
            public String getDescription() {
                return aplic.getMSGManager().getInfo( IDEInfoMSGs.PROJETO_NOVO_ABRIR_FILTRO_DESCRICAO ); 
            }
        } );
        
        fc.setDialogTitle( aplic.getMSGManager().getInfo( IDEInfoMSGs.PROJETO_NOVO_TITULO ) );
        fc.setAcceptAllFileFilterUsed( true );
        fc.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
        fc.setCurrentDirectory( basedirFile );
        int result = fc.showOpenDialog( null );
        if ( result == JFileChooser.APPROVE_OPTION ) {
            guiTO.setProjCaminho( fc.getSelectedFile().getAbsolutePath() ); 
        }
    }
    
}
