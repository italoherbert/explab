package italo.explab_ide.gui.principal.saida;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import libs.gui.tpcombtfechar.TPComBTFechar;

public class SaidaTPPainelGUI extends JPanel implements KeyListener, MouseListener {
    
    private final JTextPane saidaCompPane = new JTextPane();
    
    private final SaidaStream outStream = new SaidaStream( this, Color.BLACK );
    private final SaidaStream erroStream = new SaidaStream( this, Color.RED );    
        
    private final KeyStroke upStroke = KeyStroke.getKeyStroke( KeyEvent.VK_UP, 0 );
    private final KeyStroke downStroke = KeyStroke.getKeyStroke( KeyEvent.VK_DOWN, 0 );
    private final KeyStroke leftStroke = KeyStroke.getKeyStroke( KeyEvent.VK_LEFT, 0 );
    private final KeyStroke rightStroke = KeyStroke.getKeyStroke( KeyEvent.VK_RIGHT, 0 );
    private final KeyStroke pageUpStroke = KeyStroke.getKeyStroke( KeyEvent.VK_PAGE_UP, 0 );
    private final KeyStroke pageDownStroke = KeyStroke.getKeyStroke( KeyEvent.VK_PAGE_DOWN, 0 );
    private final KeyStroke homeStroke = KeyStroke.getKeyStroke( KeyEvent.VK_HOME, 0 );

    private final KeyStroke enterStroke = KeyStroke.getKeyStroke( KeyEvent.VK_ENTER, 0 );
    
    private final TPComBTFechar tp;
    private final String rotulo;
    
    private final SaidaTPPainelGUITO to = new SaidaTPPainelGUITO( this );
    private SaidaTPPainelGUIListener listener;
    
    public SaidaTPPainelGUI( TPComBTFechar tp, String rotulo ) {
        this.tp = tp;
        this.rotulo = rotulo;
        
        saidaCompPane.setContentType( "text/plain;charset=UTF-8" );         
        saidaCompPane.setFont( new Font( Font.MONOSPACED, Font.PLAIN, 13 ) );        
        
        saidaCompPane.getInputMap().put( upStroke, "none" );            
        saidaCompPane.getInputMap().put( downStroke, "none" );                      
        saidaCompPane.getInputMap().put( leftStroke, "none" );         
        saidaCompPane.getInputMap().put( rightStroke, "none" ); 
        saidaCompPane.getInputMap().put( pageUpStroke, "none" );         
        saidaCompPane.getInputMap().put( pageDownStroke, "none" );   
        saidaCompPane.getInputMap().put( homeStroke, "none" );    
        saidaCompPane.getInputMap().put( enterStroke, "none" );
        
        saidaCompPane.addKeyListener( this ); 
        saidaCompPane.addMouseListener( this );
        
        super.setLayout( new GridLayout() );
        super.add( new JScrollPane( saidaCompPane ) );
    }        
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch ( e.getKeyCode() ) {
            case KeyEvent.VK_ENTER:                
                to.append( "\n" );
                String valorLido = to.finalizaLeitura();
                if ( listener != null )
                    listener.leituraConcluida( to, valorLido );                                 
                break;
            case KeyEvent.VK_LEFT:
                to.left();
                break;
            case KeyEvent.VK_RIGHT:
                to.right();
                break;
        }
    }
    
    
    @Override
    public void mouseReleased(MouseEvent e) {
        int lpos = to.getLeituraPos();
        int pos = saidaCompPane.getCaretPosition();
        if ( pos < lpos )
            saidaCompPane.setCaretPosition( saidaCompPane.getDocument().getLength() );
    }
    
    @Override
    public void mousePressed(MouseEvent e) {}
    
    @Override
    public void mouseClicked(MouseEvent e) {}
    
    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
        
    @Override
    public void keyTyped(KeyEvent e) {}        
    
    public boolean perguntaSeFechar(int i) {
        if ( listener == null )
            return true;
        
        return listener.verificaSeFechar( to );
    }

    public void antesTabRemovida(int i) {
        if ( listener != null )
            listener.antesTabRemovida( to, i );        
    }
        
    public SaidaTPPainelGUIListener getSaidaTPPainelGUIListener() {
        return listener;
    }
    
    public void setSaidaTPPainelGUIListener( SaidaTPPainelGUIListener listener ) {
        this.listener = listener;
    }
    
    public SaidaTPPainelGUITO getGUITO() {
        return to;
    }
        
    public TPComBTFechar getTP() {
        return tp;
    }

    public String getRotulo() {
        return rotulo;
    }    
    
    public SaidaStream getOutStream() {
        return outStream;
    }

    public SaidaStream getErroStream() {
        return erroStream;
    }
    
    public JTextPane getSaidaCompPane() {
        return saidaCompPane;
    }    

}