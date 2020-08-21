package italo.explab.gui.splash;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class SplashPainel extends JPanel {
        
    private Image imagem = null;
    
    public SplashPainel() {        
        try {
            InputStream in = SplashPainel.class.getResourceAsStream( "logo.png" );            
            if ( in != null ) {
                imagem = ImageIO.read( in );
                super.setPreferredSize( new Dimension( imagem.getWidth( this ), imagem.getHeight( this ) ) );
            }
        } catch ( IOException ex ) {
            
        }
    }
    
    @Override
    public void paintComponent( Graphics g ) {
        super.paintComponent( g );
                
        ((Graphics2D)g).setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        ((Graphics2D)g).setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
        
        if ( imagem != null )
            g.drawImage( imagem, 0, 0, this );        
    }
    
}
