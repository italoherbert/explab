package italo.explab_ide.logica.arquivo;

import italo.explab_ide.logica.arquivo.projeto.ProjetoXMLNo;
import libs.gui.arv.ArvNo;

public class ArqArvNo extends ArvNo {
    
    private ProjetoXMLNo projeto;
    private boolean pastaDeProjeto = false;

    public ArqArvNo() {}
    
    public ArqArvNo( ProjetoXMLNo projeto, boolean pastaDeProjeto ) {
        this.projeto = projeto;
        this.pastaDeProjeto = pastaDeProjeto;
    }

    public ProjetoXMLNo getProjeto() {
        return projeto;
    }

    public void setProjeto(ProjetoXMLNo projeto) {
        this.projeto = projeto;
    }
        
    public boolean isPastaDeProjeto() {
        return pastaDeProjeto;
    }

    public void setEhPastaDeProjeto(boolean pastaDeProjeto) {
        this.pastaDeProjeto = pastaDeProjeto;
    }
    
}
