package italo.explab.recursos.var;

import italo.explab.recursos.RecursoManager;

public class VariavelBuscaResult {
    
    private final Variavel variavel;
    private final RecursoManager recursos;

    public VariavelBuscaResult( Variavel variavel, RecursoManager recursos ) {
        this.variavel = variavel;
        this.recursos = recursos;
    }

    public Variavel getVariavel() {
        return variavel;
    }

    public RecursoManager getRecursoManager() {
        return recursos;
    }
    
}
