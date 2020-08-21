package italo.explab.arvore;

import italo.explab.msg.Erro;
import italo.explab.var.Var;

public class ExecResult {
    
    private Var var = null;
    private Erro erro = null;
    private boolean continueEncontrado = false;
    private boolean pareEncontrado = false;
    private Var retornada = null;
    private ExObj exObj = null;

    public ExecResult() {}
    
    public ExecResult( ExObj exObj ) {
        this.exObj = exObj;
    }
    
    public ExecResult( Var var ) {
        this.var = var;
    }
    
    public ExecResult( Erro erro ) {
        this.erro = erro;
    }
            
    public boolean isFinalizarBloco() {
        return continueEncontrado || this.isFimLoop();
    }
    
    public boolean isFimLoop() {
        return pareEncontrado || retornada != null || exObj != null;
    }
    
    public boolean isErroOuEx() {
        return erro != null || exObj != null;
    }
    
    public Var getVar() {
        return var;
    }

    public void setVar(Var var) {
        this.var = var;
    }
    
    public Erro getErro() {
        return erro;
    }

    public void setErro(Erro erro) {
        this.erro = erro;
    }

    public boolean isContinueEncontrado() {
        return continueEncontrado;
    }

    public void setContinueEncontrado(boolean continueEncontrado) {
        this.continueEncontrado = continueEncontrado;
    }

    public boolean isPareEncontrado() {
        return pareEncontrado;
    }

    public void setPareEncontrado(boolean pareEncontrado) {
        this.pareEncontrado = pareEncontrado;
    }

    public Var getRetornada() {
        return retornada;
    }

    public void setRetornada(Var retornada) {
        this.retornada = retornada;
    }

    public ExObj getExObj() {
        return exObj;
    }

    public void setExObj(ExObj exceptionObj) {
        this.exObj = exceptionObj;
    }


}
