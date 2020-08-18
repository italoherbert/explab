package italo.explab.arvore.classe;

import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;

public class EsteOuSuperConstrutorExec implements Exec {

    @Override
    public ExecResult exec(ExecNo no, Executor executor) {
        EsteOuSuperConstrutor esteOuSuper = (EsteOuSuperConstrutor)no;
        
        ClasseConstrutor cc = executor.getExecManager().getExecNoFactory().novoConstrutor( no.getI(), no, no.getCodigo() );
        cc.setParams( esteOuSuper.getParams() );
        cc.setConstrutorBusca( esteOuSuper.getConstrutorBusca() );                           
        
        ExecResult er = executor.exec( cc );
        if ( er.isErroOuEx() )
            return er;
        
        return new ExecResult();
    }
    
}
