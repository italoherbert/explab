package italo.explab_ide.controller;

import italo.explab_ide.ExpLabIDEAplic;
import italo.explab_ide.IDEInfoMSGs;
import italo.explab_ide.gui.principal.codigofonte.tppainel.CodigoFonteTPPainelGUITO;
import italo.explab_ide.gui.principal.codigofonte.CodigoFonteGUIListener;
import italo.explab_ide.gui.principal.codigofonte.CodigoFonteGUITO;
import java.awt.Point;
import libs.gui.msg.MSGGUI;

public class CodigoFonteGUIController implements CodigoFonteGUIListener {
    
    private final ExpLabIDEAplic aplic;

    public CodigoFonteGUIController(ExpLabIDEAplic aplic) {
        this.aplic = aplic;
    }

    @Override
    public boolean verificaSeFechar( CodigoFonteTPPainelGUITO guiTO ) {
        if ( guiTO.isAlterado() ) {
            int result = aplic.getMSGManager().mostraPergunta( IDEInfoMSGs.PERGUNTA_FECHAR_ARQ_SEM_SALVAR );
            return ( result == MSGGUI.SIM );            
        }
        return true;
    }
    
    @Override
    public void teclaDigitada( CodigoFonteTPPainelGUITO guiTO, Point p ) {
        if ( guiTO.isAutoCompleteExecutando() ) {
            aplic.getAutoCompleteControllerCtrl().execAutoComplete( guiTO, p ); 
        } else {
            aplic.getCodigoFonteCtrl().formataArquivoModificado( guiTO ); 
            aplic.getGUI().getPrincipalGUI().getGUITO().salvarIcone();
        }
    }

    @Override
    public void antesTabRemovida( CodigoFonteTPPainelGUITO guiTO, int i ) {
        aplic.getCodigoFonteCtrl().removeTPPainelGUITO( guiTO );
    }   

    @Override
    public void removeTodasAsTabs( CodigoFonteGUITO guiTO, int i ) {
        aplic.getCodigoFonteCtrl().removeTodosTPPainelGUITO();        
        guiTO.removeTodasAsTabs();
    }

    @Override
    public void removeOutrasTabs( CodigoFonteGUITO guiTO, int i ) {
        CodigoFonteTPPainelGUITO tpPainelGUITO = guiTO.getTPPainelGUITO( i );
        
        aplic.getCodigoFonteCtrl().removeOutrosTPPainelGUITO( tpPainelGUITO );
        guiTO.removeOutrasTabs( i );
    }

    @Override
    public void removeEstaTab( CodigoFonteGUITO guiTO, int i ) {
        CodigoFonteTPPainelGUITO tpPainelGUITO = guiTO.getTPPainelGUITO( i );
        
        aplic.getCodigoFonteCtrl().removeTPPainelGUITO( tpPainelGUITO );
        guiTO.removeEstaTab( i );
    }

}
