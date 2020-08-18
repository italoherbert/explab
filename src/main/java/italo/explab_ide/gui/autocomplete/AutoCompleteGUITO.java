package italo.explab_ide.gui.autocomplete;

import java.awt.Point;
import java.util.Collection;

public class AutoCompleteGUITO {
    
    private final AutoCompleteGUI gui;
    
    public AutoCompleteGUITO( AutoCompleteGUI gui ) {
        this.gui = gui;
    }
        
    public void mostrar( Point p, Collection<String> list ) {
        int size = list.size();
        if ( size == 0 )
            return;
        
        if ( size < gui.getQuantLinhasVisiveisPadrao() ) {
            gui.getJLista().setVisibleRowCount( size ); 
        } else {
            gui.getJLista().setVisibleRowCount( gui.getQuantLinhasVisiveisPadrao() );
        }
        
        gui.getJLista().setListData( list.toArray() );
        gui.getJLista().setSelectedIndex( 0 ); 
        gui.setLocation( p ); 
        gui.setVisible( true ); 
    }
    
    public void proxListItem() {
        int i = gui.getJLista().getSelectedIndex();
        if ( i < gui.getJLista().getModel().getSize()-1 ) {
            i++;
            gui.getJLista().setSelectedIndex( i ); 
            gui.getJLista().ensureIndexIsVisible( i ); 
        }
    }
    
    public void antListItem() {
        int i = gui.getJLista().getSelectedIndex();
        if ( i > 0 ) {
            i--;
            gui.getJLista().setSelectedIndex( i ); 
            gui.getJLista().ensureIndexIsVisible( i ); 
        }
    }
    
    public void setVisivel( boolean visivel ) {
        gui.setVisible( visivel ); 
    }
    
}
