package italo.explab.gui.splash;

import javax.swing.JWindow;

public class SplashGUI extends JWindow {
    
    private final SplashPainel splashPainel = new SplashPainel();
    
    public SplashGUI() {
        super.setContentPane( splashPainel );
        super.pack();
        super.setLocationRelativeTo( this ); 
    }
    
    public SplashPainel getSplashPainel() {
        return splashPainel;
    }
    
}
