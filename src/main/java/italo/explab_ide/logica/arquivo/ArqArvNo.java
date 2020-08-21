package italo.explab_ide.logica.arquivo;

import italo.explab_ide.logica.arquivo.projeto.Projeto;
import libs.gui.arv.ArvNo;

public class ArqArvNo extends ArvNo {
    
    private Projeto projeto;
    private boolean pastaDeProjeto = false;

    public ArqArvNo() {}
    
    public ArqArvNo( Projeto projeto, boolean pastaDeProjeto ) {
        this.projeto = projeto;
        this.pastaDeProjeto = pastaDeProjeto;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }
        
    public boolean isPastaDeProjeto() {
        return pastaDeProjeto;
    }

    public void setEhPastaDeProjeto(boolean pastaDeProjeto) {
        this.pastaDeProjeto = pastaDeProjeto;
    }
    
}
