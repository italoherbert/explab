package italo.explab_ide.controller.ctrl;

import italo.explab_ide.ExpLabIDEAplic;
import italo.explab_ide.IDEInfoMSGs;
import libs.gui.arv.ArvGUITO;
import libs.gui.msg.MSGGUI;

public class JanelaControllerCtrl {
    
    private final ExpLabIDEAplic aplic;

    public JanelaControllerCtrl( ExpLabIDEAplic aplic ) {
        this.aplic = aplic;
    }    
    
    public void sairBTAcionado() {                
        if ( aplic.getCodigoFonteCtrl().temArquivoNaoSalvo() ) {
            int result = aplic.getMSGManager().mostraPergunta( IDEInfoMSGs.PERGUNTA_FECHAR_SEM_SALVAR );
            if ( result == MSGGUI.SIM )
                aplic.getCodigoFonteCtrl().salvaArquivosModificados();            
        }
        
        ArvGUITO arvGUITO = aplic.getGUI().getPrincipalGUI().getProjetosGUI().getArvGUITO();
        aplic.getCodigoFonteCtrl().salvaRecursosAbertos( arvGUITO );
    }
        
}
