package italo.explab_cmd.gui.inter;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import italo.explab.gui.ExpLabGUIDriver;

public class InterGUI extends JFrame implements KeyListener, MouseListener {

    private final JTextPane cmdTP;
            
    private final InterGUITO to = new InterGUITO( this );
    private InterGUIListener listener;
    
    public InterGUI( ExpLabGUIDriver drv ) {
        super.setIconImages( drv.getJanelaIcones() );
        cmdTP = new JTextPane();
                
        cmdTP.getInputMap().put( KeyStroke.getKeyStroke( KeyEvent.VK_UP, 0 ), "none" );
        cmdTP.getInputMap().put( KeyStroke.getKeyStroke( KeyEvent.VK_DOWN, 0 ), "none" );
        cmdTP.getInputMap().put( KeyStroke.getKeyStroke( KeyEvent.VK_LEFT, 0 ), "none" );
        cmdTP.getInputMap().put( KeyStroke.getKeyStroke( KeyEvent.VK_BACK_SPACE, 0 ), "none" );
        cmdTP.getInputMap().put( KeyStroke.getKeyStroke( KeyEvent.VK_HOME, 0 ), "none" );
        cmdTP.getInputMap().put( KeyStroke.getKeyStroke( KeyEvent.VK_ENTER, 0 ), "none" );
                
        cmdTP.setBackground( Color.BLACK );
        cmdTP.setForeground( Color.WHITE );
        cmdTP.setCaretColor( Color.WHITE );
        
        cmdTP.setFocusable( true );
        cmdTP.setFont( new Font( "Monospaced", Font.BOLD, 13 ) );  
                                                
        super.setContentPane( new JScrollPane( cmdTP ) );        
        super.setTitle( "Interpretador ExpLab" ); 
        super.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); 
        super.setSize( 750, 450 ); 
        super.setLocationRelativeTo( this );        
                        
        cmdTP.addKeyListener( this ); 
        cmdTP.addMouseListener( this ); 
        
        to.novoCMD();         
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if ( listener == null )
            return;
        
        listener.cmdTeclaPressionada( to, e ); 
    }

    @Override
    public void mouseClicked( MouseEvent e ) {
        if ( listener == null )
            return;
        
        listener.cmdMouseClick( to, e.getX(), e.getY() ); 
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
    
    public void setInterGUIListener( InterGUIListener listener ) {
        this.listener = listener;
    }
    
    public InterGUITO getGUITO() {
        return to;
    }

    public JTextPane getCMDTP() {
        return cmdTP;
    }
    
}
