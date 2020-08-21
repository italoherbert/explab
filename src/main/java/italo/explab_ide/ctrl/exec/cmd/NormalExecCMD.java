package italo.explab_ide.ctrl.exec.cmd;

import italo.explab.ExpLab;

public class NormalExecCMD implements ExecCMD {

    private final CMD cmd;

    public NormalExecCMD(CMD cmd) {
        this.cmd = cmd;
    }        
    
    @Override
    public CMD configuraEConstroiCMD( ExpLab explab ) {
        return cmd;
    }        
    
}
