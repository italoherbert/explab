package italo.explab_ide.controller;

import italo.explab_ide.ExpLabIDEAplic;
import italo.explab_ide.gui.autocomplete.AutoCompleteGUIListener;
import italo.explab_ide.gui.autocomplete.AutoCompleteGUITO;
import italo.explab_ide.gui.principal.codigofonte.tppainel.CodigoFonteTPPainelGUITO;

public class AutoCompleteController implements AutoCompleteGUIListener {

    private final ExpLabIDEAplic aplic;
    
    public AutoCompleteController( ExpLabIDEAplic aplic ) {
        this.aplic = aplic;
    }
    
    @Override
    public void cliqueForaDaAutoCompleteLista( AutoCompleteGUITO guiTO ) {        
        CodigoFonteTPPainelGUITO cpGUITO = aplic.getGUI().getPrincipalGUI().getCodigoFonteGUI().getSelecionadoTPPainelGUITO();
        cpGUITO.autoCompletarFinalizado();     
    }

    @Override
    public void itemSelecionado( AutoCompleteGUITO guiTO, int i, Object item ) {        
        CodigoFonteTPPainelGUITO cpGUITO = aplic.getGUI().getPrincipalGUI().getCodigoFonteGUI().getSelecionadoTPPainelGUITO();        
        cpGUITO.autoCompletarFinalizado();
        
        int pos = cpGUITO.getCursorPos();
        String doctxt = cpGUITO.getDocText();
        int j = aplic.getCodigoFonteUtil().palavraIni( doctxt, pos );
        
        String valor = String.valueOf( item );
        
        int k = 0;
        int len = valor.length();
        boolean achou = false;
        for( ; !achou && k < len; k++ )
            if ( valor.charAt( k ) == '(' )
                achou = true;
        
        int l = k;
        String novoValor = valor.substring( 0, l ); 
        for( ; l < len; l++ ) {
            char ch = valor.charAt( l );
            if ( ch == ',' || ch == ')' ) {
                if ( ch == ',' )
                    novoValor += ",";
                else novoValor += ")";
            }                                  
        }
        
        cpGUITO.substitui( j, pos, novoValor );        
        if ( achou )
            cpGUITO.setCursorPos( j + k );          
    }
    
}
