package italo.explab.gui.plot;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import italo.explab.gui.ExpLabGUIDriver;

public class Plot3DAplicGUI extends JDialog {
        
    public Plot3DAplicGUI( ExpLabGUIDriver drv ) {
        super.setIconImages( drv.getJanelaIcones() ); 
        super.setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );        
    }

    public void setDesenhoComponent( JComponent component ) {
        super.setContentPane( component );
    }
    
}

