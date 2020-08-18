package italo.explab_ide.ctrl.exec.cmd;

import italo.explab.ExpLab;
import italo.explab_ide.ExpLabIDEAplic;
import italo.explab_ide.logica.arquivo.projeto.Projeto;
import italo.explab_ide.logica.arquivo.projeto.ProjetoConfig;

public class ScriptExecCMD implements ExecCMD {

    private final Projeto proj;
    private final ProjetoConfig projCFG;
    private final String execScript;
    
    private final ExpLabIDEAplic aplic;

    public ScriptExecCMD( ExpLabIDEAplic aplic, Projeto proj, ProjetoConfig projCFG, String execScript ) {
        this.proj = proj;
        this.projCFG = projCFG;
        this.execScript = execScript;
        this.aplic = aplic;
    }
    
    @Override
    public CMD configuraEConstroiCMD( ExpLab explab ) {
        String projBasedir = proj.getBasedir().replaceAll( "\\\\", "/" );
        if ( !projBasedir.endsWith( "/" ) )
            projBasedir += "/";
        
        String rotulo = proj.getNome()+ " - " + projCFG.getExecScript();
        
        explab.getAplic().getConfig().setDiretorioCorrente( projBasedir );
                
        String script;        
        if ( execScript != null ) {
            script = execScript;
        } else {
            script = projCFG.getExecScript();
        }
                        
        String cmd = "script "+script;
                
        return new CMD( cmd, rotulo );
    }
    
}
