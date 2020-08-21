package italo.explab.arvore;

import italo.explab.arvore.exp.atrib.AtribExec;
import italo.explab.arvore.bloco.BlocoExec;
import italo.explab.arvore.classe.ConstrutorExec;
import italo.explab.arvore.classe.EsteOuSuperConstrutorExec;
import italo.explab.arvore.exp.ExpExecManager;
import italo.explab.arvore.grupo.GrupoExec;
import italo.explab.arvore.exp.incdec.IncDecExec;
import italo.explab.arvore.cmd.CMDExecManager;
import italo.explab.arvore.estruturas.EstruturaExecManager;

public class ExecManager {
    
    private final ExpExecManager expExecManager = new ExpExecManager();
    private final EstruturaExecManager estruturaExecManager = new EstruturaExecManager();
    private final CMDExecManager cmdExecManager = new CMDExecManager();
    
    private final ExecNoFactory execNoFactory = new ExecNoFactory( this );

    private final BlocoExec blocoExec = new BlocoExec();
    private final GrupoExec grupoExec = new GrupoExec();

    private final ConstrutorExec construtorExec = new ConstrutorExec();
    private final EsteOuSuperConstrutorExec esteOuSuperConstrutorExec = new EsteOuSuperConstrutorExec();

    private final AtribExec atribExec = new AtribExec();
    private final IncDecExec incDecExec = new IncDecExec();
    
    public GrupoExec getGrupoExec() {
        return grupoExec;
    }
    
    public BlocoExec getBlocoExec() {
        return blocoExec;
    }        
    
    public ConstrutorExec getConstrutorExec() {
        return construtorExec;
    }

    public EsteOuSuperConstrutorExec getEsteOuSuperConstrutorExec() {
        return esteOuSuperConstrutorExec;
    }

    public AtribExec getAtribExec() {
        return atribExec;
    }

    public IncDecExec getIncDecExec() {
        return incDecExec;
    }
        
    public ExpExecManager getExpExecManager() {
        return expExecManager;
    }

    public EstruturaExecManager getEstruturaExecManager() {
        return estruturaExecManager;
    }

    public CMDExecManager getCMDExecManager() {
        return cmdExecManager;
    }
            
    public ExecNoFactory getExecNoFactory() {
        return execNoFactory;
    }          
        
}
