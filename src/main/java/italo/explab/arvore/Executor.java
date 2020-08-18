package italo.explab.arvore;

import italo.explab.InterAplic;
import italo.explab.arvore.busca.ArvBuscaManager;

public class Executor {
    
    private final ArvBuscaManager buscaManager = new ArvBuscaManager();
    private final ExecManager manager = new ExecManager();
    private final InterAplic aplic;    

    public Executor( InterAplic aplic ) {
        this.aplic = aplic;
    }
        
    public ExecResult exec( ExecNo no ) {        
        return no.getExec().exec( no, this );
    }
    
    public ExecManager getExecManager() {
        return manager;
    }
    
    public ArvBuscaManager getBuscaManager() {
        return buscaManager;
    }
    
    public InterAplic getAplic() {
        return aplic;
    }

}
