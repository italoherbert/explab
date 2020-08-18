
package italo.explab.var_alterador;

import italo.explab.var.Var;

public interface VarAlterador {
    
    public void alteraVar( Var var ) throws VarAlteradorException;        
    
}
