package italo.explab.arvore.exp.variavel.func;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.func.UsuarioFunc;

public class FuncVarExp extends Exp {
    
    private UsuarioFunc ufunc;

    @Override
    public Instrucao novo( ExecNo parent ) {
        FuncVarExp funcVarExp = new FuncVarExp();
        super.carrega( funcVarExp, parent );
        
        funcVarExp.setUsuarioFunc( (UsuarioFunc)ufunc.nova() ); 
        
        return funcVarExp;
    }
    
    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        
    }

    public UsuarioFunc getUsuarioFunc() {
        return ufunc;
    }

    public void setUsuarioFunc(UsuarioFunc func) {
        this.ufunc = func;
    }
            
}
