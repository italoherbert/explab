package italo.explab_ide.gui.principal.codigofonte.tppainel;

import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import libs.gui.tpcombtfechar.TPBTFecharTabComponent;

public class CodigoFonteTPPainelGUITO {
    
    private final CodigoFonteTPPainelGUI gui;
    private boolean alterado = false;
    private int caretPositionSalva = 0;
    
    private boolean autoCompleteExecutando = false;
    private int autoCompleteI = -1;
    
    private final LinkedList<CodigoFonte> codigoFontes = new LinkedList();
    private int codigoFonteI = 0;
    
    private int tabtam = 4;
    
    public CodigoFonteTPPainelGUITO( CodigoFonteTPPainelGUI gui ) {
        this.gui = gui;
    }
    
    public StyledDocument novoStyledDocument() {
        return gui.novoStyledDocument();
    }
    
    public void docAppend( String texto, AttributeSet attr ) {
        gui.docAppend( texto, attr );
    }
    
    public void seleciona( int i1, int i2 ) {
        gui.getDocTextoPane().select( i1, i2 ); 
    }
    
    public void limpa() {
        gui.limpa();
    }
    
    public void selecionaTab() {
        gui.selecionaTab();
    }
    
    public void autoCompletarFinalizado() {
        gui.setHabilitaScroll( true );
        autoCompleteExecutando = false;
    }
    
    public void autoCompletarIniciado() {
        gui.setHabilitaScroll( false );
        gui.setNovaLinhaHabilitada( false );
        autoCompleteExecutando = true;
        
        int pos = gui.getDocTextoPane().getCaretPosition();
        try {
            autoCompleteI = gui.getDocTextoPane().getDocument().createPosition( pos ).getOffset();
        } catch (BadLocationException ex) {
            autoCompleteI = pos;
            Logger.getLogger(CodigoFonteTPPainelGUITO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
            
    public int getCursorPos() {       
        return gui.getDocTextoPane().getCaretPosition();        
    }
    
    public void setCursorPos( int pos ) {                 
        try {
            if ( pos > gui.getDocTextoPane().getDocument().getLength() )
                pos = gui.getDocTextoPane().getDocument().getLength();
            
            gui.getDocTextoPane().setCaretPosition( pos );
            Rectangle2D ret = gui.getDocTextoPane().modelToView( pos );
            if ( ret != null )
                gui.getDocTextoPane().scrollRectToVisible( ret.getBounds() );            
        } catch (BadLocationException ex) {
            Logger.getLogger(CodigoFonteTPPainelGUITO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public void removeTab() {        
        gui.getTP().removeTabPainel( gui ); 
    }
    
    public void salva() {      
        CodigoFonte cf = new CodigoFonte();
        cf.texto = this.getPlainText();
        cf.pos = gui.getDocTextoPane().getCaretPosition();
                         
        int size = codigoFontes.size();
        if ( codigoFonteI < size - 1 )
            for ( int i = size-1; i > codigoFonteI; i-- )
                codigoFontes.removeLast();        
        
        codigoFontes.addLast( cf );   
        codigoFonteI = codigoFontes.size()-1;
    }
            
    public void proximoCodigoFonte() {
        int len = codigoFontes.size();
        if ( codigoFonteI >= -1 && codigoFonteI < len ) {
            if ( codigoFonteI+1 < len )
                codigoFonteI++;
            
            CodigoFonte cf = codigoFontes.get( codigoFonteI );
            gui.getDocTextoPane().setText( cf.texto ); 
            gui.getDocTextoPane().setCaretPosition( cf.pos ); 
        }
        
    }
    
    public void anteriorCodigoFonte() {        
        if ( codigoFonteI >= 0 && codigoFonteI <= codigoFontes.size() ) {            
            if ( codigoFonteI > 0 )
                codigoFonteI--;
                        
            CodigoFonte cf = codigoFontes.get( codigoFonteI );
            gui.getDocTextoPane().setText( cf.texto ); 
            gui.getDocTextoPane().setCaretPosition( cf.pos );             
        }
    }
    
    public void codigoAlterado() {        
        int indice = gui.getTP().indexOfComponent( gui );
        TPBTFecharTabComponent pnl = (TPBTFecharTabComponent)gui.getTP().getTabComponentAt( indice );
        pnl.getTituloLB().setForeground( Color.BLUE ); 

        alterado = true; 
    }
    
    public void arquivoSalvoEAtualizado() {
        alterado = false;
        
        int indice = gui.getTP().indexOfComponent( gui );
        TPBTFecharTabComponent pnl = (TPBTFecharTabComponent)gui.getTP().getTabComponentAt( indice );
        pnl.getTituloLB().setForeground( Color.BLACK ); 
    }        
    
    public boolean removeTextoSelecionado() {
        int i = gui.getDocTextoPane().getSelectionStart();
        int i2 = gui.getDocTextoPane().getSelectionEnd();        
        if ( i > i2 ) {
            int aux = i;
            i = i2;
            i2 = aux;
        }

        if ( i < i2 ) {
            this.removeTextoSelecionado( i, i2 );               
            return true;
        }
        return false;
    }
    
    public void copiar() {
        int i = gui.getDocTextoPane().getSelectionStart();
        int i2 = gui.getDocTextoPane().getSelectionEnd();        
        if ( i > i2 ) {
            int aux = i;
            i = i2;
            i2 = aux;
        }                     
        
        if ( i < i2 )
            this.copiaParaClipboard( i, i2 ); 
    }
    
    public void mover() {
        int i = gui.getDocTextoPane().getSelectionStart();
        int i2 = gui.getDocTextoPane().getSelectionEnd();        
        if ( i > i2 ) {
            int aux = i;
            i = i2;
            i2 = aux;
        }
        if ( i < i2 ) {
            this.copiaParaClipboard( i, i2 );
            this.removeTextoSelecionado( i, i2 );
        }
    }
    
    public void colar() {
        this.recuperaCopiaDeClipboard();
    }
    
    public void selecionaTudo() {
        this.seleciona( 0, gui.getDocTextoPane().getDocument().getLength() );
    }
        
    public void moveParaFrente() {
        int i = gui.getDocTextoPane().getSelectionStart();
        int i2 = gui.getDocTextoPane().getSelectionEnd();        
        Document doc = gui.getDocTextoPane().getDocument();
        if ( i > i2 ) {
            int aux = i;
            i = i2;
            i2 = aux;
        }
                                                
        try {                                       
            if ( i == i2 ) {
                int j = this.antCHIndice( i, '\n' );                         
                j++;
                int k = gui.getDocTextoPane().getCaretPosition();
                this.insereTabEsps( k, j );
                return;
            }
            
            int j = this.antCHIndice( i, '\n' );                         
            i2 = this.proxCHIndice( i2, '\n' );
                                                
            while ( j <= i2 && j != -1 ) { 
                j++;
                
                int kk = this.proxCHOuPulaLinhaIndice( j );
                if ( kk == -1 ) {
                    j = -1;
                    continue;
                } else {               
                    char ch = doc.getText( kk, 1 ).charAt( 0 );
                    if ( ch == '\n' ) {
                        j = kk;
                        continue;
                    }       
                }
                
                int k = this.indiceProxNaoEspVazio( j );                
                if ( k == -1 ) {
                    j = -1;
                } else {
                    if ( k <= i2 ) {
                        this.insereTabEsps( k, j );                                                                                
                        j = this.proxCHIndice( k, '\n' );
                    } else {
                        j = -1;
                    }
                }
            }
        } catch (BadLocationException ex) {
            Logger.getLogger(CodigoFonteTPPainelGUITO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void moveParaTraz() {
        int i = gui.getDocTextoPane().getSelectionStart();
        int i2 = gui.getDocTextoPane().getSelectionEnd();        
        Document doc = gui.getDocTextoPane().getDocument();
        
        if ( i > i2 ) {
            int aux = i;
            i = i2;
            i2 = aux;
        }
    
        try {   
            int j = this.antCHIndice( i, '\n' );                         
            if ( i == i2 )
                i2 = this.proxCHIndice( i2, '\n' );
                        
            while ( j <= i2 && j != -1 ) { 
                j++;
                
                int kk = this.proxCHOuPulaLinhaIndice( j );
                if ( kk == -1 ) {
                    j = -1;
                    continue;
                } else {                   
                    char ch = doc.getText( kk, 1 ).charAt( 0 );
                    if ( ch == '\n' ) {
                        j = kk;
                        continue;
                    }       
                }
                
                int k = this.indiceProxNaoEspVazio( j );                                
                if ( k == -1 ) {
                    j = -1;
                } else {                            
                    if ( k <= i2 ) {
                        if ( k > j ) {
                            int ii = ( k - j ) % tabtam;
                            if ( ii == 0 )
                                ii = tabtam;

                            doc.remove( k-ii, ii );                                                          
                        }
                        j = this.proxCHIndice( k, '\n' );
                    } else {
                        j = -1;
                    }       
                }
            }
        } catch (BadLocationException ex) {
            Logger.getLogger(CodigoFonteTPPainelGUITO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void insereTabEsps( int k, int j ) throws BadLocationException {        
        int ii = tabtam - ( ( k - j ) % tabtam );                    
        String esps = "";
        for( int jj = 0; jj < ii; jj++ )
            esps += " ";
        
        Document doc = gui.getDocTextoPane().getDocument();
        doc.insertString( k, esps, gui.getDocTextoPane().getCharacterAttributes() );        
    }
    
    private int proxCHOuPulaLinhaIndice( int i ) throws BadLocationException {
        Document doc = gui.getDocTextoPane().getDocument();
        int len = doc.getLength();
        int j = i;
        while( j < len ) {
            char c = doc.getText( j, 1 ).charAt( 0 );                
            if ( !Character.isWhitespace( c ) || c == '\n' )
                return j;
            j++;                
        }        
        return -1;
    }
    
    private int proxCHIndice( int i, char ch ) throws BadLocationException {
        Document doc = gui.getDocTextoPane().getDocument();
        int len = doc.getLength();
        int j = i;
        while( j < len ) {
            char c = doc.getText( j, 1 ).charAt( 0 );
            if ( c == ch )
                return j;                
            j++;                
        }       
        return -1;
    }
    
    private int antCHIndice( int i, char ch ) throws BadLocationException {
        Document doc = gui.getDocTextoPane().getDocument();
        int j = i;
        while( j >= 0 ) {
            char c = doc.getText( j, 1 ).charAt( 0 );
            if ( c == ch )
                return j;                
            j--;                
        }
        return -1;
    }
    
    private int indiceProxNaoEspVazio( int i ) throws BadLocationException {
        Document doc = gui.getDocTextoPane().getDocument();
        int len = doc.getLength();
        int j = i;
        while( j < len ) {
            char c = doc.getText( j, 1 ).charAt( 0 );
            if ( !Character.isWhitespace( c ) )
                return j;                
            j++;                
        }        
        return -1;
    }
    
    public Point cursorCHPonto() {
        Point p = new Point( 0, 0 );
        try {
            Rectangle2D ret = gui.getDocTextoPane().modelToView( gui.getDocTextoPane().getCaretPosition() );
            p.x = (int)ret.getX() + gui.getDocTextoPane().getLocationOnScreen().x;
            p.y = (int)ret.getY() + gui.getDocTextoPane().getLocationOnScreen().y + (int)ret.getHeight();
        } catch (BadLocationException ex) {
            Logger.getLogger(CodigoFonteTPPainelGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    
    public void copiaParaClipboard( int i, int i2 ) {
        try {
            StyledDocument doc = (StyledDocument)gui.getDocTextoPane().getDocument();
            int posI =  doc.createPosition( i ).getOffset();
            int posI2 =  doc.createPosition( i2 ).getOffset();
            
            String texto = doc.getText( posI, posI2-posI );
            StringSelection conteudo = new StringSelection( texto );
            
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents( conteudo, null );
        } catch (BadLocationException ex) {
            Logger.getLogger(CodigoFonteTPPainelGUITO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void recuperaCopiaDeClipboard() {
        Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents( null );
        
        if ( t.isDataFlavorSupported( DataFlavor.stringFlavor ) ) {
            try {
                String texto = String.valueOf( t.getTransferData( DataFlavor.stringFlavor ) );
                StyledDocument doc = (StyledDocument)gui.getDocTextoPane().getDocument();
                int pos =  doc.createPosition( gui.getDocTextoPane().getCaretPosition() ).getOffset();

                AttributeSet as = doc.getCharacterElement( pos ).getAttributes();

                doc.insertString( pos, texto, as );                
            } catch ( UnsupportedFlavorException | IOException | BadLocationException ex ) {
                Logger.getLogger(CodigoFonteTPPainelGUITO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
            
    public void backspaceDelete() {
        try {
            int pos = gui.getDocTextoPane().getCaretPosition();
            
            StyledDocument doc = (StyledDocument)gui.getDocTextoPane().getDocument();            
            int i =  doc.createPosition( pos ).getOffset();
            if ( i <= doc.getLength() ) {
                doc.remove( i-1, 1 );             
                gui.getDocTextoPane().setCaretPosition( pos-1 );             
            }
        } catch ( BadLocationException ex ) {
            Logger.getLogger(CodigoFonteTPPainelGUITO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete() {
        StyledDocument doc = (StyledDocument)gui.getDocTextoPane().getDocument();
        try {
            int pos =  doc.createPosition( gui.getDocTextoPane().getCaretPosition() ).getOffset();            
            if ( pos < doc.getLength() )
                doc.remove( pos, 1 ); 
        } catch ( BadLocationException ex ) {
            Logger.getLogger(CodigoFonteTPPainelGUITO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insertTab() {
        try {
            StyledDocument doc = (StyledDocument)gui.getDocTextoPane().getDocument();
            int pos =  doc.createPosition( gui.getDocTextoPane().getCaretPosition() ).getOffset();
            AttributeSet as = doc.getCharacterElement( pos ).getAttributes();

            StringBuilder sb = new StringBuilder( tabtam );
            for( int i = 0; i < tabtam; i++ )
                sb.append( ' ' );
            doc.insertString( pos, sb.toString(), as );
        } catch ( BadLocationException ex ) {
            Logger.getLogger(CodigoFonteTPPainelGUITO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public void removeTextoSelecionado( int i, int i2 ) {                
        try {
            StyledDocument doc = (StyledDocument)gui.getDocTextoPane().getDocument();
            int posI =  doc.createPosition( i ).getOffset();
            int posI2 =  doc.createPosition( i2 ).getOffset();

            if ( posI >= 0 && posI2 <= doc.getLength() ) {
                doc.remove( posI, posI2-posI );

                gui.getDocTextoPane().setCaretPosition( i ); 
            }
        } catch (BadLocationException ex) {
            Logger.getLogger(CodigoFonteTPPainelGUITO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void novoFechaChave() {
        try {
            int pos = gui.getDocTextoPane().getCaretPosition();
            
            StyledDocument doc = (StyledDocument)gui.getDocTextoPane().getDocument();           
            AttributeSet as = doc.getCharacterElement( pos ).getAttributes();
            
            int i = doc.createPosition( pos ).getOffset(); 
            int ii = i;
            int j = 0;
            
            int k = ii-1;            
            boolean vasio = true;            
            boolean fim = false;
            while ( k >= 0 && !fim ) {
                char ch = doc.getText( k, 1 ).charAt( 0 );                                     
                switch ( ch ) {
                    case '\n':
                        k++;
                        fim = true;
                        break;                        
                    case ' ':
                    case '\t':
                    case '\r':
                        k--;
                        break;
                    default:
                        vasio = false;
                        fim = true;
                        break;                            
                }                    
            } 
                                 
            if ( vasio ) {
                if ( i > 0 )
                    i--;            

                char ch;
                boolean achou = false;
                while( i >= 0 && !achou ) {
                    ch = doc.getText( i, 1 ).charAt( 0 );                                     
                    if ( ch == '{' )
                        achou = true;                 
                    i--;                                
                }

                if ( !achou )
                    i = 0;

                if ( achou ) {
                    boolean achouLN = false;
                    while ( i > 0 && !achouLN ) {
                        ch = doc.getText( i, 1 ).charAt( 0 );                                     
                        if ( ch == '\n' ) {
                            i++;
                            achouLN = true;
                        } else {
                            i--;
                        }
                    }

                    boolean achouCH = false;
                    while ( !achouCH ) {
                        ch = doc.getText( i+j, 1 ).charAt( 0 );                                     
                        switch ( ch ) {
                            case ' ':
                            case '\r':
                            case '\t':
                                j++;
                                break;
                            default:
                                achouCH = true;
                                break;
                        }
                    }                

                    if ( tabtam > 0 ) {
                        j /= tabtam;
                    } else {
                        j = 0;
                    }                                                                   
                }

                if ( achou ) {
                    StringBuilder sb = new StringBuilder();

                    j *= tabtam;
                    for( int c = 0; c < j; c++ )
                        sb.append( ' ' );                    
                    sb.append( '}' );
                    
                    doc.remove( k, ii-k );
                    doc.insertString( k, sb.toString(), as );
                    gui.getDocTextoPane(
                    ).setCaretPosition( k + j + 1 );
                } else {
                    doc.insertString( ii, "}", as );
                    gui.getDocTextoPane().setCaretPosition( pos + 1 );
                }
            } else {
                doc.insertString( ii, "}", as );
                gui.getDocTextoPane().setCaretPosition( pos + 1 );
            }                        
        } catch (BadLocationException ex) {
            Logger.getLogger(CodigoFonteTPPainelGUITO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void novaLinha() {
        try {
            int pos = gui.getDocTextoPane().getCaretPosition();
            
            StyledDocument doc = (StyledDocument)gui.getDocTextoPane().getDocument();           
            AttributeSet as = doc.getCharacterElement( pos ).getAttributes();
            
            int len = doc.getLength();
            int i = doc.createPosition( pos ).getOffset(); 
            int ii = i;
                                 
            if ( i > 0 )
                i--;            
            
            char ch;
            boolean achou = false;
            boolean abreChave = false;
            boolean naoVasio = false;
            while( i > 0 && !achou ) {
                ch = doc.getText( i, 1 ).charAt( 0 );                                     
                switch ( ch ) {
                    case ' ':
                    case '\r':
                    case '\t':
                        i--;
                        break;
                    case '\n':  
                        i++;
                        achou = true;
                        break;                    
                    default:
                        if ( !naoVasio && ch == '{' )
                            abreChave = true;                        
                        naoVasio = true;
                        i--;
                        break;
                }
            }
            
            if ( naoVasio ) {
                int j = 0;
                achou = false;
                while( !achou && tabtam > 0 && i+j < len ) {                             
                    ch = doc.getText( i+j, 1 ).charAt( 0 ); 
                    switch( ch ) {
                        case ' ':
                        case '\r':
                            j++;
                            break;
                        case '\t':
                            j += tabtam;
                        default:
                            j++;
                            achou = true;
                            break;
                    }                   
                }    
                
                if ( tabtam > 0 ) {
                    j /= tabtam; 
                } else {
                    j = 0;
                }

                StringBuilder sb = new StringBuilder( j+2 );               
                sb.append( '\n' );                
                
                if ( abreChave )
                    j++;
                                
                j *= tabtam;                
                for( int k = 0; k < j; k++ )
                    sb.append( ' ' );
                                
                doc.insertString( ii, sb.toString(), as );
                gui.getDocTextoPane().setCaretPosition( pos + j + 1 ); 
            } else {
                int j = ii-i;
                
                StringBuilder sb = new StringBuilder( j );
                sb.append( '\n' );
                
                if ( tabtam > 0 ) {                                    
                    j /= tabtam;
                    j *= tabtam;
                } else {
                    j = 0;
                }
                
                for( int k = 0; k < j; k++ )
                    sb.append( ' ' );
                
                doc.insertString( ii, sb.toString(), as );
                gui.getDocTextoPane().setCaretPosition( pos + j + 1 );
            }  
        } catch (BadLocationException ex) {
            Logger.getLogger(CodigoFonteTPPainelGUITO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setCharset( String charset ) {
        gui.setCharset( charset ); 
    }
    
   public void salvaCaretPosition() {
        caretPositionSalva = gui.getDocTextoPane().getCaretPosition();
    }
    
    public void recuperaCaretPisicao() {
        gui.getDocTextoPane().setCaretPosition( caretPositionSalva );
    }       
    
    public void finalizaFormatacao() {
        this.recuperaCaretPisicao();
        gui.atualizaNumLinhas();
    }
        
    public void substitui( int i1, int i2, String texto ) {
        try {
            AttributeSet as = gui.getDocTextoPane().getCharacterAttributes();
            gui.getDocTextoPane().getDocument().remove( i1, i2-i1 );
            gui.getDocTextoPane().getDocument().insertString( i1, texto, as );
        } catch (BadLocationException ex) {
            Logger.getLogger(CodigoFonteTPPainelGUITO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getDocText() {
        try {
            return gui.getDocTextoPane().getDocument().getText( 0, gui.getDocTextoPane().getDocument().getLength() );
        } catch (BadLocationException ex) {
            Logger.getLogger(CodigoFonteTPPainelGUITO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }           
    
    public String getRotulo() {
        return gui.getRotulo();
    }
         
    public String getPlainText() {
        return gui.getDocTextoPane().getText();
    }
          
    public boolean isAlterado() {
        return alterado;
    }

    public int getTabtam() {
        return tabtam;
    }

    public void setTabtam(int tabtam) {
        this.tabtam = tabtam;
    }

    public boolean isAutoCompleteExecutando() {
        return autoCompleteExecutando;
    }

    public int getAutoCompleteI() {
        return autoCompleteI;
    }
    
    public CodigoFonteTPPainelGUI getCodigoFonteTPPainelGUI() {
        return gui;
    }
        
    class CodigoFonte {
        public String texto;
        public int pos;        
    }
    
}
