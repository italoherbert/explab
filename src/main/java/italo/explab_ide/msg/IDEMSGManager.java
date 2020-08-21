package italo.explab_ide.msg;

import italo.explab.msg.Erro;
import libs.gui.msg.MSGManager;

public class IDEMSGManager extends MSGManager {
           
    public void mostraErro( Erro erro ) {
        super.mostraErro( erro.getChave(), erro.getParams() ); 
    }
    
    public void mostraErroMSG( String msg ) {
        super.getMSGUtil().mostraErro( msg ); 
    }
            
}
