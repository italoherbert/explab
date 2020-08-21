package italo.explab.arvore.exp;

public class OOChamada {

    private ExpRecurso objeto;
    private ExpRecurso chamada;
    
    public OOChamada( ExpRecurso objeto, ExpRecurso chamada ) {
        this.objeto = objeto;
        this.chamada = chamada;
    }
    
    public ExpRecurso getObjetoChamador() {
        return objeto;
    }

    public void setObjetoChamador(ExpRecurso objeto) {
        this.objeto = objeto;
    }

    public ExpRecurso getChamada() {
        return chamada;
    }

    public void setChamada(ExpRecurso chamada) {
        this.chamada = chamada;
    }       
    
}
