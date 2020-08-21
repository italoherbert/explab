package italo.explab_ide.gui.principal.codigofonte;

import italo.explab_ide.gui.principal.codigofonte.tppainel.CodigoFonteTPPainelGUITO;

public class CodigoFonteGUITO {

    private final CodigoFonteGUI gui;
    private int ultimoTabClicadoI = -1;
    
    public CodigoFonteGUITO( CodigoFonteGUI gui ) {
        this.gui = gui;
    }           
    
    public void removeTodasAsTabs() {
        int len = gui.getTP().getTabCount();
        for( int i = 0; i < len; i++ )
            gui.getTP().removeTabAt( 0 ); 
    }
    
    public void removeOutrasTabs( int indice ) {
        int len = gui.getTP().getTabCount();
        for( int i = 0; i < indice; i++ )
            gui.getTP().removeTabAt( 0 );
        for( int i = indice+1; i < len; i++ )
            gui.getTP().removeTabAt( 1 );          
    }
    
    public boolean removeEstaTab( int indice ) {
        if ( indice >= 0 && indice < gui.getTP().getTabCount() ) {
            gui.getTP().removeTabAt( indice ); 
            return true;
        }
        return false;
    }
    
    public void setSelecionaTab( int i ) {
        gui.getTP().setSelectedIndex( i );
    }
    
    public CodigoFonteTPPainelGUITO getTPPainelGUITO( int i ) {
        return gui.getTPPainelGUITO( i );
    }
    
    public CodigoFonteTPPainelGUITO getSelecionadoTPPainelGUITO() {        
        return gui.getSelecionadoTPPainelGUITO();
    }

    public int getUltimoTabClicadoI() {
        return ultimoTabClicadoI;
    }

    public void setUltimoTabClicadoI(int ultimoTabClicadoI) {
        this.ultimoTabClicadoI = ultimoTabClicadoI;
    }
    
}
