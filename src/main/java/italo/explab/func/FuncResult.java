package italo.explab.func;

import italo.explab.arvore.ExObj;
import italo.explab.msg.Erro;
import italo.explab.var.Var;

public class FuncResult {
    
    private Var retornada = null;
    private ExObj exObj = null;
    private Erro erro = null;
    private Func func = null;

    public FuncResult( Func func ) {
        this.func = func;
    }
    
    public FuncResult( ExObj exObj ) {
        this.exObj = exObj;
    }
    
    public FuncResult( Var retornada, Func func ) {
        this.retornada = retornada;
        this.func = func;
    }
    
    public FuncResult( Erro erro ) {
        this.erro = erro;
    }
        
    public Var getRetornada() {
        return retornada;
    }

    public Erro getErro() {
        return erro;
    }

    public Func getFunc() {
        return func;
    }

    public ExObj getExObj() {
        return exObj;
    }

    public void setExObj(ExObj exeptionObj) {
        this.exObj = exeptionObj;
    }
    
}
