package italo.explab_ide.gui.autocomplete;

import italo.explab_ide.gui.IDEGUIConfig;
import java.awt.AWTEvent;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class AutoCompleteGUI extends JPopupMenu implements MouseListener, AWTEventListener {
    
    private final JList lista = new JList();
    
    private final AutoCompleteGUITO to = new AutoCompleteGUITO( this );
    private AutoCompleteGUIListener listener;
        
    private boolean foco = false;    
    private final int quantLinhasVisiveisPadrao;
    
    public AutoCompleteGUI( IDEGUIConfig config ) {
        quantLinhasVisiveisPadrao = lista.getVisibleRowCount();
        
        lista.setSelectionMode( ListSelectionModel.SINGLE_SELECTION ); 
                      
        super.setLayout( new GridLayout() );
        super.add( new JScrollPane( lista ) );
        
        lista.addMouseListener( this );

        Toolkit.getDefaultToolkit().addAWTEventListener( this, AWTEvent.MOUSE_EVENT_MASK ); 
        Toolkit.getDefaultToolkit().addAWTEventListener( this, AWTEvent.KEY_EVENT_MASK );         
    }    
    
    @Override
    public void eventDispatched(AWTEvent event) {        
        if ( !super.isVisible() )
            return;
            
        switch ( event.getID() ) {
            case MouseEvent.MOUSE_PRESSED:
                if ( !foco )
                    if ( listener != null )
                        listener.cliqueForaDaAutoCompleteLista( to );                
                break;
            case MouseEvent.MOUSE_RELEASED:
                super.setVisible( false );
                break;
            case KeyEvent.KEY_PRESSED:
                int code = ((KeyEvent)event).getKeyCode();
                switch( code ) {
                    case KeyEvent.VK_UP:
                        to.antListItem();
                        break;
                    case KeyEvent.VK_DOWN:
                        to.proxListItem();
                        break;
                    case KeyEvent.VK_ENTER:
                        if ( listener != null ) {
                            super.setVisible( false );
                            listener.itemSelecionado( to, lista.getSelectedIndex(), lista.getSelectedValue() );
                       }
                        break;            
                }   
                break;
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if ( listener != null )
            listener.itemSelecionado( to, lista.getSelectedIndex(), lista.getSelectedValue() );                 
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {}        

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        foco = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        foco = false;
    }          
    
    public void setAutoCompleteGUIListener( AutoCompleteGUIListener listener )     {
        this.listener = listener;
    }
    
    public AutoCompleteGUITO getGUITO() {
        return to;
    }   
    
    public JList getJLista() {
        return lista;
    }        

    public int getQuantLinhasVisiveisPadrao() {
        return quantLinhasVisiveisPadrao;
    }
    
}
