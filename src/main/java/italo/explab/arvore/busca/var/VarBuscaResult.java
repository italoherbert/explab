package italo.explab.arvore.busca.var;

import italo.explab.recursos.RecursoManager;
import italo.explab.recursos.classe.Objeto;
import italo.explab.recursos.var.Variavel;

public class VarBuscaResult {
    
    private Variavel variavel;
    private RecursoManager recursos;
    private Objeto runtimeObjeto;

    public VarBuscaResult( Variavel variavel, RecursoManager recursos, Objeto obj ) {
        this.variavel = variavel;
        this.recursos = recursos;
        this.runtimeObjeto = obj;
    }

    public Variavel getVariavel() {
        return variavel;
    }

    public void setVariavel(Variavel variavel) {
        this.variavel = variavel;
    }

    public RecursoManager getRecursos() {
        return recursos;
    }

    public void setRecursos(RecursoManager recursos) {
        this.recursos = recursos;
    }

    public Objeto getRuntimeObjeto() {
        return runtimeObjeto;
    }

    public void setRuntimeObjeto(Objeto runtimeObjeto) {
        this.runtimeObjeto = runtimeObjeto;
    }    
    
}
