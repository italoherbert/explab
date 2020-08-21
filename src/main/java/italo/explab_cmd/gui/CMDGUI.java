package italo.explab_cmd.gui;

import italo.explab.gui.ExpLabGUIDriver;
import italo.explab_cmd.gui.inter.InterGUI;

public class CMDGUI {
    
    private final InterGUI interGUI;
            
    public CMDGUI( ExpLabGUIDriver drv ) {
        this.interGUI = new InterGUI( drv );
    }
    
    public InterGUI getInterGUI() {
        return interGUI;
    }
    
}
