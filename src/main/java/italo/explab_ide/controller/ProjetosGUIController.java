package italo.explab_ide.controller;

import italo.explab_ide.ExpLabIDEAplic;
import italo.explab_ide.logica.arquivo.ArqArvNo;
import italo.explab_ide.gui.principal.projetos.ProjetosGUIListener;
import java.awt.event.MouseEvent;
import javax.swing.tree.TreePath;
import libs.gui.arv.ArvGUITO;
import libs.gui.arv.ArvNo;
import italo.explab_ide.IDEErroMSGs;

public class ProjetosGUIController implements ProjetosGUIListener {
    
    private final ExpLabIDEAplic aplic;

    public ProjetosGUIController(ExpLabIDEAplic aplic) {
        this.aplic = aplic;
    }

    @Override
    public void duploClique( ArvNo no, MouseEvent e ) {
        aplic.getCodigoFonteCtrl().carregaArquivoArvNo( no );
    }

    @Override
    public void moveAcionado( ArvGUITO arvGUITO, TreePath[] origs, TreePath dest ) {
        String destCaminho = arvGUITO.getCaminho( dest );
        ArvNo destArvNo = arvGUITO.getNoPorCaminho( destCaminho );        
        String destino = destArvNo.getSisArqCaminho();
        
        for( TreePath orig : origs ) {
            String origCaminho = arvGUITO.getCaminho( orig );
            ArvNo origArvNo = arvGUITO.getNoPorCaminho( origCaminho );            
            String origem = origArvNo.getSisArqCaminho();
            
            if ( destino.startsWith( origem ) ) {
                aplic.getMSGManager().mostraErro(IDEErroMSGs.ARV_PASTA_MOV_INVALIDO );
                return;                
            }                    
        }
        
        for( TreePath orig : origs ) {            
            String origCaminho = arvGUITO.getCaminho( orig );
            ArvNo origArvNo = arvGUITO.getNoPorCaminho( origCaminho );            
            String origem = origArvNo.getSisArqCaminho();
                                                
            aplic.getArquivoManager().move( origem, destino );
                        
            String parenteOrigCaminho = arvGUITO.getCaminho( orig.getParentPath() );
            ArqArvNo parenteOrigArqNo = (ArqArvNo)origArvNo.getParente();
            aplic.getArquivoManager().carregaSubArv( parenteOrigArqNo );
                        
            ArqArvNo destArqNo = (ArqArvNo)destArvNo;
            aplic.getArquivoManager().carregaSubArv( destArqNo );
                                           
            arvGUITO.carregaNo( parenteOrigCaminho, parenteOrigArqNo.getFilhos() );
            arvGUITO.carregaNo( destCaminho, destArqNo.getFilhos() );
            arvGUITO.rolarParaEOuExpande( destCaminho, true, true ); 
        }        
    }

    @Override
    public void deleteAcionado( ArvGUITO arvGUITO ) {
        aplic.getProjetoOuArquivoControllerCtrl().deletarAcionado();
    }
    
}
