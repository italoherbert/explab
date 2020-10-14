package italo.explab_ide.ctrl.exec.cmd;

import italo.explab.ExpLab;
import italo.explab_ide.ExpLabIDEAplic;

public class ScriptExecCMD implements ExecCMD {

    private final String projNome;
    private final String projBasedir;
    private final String execScript;
    
    public ScriptExecCMD( ExpLabIDEAplic aplic, String projNome, String projBasedir, String execScript ) {
        this.projNome = projNome;
        this.projBasedir = projBasedir;
        this.execScript = execScript;
    }
    
    @Override
    public CMD configuraEConstroiCMD( ExpLab explab ) {        
        if ( projBasedir != null )                
            explab.getAplic().getConfig().setDiretorioCorrente( projBasedir );
               
        String rotulo = "Rodando ("+projNome+")";                                
        String cmd = "script "+execScript;
                
        return new CMD( cmd, rotulo );
    }
    
}
