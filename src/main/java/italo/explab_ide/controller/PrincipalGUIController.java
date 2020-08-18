package italo.explab_ide.controller;

import italo.explab_ide.ExpLabIDEAplic;
import italo.explab_ide.gui.principal.PrincipalGUIListener;
import italo.explab_ide.gui.principal.PrincipalGUITO;

public class PrincipalGUIController implements PrincipalGUIListener {

    private final ExpLabIDEAplic aplic;
    
    public PrincipalGUIController( ExpLabIDEAplic aplic ) {
        this.aplic = aplic;
    }
    
    @Override
    public void novoProjetoBTAcionado( PrincipalGUITO guiTO ) {
        aplic.getProjetoOuArquivoControllerCtrl().novoProjetoAcionado();
    }

    @Override
    public void abrirProjetoBTAcionado( PrincipalGUITO guiTO ) {
        aplic.getProjetoOuArquivoControllerCtrl().abrirProjetoAcionado();
    }

    @Override
    public void salvarTudoBTAcionado( PrincipalGUITO guiTO ) {
        aplic.getProjetoOuArquivoControllerCtrl().salvarTudoAcionado( guiTO );
    }

    @Override
    public void executarBTAcionado( PrincipalGUITO guiTO ) {
        aplic.getProjetoOuArquivoControllerCtrl().executarProjetoAcionado();
    }

    @Override
    public void sairBTAcionado(PrincipalGUITO guiTO) {
         aplic.getJanelaControllerCtrl().sairBTAcionado();
    }
        
}
