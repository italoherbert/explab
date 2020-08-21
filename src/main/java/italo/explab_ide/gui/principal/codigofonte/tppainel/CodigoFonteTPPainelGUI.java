package italo.explab_ide.gui.principal.codigofonte.tppainel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JTextPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import libs.gui.tpcombtfechar.TPComBTFechar;

public class CodigoFonteTPPainelGUI extends JPanel implements KeyListener {
    
    private final JTextPane docTextoPane = new JTextPane() {
        public boolean getScrollableTracksViewportWidth() {
            return getUI().getPreferredSize( this ).width <= getParent().getSize().width;
        }
    };
    private final JScrollPane scroll = new JScrollPane();
    private final TPComBTFechar tp;
    private final String rotulo;
        
    private final KeyStroke enterKeyStroke = KeyStroke.getKeyStroke( KeyEvent.VK_ENTER, 0 );    
    private final KeyStroke upKeyStroke = KeyStroke.getKeyStroke( KeyEvent.VK_UP, 0 );
    private final KeyStroke downKeyStroke = KeyStroke.getKeyStroke( KeyEvent.VK_DOWN, 0 );
    
    private final KeyStroke tabStroke = KeyStroke.getKeyStroke( KeyEvent.VK_TAB, 0 );
    private final KeyStroke backspaceStroke = KeyStroke.getKeyStroke( KeyEvent.VK_BACK_SPACE, 0 );
    private final KeyStroke deleteStroke = KeyStroke.getKeyStroke( KeyEvent.VK_DELETE, 0 );

    private final KeyStroke fechaChavesStroke = KeyStroke.getKeyStroke( '}' );
    
    private final KeyStroke ctrlCStroke = KeyStroke.getKeyStroke( KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK );
    private final KeyStroke ctrlVStroke = KeyStroke.getKeyStroke( KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK );
    private final KeyStroke ctrlXStroke = KeyStroke.getKeyStroke( KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK );
    private final KeyStroke ctrlAStroke = KeyStroke.getKeyStroke( KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK );

    private final Font docTextoFont = new Font( Font.MONOSPACED, Font.PLAIN, 13 );
        
    private final CodigoFonteTPPainelGUITO to = new CodigoFonteTPPainelGUITO( this );
    private CodigoFonteTPPainelGUIListener listener;
            
    private boolean codigoAlterado = true;
    
    private boolean novaLinhaHabilitada = true;
    
    private final NumeradoRowHeaderView numeradoRowHeaderView = new NumeradoRowHeaderView();
        
    public CodigoFonteTPPainelGUI( TPComBTFechar tp, String rotulo ) {        
        this.tp = tp;
        this.rotulo = rotulo;
        
        docTextoPane.setStyledDocument( new DefaultStyledDocument() );     
        docTextoPane.setContentType( "text/plain;charset=UTF-8" );         
                
        docTextoPane.getInputMap().put( tabStroke, "none" );            
        docTextoPane.getInputMap().put( deleteStroke, "none" );            
        docTextoPane.getInputMap().put( backspaceStroke, "none" );         
        docTextoPane.getInputMap().put( fechaChavesStroke, "none" ); 
        docTextoPane.getInputMap().put( enterKeyStroke, "none" );    
               
        docTextoPane.getInputMap().put( ctrlAStroke, "none" );            
        docTextoPane.getInputMap().put( ctrlXStroke, "none" );    
        docTextoPane.getInputMap().put( ctrlCStroke, "none" );    
        docTextoPane.getInputMap().put( ctrlVStroke, "none" );            
                
        docTextoPane.setFont( docTextoFont );
                 
        scroll.setViewportView( docTextoPane ); 
        scroll.setRowHeaderView( numeradoRowHeaderView ); 
            
        super.setLayout( new GridLayout() );
        super.add( scroll );         

        docTextoPane.addKeyListener( this );         
    }
       
    public void setCharset( String charset ) {
        docTextoPane.setContentType( "text/plain;charset="+charset );
    }
    
    public void setHabilitaScroll( boolean habilita ) {
        if ( habilita ) {          
            scroll.setEnabled( true ); 
            docTextoPane.getInputMap().remove( upKeyStroke );
            docTextoPane.getInputMap().remove( downKeyStroke ); 
        } else {            
            scroll.setEnabled( false );
            docTextoPane.getInputMap().put( upKeyStroke, "none" );            
            docTextoPane.getInputMap().put( downKeyStroke, "none" );                                         
       }
    }
    
    public StyledDocument novoStyledDocument() {
        StyledDocument doc = new DefaultStyledDocument();
        docTextoPane.setDocument( doc );        
        return doc;
    }
    
    public void docAppend( String texto, AttributeSet style ) {
        SimpleAttributeSet as = new SimpleAttributeSet( style );
        as.addAttribute( StyleConstants.FontFamily, docTextoFont.getFamily() );
        as.addAttribute( StyleConstants.FontSize, docTextoFont.getSize() );
        as.addAttribute( StyleConstants.Bold, docTextoFont.isBold() );
        as.addAttribute( StyleConstants.Italic, docTextoFont.isItalic() );
        try { 
            docTextoPane.getDocument().insertString( docTextoPane.getDocument().getLength(), texto, as );
        } catch (BadLocationException ex) {
            Logger.getLogger(CodigoFonteTPPainelGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int contaQuantLinhas() {
        int linhaCont = 0;
        String texto;
        try {
            texto = docTextoPane.getDocument().getText( 0, docTextoPane.getDocument().getLength() );
            int len = texto.length();
            for( int i = 0; i < len; i++ )
                if ( texto.charAt( i ) == '\n' )
                    linhaCont++;            
        } catch (BadLocationException ex) {
            Logger.getLogger(CodigoFonteTPPainelGUI.class.getName()).log(Level.SEVERE, null, ex);
        }   
        return linhaCont;
    }
    
    public void selecionaTab() {
        tp.setSelectedComponent( this );         
    }
        
    public void limpa() {
        docTextoPane.setText( "" ); 
        this.atualizaNumLinhas();
    }
    
    public void atualizaNumLinhas() {        
        numeradoRowHeaderView.repaint();
    }
               
    @Override
    public void keyPressed( KeyEvent e ) {                                                            
        codigoAlterado = true;
                
        boolean textoRemovido = false;        
        
        switch( e.getKeyCode() ) {
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_DELETE:
            case KeyEvent.VK_BACK_SPACE:
                textoRemovido = to.removeTextoSelecionado();
                break;
            default:
                switch ( e.getModifiersEx() ) {
                    case KeyEvent.SHIFT_DOWN_MASK:
                        if ( e.getExtendedKeyCode() == KeyEvent.VK_CLOSE_BRACKET ) 
                            textoRemovido = to.removeTextoSelecionado();
                        break;
                    case KeyEvent.CTRL_DOWN_MASK:
                        switch( e.getExtendedKeyCode() ) {
                            case KeyEvent.VK_V:
                            case KeyEvent.VK_SPACE:
                                textoRemovido = to.removeTextoSelecionado();                        
                        }
                        break;
                }
                break;
        }
                                                                                     
        switch ( e.getModifiersEx() ) {
            case 0:
                switch ( e.getKeyCode() ) {
                    case KeyEvent.VK_ENTER:
                        if ( novaLinhaHabilitada ) {
                            to.novaLinha();
                        } else {
                            codigoAlterado = textoRemovido;
                        }
                        novaLinhaHabilitada = true;
                        break;                    
                    case KeyEvent.VK_DELETE:
                        if ( !textoRemovido )
                            to.delete();
                        break;
                    case KeyEvent.VK_BACK_SPACE:
                        if ( !textoRemovido )
                            to.backspaceDelete();
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_HOME:
                    case KeyEvent.VK_END:
                    case KeyEvent.VK_PAGE_UP:
                    case KeyEvent.VK_PAGE_DOWN:
                        codigoAlterado = false;
                        break;
                }   
                break;
            case KeyEvent.SHIFT_DOWN_MASK:
                if ( e.getExtendedKeyCode() == KeyEvent.VK_CLOSE_BRACKET )
                    to.novoFechaChave();                
                break;
            case KeyEvent.CTRL_DOWN_MASK:
                switch ( e.getExtendedKeyCode() ) {
                    case KeyEvent.VK_SPACE:                                            
                    case KeyEvent.VK_C:                        
                    case KeyEvent.VK_Z:                        
                    case KeyEvent.VK_Y:                        
                    case KeyEvent.VK_A:                        
                    case KeyEvent.VK_S:                        
                        codigoAlterado = false;
                        break;
                }   
                break;            
        }
                          
    }    
    
    @Override
    public void keyReleased( KeyEvent e ) {                                                                        
        if ( e.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK ) {
            switch( e.getExtendedKeyCode() ) {                            
                case KeyEvent.VK_X:                
                    break;
            }
        }
        
        if ( codigoAlterado ) {
            to.salva();            
            to.codigoAlterado();            
            
            if ( listener != null ) {
                to.salvaCaretPosition();

                Point p = to.cursorCHPonto();
                listener.teclaDigitada( to, p );                         

                to.recuperaCaretPisicao();
            }
            this.atualizaNumLinhas();
        }                
    }   
                    
    @Override
    public void keyTyped( KeyEvent e ) {}

    public void setCodigoFonteTPPainelGUIListener( CodigoFonteTPPainelGUIListener listener ) {
        this.listener = listener;
    }
    
    public CodigoFonteTPPainelGUITO getGUITO() {
        return to;
    }

    public JTextPane getDocTextoPane() {
        return docTextoPane;
    }
    
    public JScrollPane getScroll() {
        return scroll;
    }

    public TPComBTFechar getTP() {
        return tp;
    }

    public String getRotulo() {
        return rotulo;
    }    
       
    class NumeradoRowHeaderView extends JComponent {
                        
        @Override
        public void paintComponent( Graphics g ) {
            super.paintComponent( g );
            int linhasCont = contaQuantLinhas();
            
            int q = (int)Math.log10( linhasCont );
            int desloc = q+1;
            if ( desloc <= 1 )
                desloc = 2;
            
            g.setFont( docTextoFont ); 
                                    
            FontMetrics fm = g.getFontMetrics( docTextoFont );
            Rectangle2D r = fm.getMaxCharBounds( g );
            int w = (int)r.getWidth();
            int h = (int)r.getHeight();
                        
            super.setPreferredSize( new Dimension( desloc * w, scroll.getVerticalScrollBar().getMaximum() ) );                                                    
            super.setSize( new Dimension( desloc * w, scroll.getVerticalScrollBar().getMaximum() ) );                                        
                                        
            String formato = "%"+(desloc+1)+"d";
            
            Document doc = docTextoPane.getDocument();
            int len = doc.getLength();
            try {               
                String texto = doc.getText( 0, len );
                int x = 0;
                                                             
                int cont = 0;
                for( int i = 0; i < len; i++ ) {
                    if ( texto.charAt( i ) == '\n' ) {
                        Rectangle2D ret = docTextoPane.modelToView( i );                        
                        int y = h + (int)ret.getY();                        

                        String num = String.format( formato, cont+1 );                                                                        
                        g.drawString( num, x, y );        
                        
                        cont++;                        
                    }
                }    
                
                Rectangle2D ret = docTextoPane.modelToView( len );                        
                int y = h + (int)ret.getY();

                String num = String.format( formato, cont+1 );                        
                g.drawString( num, x, y );
                
            } catch (BadLocationException ex) {
                Logger.getLogger(CodigoFonteTPPainelGUI.class.getName()).log(Level.SEVERE, null, ex);
            }                                                           
        }
                
    }
    
    public boolean isNovaLinhaHabilitada() {
        return novaLinhaHabilitada;
    }
    
    public void setNovaLinhaHabilitada( boolean habilitada ) {
        novaLinhaHabilitada = habilitada;
    }
    
}
