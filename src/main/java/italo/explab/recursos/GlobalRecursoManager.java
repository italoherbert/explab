package italo.explab.recursos;

import italo.explab.recursos.func.GlobalFuncLista;
import italo.explab.recursos.var.GlobalVarLista;

public class GlobalRecursoManager extends RecursoManager {
            
    public GlobalRecursoManager() {
        super.varLista = new GlobalVarLista();
        super.funcLista = new GlobalFuncLista();
    }        
    
    public GlobalVarLista getGlobalVarLista() {
        return (GlobalVarLista)super.varLista;
    }
    
    public GlobalFuncLista getGlobalFuncLista() {
        return (GlobalFuncLista)super.funcLista;
    }
        
}
