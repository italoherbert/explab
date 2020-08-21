package italo.explab_ide.gui.autocomplete;

public interface AutoCompleteGUIListener {
        
    public void itemSelecionado( AutoCompleteGUITO guiTO, int i, Object item );
    
    public void cliqueForaDaAutoCompleteLista( AutoCompleteGUITO guiTO );
    
}
