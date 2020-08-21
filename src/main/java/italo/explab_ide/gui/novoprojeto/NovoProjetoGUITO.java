package italo.explab_ide.gui.novoprojeto;

import italo.explab_ide.gui.GUIVisivel;

public class NovoProjetoGUITO implements GUIVisivel {
    
    private final NovoProjetoGUI gui;
    
    public NovoProjetoGUITO( NovoProjetoGUI gui ) {
        this.gui = gui;
    }
    
    @Override
    public void setVisivel( boolean visivel ) {
        gui.setVisible( visivel );
    }
    
    public String getProjNome() {
        return gui.getProjNomeTF().getText();
    }
    
    public void setProjNome( String nome ) {
        gui.getProjNomeTF().setText( nome ); 
    }
    
    public String getProjCaminho() {
        return gui.getProjCaminhoTF().getText();
    }
    
    public void setProjCaminho( String nome ) {
        gui.getProjCaminhoTF().setText( nome );
    }
    
}
