package italo.explab.msg;

import italo.explab.var.ObjetoVar;
import java.util.LinkedList;

public class MSGController {
    
    private final LinkedList<Erro> erros = new LinkedList();    
    private final LinkedList<ObjetoVar> exObjVars = new LinkedList();
    
    private final MSGManager msgmanager;

    public MSGController( MSGManager msgmanager ) {
        this.msgmanager = msgmanager;
    }        
    
    public ObjetoVar popEXObjVar() {
        if ( exObjVars.isEmpty() )
            return null;
        return exObjVars.pop();
    }
    
    public void addEXObjVar( ObjetoVar var ) {
        exObjVars.addFirst( var );
    }
    
    public void addErro( Erro erro ) {
        erros.addLast( erro ); 
    }
    
    public void enviaErros() {
        msgmanager.enviaPilhaErros( erros ); 
    }
    
}
