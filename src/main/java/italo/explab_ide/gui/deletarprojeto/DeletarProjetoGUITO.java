package italo.explab_ide.gui.deletarprojeto;

public class DeletarProjetoGUITO {
    
    private final DeletarProjetoPNL gui;
    
    public DeletarProjetoGUITO( DeletarProjetoPNL gui ) {
        this.gui = gui;
    }
    
    public void setMensagem( String mensagem ) {
        gui.getMensagemLB().setText( mensagem ); 
    }
    
    public void setRemoverProjArqsRotulo( String rotulo ) {
        gui.getRemoverProjArqsCB().setText( rotulo );
    }
    
    public boolean isRemoverProjArqsSelecionado() {
        return gui.getRemoverProjArqsCB().isSelected();
    }
    
    public void setRemoverProjArqsSelecionado( boolean selecionado ) {
        gui.getRemoverProjArqsCB().setSelected( selecionado ); 
    }
    
}

