package italo.explab.arvore.busca.func;

import italo.explab.func.Func;
import italo.explab.recursos.classe.Objeto;

public class FuncBuscaResult {
    
    private Func func;
    private Objeto runtimeObjeto;
    
    public FuncBuscaResult( Func func ) {
        this.func = func;
    }
        
    public FuncBuscaResult( Func func, Objeto runtimeObjeto ) {
        this.func = func;
        this.runtimeObjeto = runtimeObjeto;
    }

    public Func getFunc() {
        return func;
    }

    public void setFunc(Func func) {
        this.func = func;
    }

    public Objeto getRuntimeObjeto() {
        return runtimeObjeto;
    }  

    public void setRuntimeObjeto(Objeto runtimeObjeto) {
        this.runtimeObjeto = runtimeObjeto;
    }
    
}
