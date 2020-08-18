package italo.explab.arvore.cmd.exec;

import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.cmd.node.Exiba;
import italo.explab.msg.InterImpr;
import italo.explab.msg.MSGManager;

public class ExibaExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        MSGManager msgManager = executor.getAplic().getMSGManager();
        InterImpr impr = executor.getAplic().getImpr();
        
        Exiba exiba = (Exiba)no;
                                
        ExecResult er = executor.exec( exiba.getExp() );
        if ( er.isErroOuEx() )
            return er;
        
        msgManager.envia( impr.formataVar( er.getVar() ) );
        if ( exiba.isPularLinha() )
            msgManager.envia( "\n" );
        
        return new ExecResult();
    }
    
    
}
