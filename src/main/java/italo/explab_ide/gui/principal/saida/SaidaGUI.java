package italo.explab_ide.gui.principal.saida;

import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JPanel;
import libs.gui.tpcombtfechar.TPComBTFechar;
import libs.gui.tpcombtfechar.TPComBTFecharListener;

public class SaidaGUI extends JPanel implements TPComBTFecharListener {
    
    private final TPComBTFechar tp = new TPComBTFechar();        
    
    public SaidaGUI() {
        super.setLayout( new GridLayout() );
        super.add( tp );
        
        tp.setTPListener( this ); 
    }
    
    public SaidaTPPainelGUITO addTabPainel( String tabRotulo ) {
        SaidaTPPainelGUI gui = new SaidaTPPainelGUI( tp, tabRotulo );
        tp.addTabPainel( tabRotulo, gui );                
        return gui.getGUITO();
    }
    
    public void removeTabPainel( SaidaTPPainelGUI pnl ) {
        int indice = tp.indexOfComponent( pnl );                
        tp.removeTabAt( indice ); 
    }

    @Override
    public boolean perguntaSeFechar(int i) {                
        SaidaTPPainelGUI pgui = (SaidaTPPainelGUI)tp.getComponentAt( i );
        return pgui.perguntaSeFechar( i );
    }

    @Override
    public void antesTabRemovida(int i) {
        SaidaTPPainelGUI pgui = (SaidaTPPainelGUI)tp.getComponentAt( i );
        pgui.antesTabRemovida( i );        
    }

    @Override
    public void aposTabRemovida(int i) {
        
    }
    
    public SaidaTPPainelGUITO getSelecionadaSaidaTPPainelGUITO() {
        Component c = tp.getSelectedComponent();
        if ( c != null )
            return ((SaidaTPPainelGUI)c).getGUITO();
        return null;
    }
        
}
