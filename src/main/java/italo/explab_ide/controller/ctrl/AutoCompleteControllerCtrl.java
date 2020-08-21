package italo.explab_ide.controller.ctrl;

import italo.explab.ExpLab;
import italo.explab_ide.ExpLabIDEAplic;
import italo.explab_ide.gui.principal.codigofonte.tppainel.CodigoFonteTPPainelGUITO;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class AutoCompleteControllerCtrl {
    
    private final ExpLabIDEAplic aplic;
    
    public AutoCompleteControllerCtrl( ExpLabIDEAplic aplic ) {
        this.aplic = aplic;
    }
        
    public void execAutoComplete( CodigoFonteTPPainelGUITO guiTO, Point p ) {        
        ExpLab explab = aplic.getCodigoFonteCtrl().getExplab();
        String texto = guiTO.getDocText();
        
        guiTO.autoCompletarIniciado();

        int i = guiTO.getAutoCompleteI() - 1;
        boolean fim = false;
        while( !fim && i >= 0 ) {
            char ch = texto.charAt( i ); 
            if ( Character.isLetterOrDigit( ch ) || ch == '_' ) {
                i--;
            } else {
                fim = true;
            }
        }
        i++;
        
        int j = guiTO.getAutoCompleteI();
        fim = false;
        int len = texto.length();
        while( !fim && j < len ) {
            char ch = texto.charAt( j );
            if ( Character.isLetterOrDigit( ch ) || ch == '_' ) {
                j++;
            } else {
                fim = true;
            }
        }
            
        String termo = texto.substring( i, j );        
        
        List<String> vars = explab.getAplic().getGlobalRecursoManager().getGlobalVarLista().buscaLocalPorIniTermo( termo );
        List<String> funcs = explab.getAplic().getGlobalRecursoManager().getGlobalFuncLista().buscaLocalPorIniTermo( termo );
        List<String> lista = new ArrayList( vars.size()+funcs.size() );
        lista.addAll( funcs );
        lista.addAll( vars );
        
        if ( lista.isEmpty() ) {
            aplic.getGUI().getAutoCompleteGUI().getGUITO().setVisivel( false ); 
            guiTO.autoCompletarFinalizado();
        } else {
            aplic.getGUI().getAutoCompleteGUI().getGUITO().mostrar( p, lista );        
        }
    }
    
}
