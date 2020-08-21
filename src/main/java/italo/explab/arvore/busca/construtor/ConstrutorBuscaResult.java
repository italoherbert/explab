package italo.explab.arvore.busca.construtor;

import italo.explab.construtor.Construtor;
import italo.explab.recursos.classe.Objeto;

public class ConstrutorBuscaResult {

    private Construtor construtor;
    private Objeto runtimeObjeto;

    public ConstrutorBuscaResult( Construtor construtor, Objeto runtimeObjeto ) {
        this.construtor = construtor;
        this.runtimeObjeto = runtimeObjeto;
    }

    public Construtor getConstrutor() {
        return construtor;
    }

    public void setConstrutor(Construtor construtor) {
        this.construtor = construtor;
    }

    public Objeto getRuntimeObjeto() {
        return runtimeObjeto;
    }

    public void setRuntimeObjeto(Objeto ref) {
        this.runtimeObjeto = ref;
    }
    
}
