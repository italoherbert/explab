package italo.explab.arvore.exp;

import italo.explab.arvore.ExObj;
import italo.explab.arvore.ExecResult;
import italo.explab.msg.Erro;
import italo.explab.var.Var;
import italo.explab.var_alterador.VarAlterador;

public class VarOuFuncExecResult extends ExecResult {
    
    private String varnome;
    private VarAlterador alterador;
    private boolean ehFunc = false;

    public VarOuFuncExecResult() {}

    public VarOuFuncExecResult( Var var, boolean ehFunc ) {
        super( var );
        this.ehFunc = ehFunc;
    }

    public VarOuFuncExecResult( Var var, String varnome, VarAlterador alterador ) {
        super( var );
        this.varnome = varnome;
        this.alterador = alterador;
    }
    
    public VarOuFuncExecResult( ExObj exObj ) {
        super( exObj );
    }
        
    public VarOuFuncExecResult( Erro erro ) {
        super( erro );
    }
    
    public VarOuFuncExecResult( ExecResult erroOuExER ) {
        super.setErro( erroOuExER.getErro() );
        super.setExObj( erroOuExER.getExObj() ); 
    }

    public boolean isFunc() {
        return ehFunc;
    }
    
    public String getVarNome() {
        return varnome;
    }

    public VarAlterador getAlterador() {
        return alterador;
    }
    
}
