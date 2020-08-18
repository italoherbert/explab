package italo.explab.arvore.cmd;

import italo.explab.arvore.cmd.exec.AjudaExec;
import italo.explab.arvore.cmd.exec.CDExec;
import italo.explab.arvore.cmd.exec.ContinueExec;
import italo.explab.arvore.cmd.exec.ExibaExec;
import italo.explab.arvore.cmd.exec.LSExec;
import italo.explab.arvore.cmd.exec.PareExec;
import italo.explab.arvore.cmd.exec.RetorneExec;
import italo.explab.arvore.cmd.exec.LanceExec;
import italo.explab.arvore.cmd.exec.LeiaExec;
import italo.explab.arvore.cmd.exec.LimpeTelaExec;
import italo.explab.arvore.cmd.exec.ListeExec;
import italo.explab.arvore.cmd.exec.SairExec;
import italo.explab.arvore.cmd.exec.UseExec;

public class CMDExecManager {
    
    private final ContinueExec continueExec = new ContinueExec();
    private final PareExec pareExec = new PareExec();
    private final RetorneExec retorneExec = new RetorneExec();
    private final LanceExec lanceExec = new LanceExec();
    private final ExibaExec exibaExec = new ExibaExec();
    private final AjudaExec ajudaExec = new AjudaExec();
    private final CDExec cdExec = new CDExec();
    private final LSExec lsExec = new LSExec();
    private final LimpeTelaExec limpeTelaExec = new LimpeTelaExec();
    private final ListeExec listeExec = new ListeExec();
    private final LeiaExec leiaExec = new LeiaExec();
    private final UseExec useExec = new UseExec();    
    private final SairExec sairExec = new SairExec();    
    
    public ContinueExec getContinueExec() {
        return continueExec;
    }

    public PareExec getPareExec() {
        return pareExec;
    }

    public RetorneExec getRetorneExec() {
        return retorneExec;
    }

    public LanceExec getLanceExec() {
        return lanceExec;
    }

    public ExibaExec getExibaExec() {
        return exibaExec;
    }

    public AjudaExec getAjudaExec() {
        return ajudaExec;
    }        

    public CDExec getCDExec() {
        return cdExec;
    }

    public LSExec getLSExec() {
        return lsExec;
    }

    public LimpeTelaExec getLimpeTelaExec() {
        return limpeTelaExec;
    }

    public ListeExec getListeExec() {
        return listeExec;
    }

    public LeiaExec getLeiaExec() {
        return leiaExec;
    }

    public UseExec getUseExec() {
        return useExec;
    }

    public SairExec getSairExec() {
        return sairExec;
    }
    
}
