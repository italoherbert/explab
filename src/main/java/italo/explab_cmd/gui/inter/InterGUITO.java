package italo.explab_cmd.gui.inter;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;

public class InterGUITO {

    public final static String CMD_INI = "explab>> ";    

    private final InterGUI gui;
    
    private int inicioCMDPos = 0;
    private int docInicioCMDPos = 0;
    
    public InterGUITO( InterGUI gui ) {
        this.gui = gui;
    }
            
    public void limparCMD() {
        gui.getCMDTP().setText( "" ); 
        inicioCMDPos = 0;
        docInicioCMDPos = 0;
        this.novoCMD();
    }
    
    public void novoCMD() {
        this.addCMDTexto( "\n"+CMD_INI );         
    }
    
    public void limpaTela() {
        SwingUtilities.invokeLater( () -> {
            gui.getCMDTP().setText( "" ); 
        } );
    }
                
    public void addCMDTexto( String texto ) {
        SwingUtilities.invokeLater( () -> {
            Document doc = gui.getCMDTP().getStyledDocument();
            try {
                doc.insertString( doc.getLength(), texto, new SimpleAttributeSet() );
                inicioCMDPos = gui.getCMDTP().getText().length();
                docInicioCMDPos = doc.getLength();

                gui.getCMDTP().setCaretPosition( docInicioCMDPos ); 
            } catch (BadLocationException ex) {
                Logger.getLogger(InterGUITO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } );
    }
    
    public String getCMDCorrente() {
        return this.getCMDTexto( inicioCMDPos ); 
    }
            
    public void setCMDCorrente( String comando ) {
        gui.getCMDTP().setText( gui.getCMDTP().getText().substring( 0, inicioCMDPos ) + comando ); 
        gui.getCMDTP().setCaretPosition( gui.getCMDTP().getDocument().getLength() ); 
    }
        
    public void cursorParaUltimaPosic() {
        gui.getCMDTP().setCaretPosition( gui.getCMDTP().getDocument().getLength() ); 
    }
        
    public void removeCMDCHAnteriorAoCursor() {
        try { 
            int pos = gui.getCMDTP().getCaretPosition();
            gui.getCMDTP().getDocument().remove( pos-1, 1 );            
        } catch (BadLocationException ex) {
            Logger.getLogger(InterGUITO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getDocInicioCMDPos() {
        return docInicioCMDPos;
    }
    
    public int getCMDTextoCursorPos() {
        return gui.getCMDTP().getCaretPosition();
    }
    
    public void setCMDTextoCursorPos( int pos ) {
        gui.getCMDTP().setCaretPosition( pos ); 
    }
    
    public boolean isCMDTextoEditavel() {
        return gui.getCMDTP().isEditable();
    }
    
    public void setCMDTextoEditavel( boolean editavel ) {
        gui.getCMDTP().setEditable( editavel ); 
    }
        
    public String getCMDTexto( int pos ) {
        return gui.getCMDTP().getText().substring( pos, gui.getCMDTP().getText().length() );
    }
    
    public String getCMDTexto() {
        return gui.getCMDTP().getText();
    }
    
    public void setCMDTexto( String exp ) {
        gui.getCMDTP().setText( exp ); 
    }        
    
}
