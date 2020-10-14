package italo.explab.arvore.exp.matriz;

import italo.explab.arvore.ExecResult;
import italo.explab.arvore.exp.VarOuFuncExecResult;
import italo.explab.msg.Erro;
import italo.explab.var.Var;
import italo.explab.var_alterador.VarAlterador;

public class MatrizExecResult extends VarOuFuncExecResult {
    
    private int matI = -1;
    private int matJ = -1;

    public MatrizExecResult( Var var, int matI, int matJ, VarAlterador alterador ) {
        super( var, null, alterador );
        this.matI = matI;
        this.matJ = matJ;
    }
    
    public MatrizExecResult( Erro erro ) {
        super( erro );        
    }
    
    public MatrizExecResult( ExecResult erroOuExER ) {
        super.setErro( erroOuExER.getErro() );
        super.setExObj( erroOuExER.getExObj() ); 
    }
    
    public int getMatI() {
        return matI;
    }

    public int getMatJ() {
        return matJ;
    }
    
}
