package italo.explab.var_alterador;

import italo.explab.recursos.RecursoManager;
import italo.explab.recursos.var.ConstanteException;
import italo.explab.var.Var;

public class SimplesAlterador implements VarAlterador {
    
    private final String varnome;
    private final RecursoManager recursos;

    public SimplesAlterador( String varnome, RecursoManager recursos ) {
        this.varnome = varnome;
        this.recursos = recursos;
    }

    @Override
    public void alteraVar( Var var ) throws VarAlteradorException {        
        try {
            recursos.getVarLista().criaOuAltera( varnome, var );           
        } catch ( ConstanteException ex ) {
            throw new VarAlteradorException( ex );
        }
    }

    public String getVarNome() {
        return varnome;
    }

    public RecursoManager getRecursos() {
        return recursos;
    }        
    
}
