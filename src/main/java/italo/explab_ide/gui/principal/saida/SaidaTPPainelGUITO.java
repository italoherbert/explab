package italo.explab_ide.gui.principal.saida;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class SaidaTPPainelGUITO {
    
    private final SaidaTPPainelGUI gui;
    private int leituraPos = 0;
    
    public SaidaTPPainelGUITO( SaidaTPPainelGUI gui ) {
        this.gui = gui;
    }
    
    public int iniciaLeitura() {
        leituraPos = gui.getSaidaCompPane().getDocument().getLength();
        gui.getSaidaCompPane().requestFocusInWindow();
        
        gui.getSaidaCompPane().setCaretPosition( leituraPos ); 
        
        return leituraPos;
    }
    
    public String finalizaLeitura() {
        int len = gui.getSaidaCompPane().getDocument().getLength();
        try {
            return gui.getSaidaCompPane().getText( leituraPos, len-leituraPos );
        } catch (BadLocationException ex) {
            Logger.getLogger(SaidaTPPainelGUITO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public void left() {
        int pos = gui.getSaidaCompPane().getCaretPosition();
        if ( pos > leituraPos )
            gui.getSaidaCompPane().setCaretPosition( pos ); 
    }
    
    public void right() {
        int pos = gui.getSaidaCompPane().getCaretPosition();
        if ( pos < gui.getSaidaCompPane().getDocument().getLength() )
            gui.getSaidaCompPane().setCaretPosition( pos+1 ); 
        
    }
    
    public void setCharset( String charset ) {
        gui.getSaidaCompPane().setContentType( "text/plain;charset="+charset ); 
    }
    
    public void limpa() {
        this.setTexto( "" ); 
    }
    
    public void setTexto( String texto ) {
        gui.getSaidaCompPane().setText( texto ); 
    }
    
    public void append( String texto ) {        
        Document doc = gui.getSaidaCompPane().getDocument();
        AttributeSet as = gui.getSaidaCompPane().getCharacterAttributes();
        try {
            doc.insertString( doc.getLength(), texto, as );
            gui.getSaidaCompPane().setCaretPosition( doc.getLength() );
        } catch (BadLocationException ex) {
            Logger.getLogger(SaidaTPPainelGUITO.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }

    public SaidaTPPainelGUI getSaidaTPPainelGUI() {
        return gui;
    }
        
    public SaidaTPPainelGUIListener getSaidaTPPainelGUIListener() {
        return gui.getSaidaTPPainelGUIListener();
    }
    
    public SaidaStream getErroStream() {
        return gui.getErroStream();
    }
    
    public SaidaStream getOutStream() {
        return gui.getOutStream();
    }

    public int getLeituraPos() {
        return leituraPos;
    }
        
}
