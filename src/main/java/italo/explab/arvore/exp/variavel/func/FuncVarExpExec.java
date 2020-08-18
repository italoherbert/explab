package italo.explab.arvore.exp.variavel.func;

import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.var.FuncVar;

public class FuncVarExpExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        FuncVarExp funcVarExp = (FuncVarExp)no;
                  
        FuncVar fvar = new FuncVar( funcVarExp.getUsuarioFunc() );        
        return new ExecResult( fvar );                
    }
    
    
}
