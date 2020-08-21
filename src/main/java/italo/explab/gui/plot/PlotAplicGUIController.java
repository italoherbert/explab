package italo.explab.gui.plot;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import italo.explab.gui.ExpLabGUIDriver;

public class PlotAplicGUIController {
                
    private final List<Plot2DAplicGUI> plot2DAplicGUIs = new ArrayList();
    private final List<Plot3DAplicGUI> plot3DAplicGUIs = new ArrayList();
    
    private final ExpLabGUIDriver drv;
    
    public PlotAplicGUIController( ExpLabGUIDriver drv ) {
        this.drv = drv;
    }
        
    public void setSizeELocation( Plot2DAplicGUI gui, int largura, int altura ) {
        boolean umaVisivel = false;
        int size = plot2DAplicGUIs.size();
        for( int i = 0; !umaVisivel && i < size; i++ ) {
            Plot2DAplicGUI gui2d = plot2DAplicGUIs.get( i );
            if ( gui2d.isVisible() )
                umaVisivel = true;            
        }
        
        if ( umaVisivel ) {
            Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
            int xw = (int)tela.getWidth() - largura;
            int yh = (int)tela.getHeight() - altura;

            int x = (int)( Math.random() * xw );
            int y = (int)( Math.random() * yh );

            gui.setSize( largura, altura );
            gui.setLocation( x, y );
        } else {        
            gui.setSize( largura, altura );
            gui.setLocationRelativeTo( gui );             
        }
    }
    
    public void setSizeELocation( Plot3DAplicGUI gui, int largura, int altura ) {
        boolean umaVisivel = false;
        int size = plot3DAplicGUIs.size();
        for( int i = 0; !umaVisivel && i < size; i++ ) {
            Plot3DAplicGUI gui2d = plot3DAplicGUIs.get( i );
            if ( gui2d.isVisible() )
                umaVisivel = true;            
        }
        
        if ( umaVisivel ) {
            Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
            int xw = (int)tela.getWidth() - largura;
            int yh = (int)tela.getHeight() - altura;

            int x = (int)( Math.random() * xw );
            int y = (int)( Math.random() * yh );

            gui.setSize( largura, altura );
            gui.setLocation( x, y );
        } else {        
            gui.setSize( largura, altura );
            gui.setLocationRelativeTo( gui );             
        }           
    }
    
    public Plot2DAplicGUI getPlot2DAplicGUIDisponivel() {
        for( Plot2DAplicGUI gui : plot2DAplicGUIs )
            if ( !gui.isVisible() )
                return gui;
        
        Plot2DAplicGUI gui = new Plot2DAplicGUI( drv );
        plot2DAplicGUIs.add( gui );
        return gui;
    }
    
    public Plot3DAplicGUI getPlot3DAplicGUIDisponivel() {        
        for( Plot3DAplicGUI gui : plot3DAplicGUIs )
            if ( !gui.isVisible() )
                return gui;
        
        Plot3DAplicGUI gui = new Plot3DAplicGUI( drv );
        plot3DAplicGUIs.add( gui );
        return gui;       
    }
    
}
