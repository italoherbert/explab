package italo.explab.gui;

import italo.explab.gui.plot.PlotAplicGUIController;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class ExpLabGUIManager implements ExpLabGUIDriver {
    
    private final PlotAplicGUIController plotAplicGUIController = new PlotAplicGUIController( this );
            
    private List<Image> janelaIcones = null;

    @Override
    public List<Image> getJanelaIcones() {
        if ( janelaIcones == null ) {
            janelaIcones = new ArrayList( 4 );
            try {
                janelaIcones.add( ImageIO.read( ExpLabGUIManager.class.getResourceAsStream( "logo16x16.png" ) ) );
                janelaIcones.add( ImageIO.read( ExpLabGUIManager.class.getResourceAsStream( "logo32x32.png" ) ) );
                janelaIcones.add( ImageIO.read( ExpLabGUIManager.class.getResourceAsStream( "logo64x64.png" ) ) );
                janelaIcones.add( ImageIO.read( ExpLabGUIManager.class.getResourceAsStream( "logo128x128.png" ) ) );
            } catch ( IOException ex ) {

            }
        }
        return janelaIcones;
    }
    
    public PlotAplicGUIController getPlotAplicGUIController() {
        return plotAplicGUIController;
    }
    
}
