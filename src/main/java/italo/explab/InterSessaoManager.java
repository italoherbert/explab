package italo.explab;

import italo.explab.arvore.exp.incdec.IncDec;
import italo.explab.msg.MSGController;
import java.util.ArrayList;
import java.util.List;

public class InterSessaoManager {
    
    private boolean fim = false;

    private final InterAplic aplic;
    private MSGController msgController;
    
    private final List<IncDec> incdecs = new ArrayList();
    
    public InterSessaoManager( InterAplic aplic ) {
        this.aplic = aplic;
        this.msgController = new MSGController( aplic.getMSGManager() );
    }        
    
    public void addIncDec( IncDec incdec ) {
        incdecs.add( incdec );
    }
    
    public List<IncDec> popIncDecs() {
        List<IncDec> lista = new ArrayList( incdecs );
        incdecs.clear();
        
        return lista;
    }
    
    public void fim() {
        fim = true;
        aplic.getExecutor().getExecManager().getCMDExecManager().getLeiaExec().leituraConcluida();
    }
    
    public void nova() {
        msgController = new MSGController( aplic.getMSGManager() );
        fim = false;
    }
    
    public boolean isFim() {
        return fim;
    }   

    public MSGController getMSGController() {
        return msgController;
    }

    public List<IncDec> getIncDecs() {
        return incdecs;
    }
    
}
