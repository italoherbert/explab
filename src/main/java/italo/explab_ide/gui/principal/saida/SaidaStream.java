package italo.explab_ide.gui.principal.saida;

import italo.explab.InterStream;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class SaidaStream implements InterStream {        
        
    private final SaidaTPPainelGUI saidaTPPainelGUI;
    private SimpleAttributeSet atrset = new SimpleAttributeSet();

    public SaidaStream( SaidaTPPainelGUI saidaGUI, Color cor ) {
        this.saidaTPPainelGUI = saidaGUI; 
        
        StyleConstants.setForeground( atrset, cor );
    }
    
    @Override
    public void envia( String texto ) {
        try {            
            Document doc = saidaTPPainelGUI.getSaidaCompPane().getDocument();
            int i = doc.getLength();                        
            doc.insertString( i, texto, atrset );
            
            int len = doc.getLength();            
            saidaTPPainelGUI.getSaidaCompPane().setCaretPosition( len-1 ); 
        } catch (BadLocationException ex) {
            Logger.getLogger(SaidaStream.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public SimpleAttributeSet getSimpleAtrSet() {
        return atrset;
    }

    public void setSimpleAtrSet(SimpleAttributeSet atrset) {
        this.atrset = atrset;
    }
    
}


