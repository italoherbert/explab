package italo.explab_cmd.gui.inter;

import java.awt.event.KeyEvent;

public interface InterGUIListener {
    
    public void cmdTeclaPressionada( InterGUITO guiTO, KeyEvent e );
    
    public void cmdMouseClick( InterGUITO guiTO, int x, int y );
    
}
