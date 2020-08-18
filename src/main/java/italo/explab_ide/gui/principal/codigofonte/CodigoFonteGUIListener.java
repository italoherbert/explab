package italo.explab_ide.gui.principal.codigofonte;

import italo.explab_ide.gui.principal.codigofonte.tppainel.CodigoFonteTPPainelGUIListener;
import italo.explab_ide.gui.principal.codigofonte.tppainel.CodigoFonteTPPainelGUITO;

public interface CodigoFonteGUIListener extends CodigoFonteTPPainelGUIListener {
    
    public boolean verificaSeFechar( CodigoFonteTPPainelGUITO guiTO );
        
    public void antesTabRemovida( CodigoFonteTPPainelGUITO guiTO, int i );        
    
    public void removeTodasAsTabs( CodigoFonteGUITO guiTO, int i );

    public void removeOutrasTabs( CodigoFonteGUITO guiTO, int i );
    
    public void removeEstaTab( CodigoFonteGUITO guiTO, int i );
    
}
