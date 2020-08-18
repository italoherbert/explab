package italo.explab.arvore;

import italo.explab.arvore.cmd.node.Lance;
import italo.explab.var.ObjetoVar;

public class ExObj {

    private ObjetoVar exceptionObj;
    private Lance lance;

    public ExObj( ObjetoVar exceptionObj ) {
        this.exceptionObj = exceptionObj;
    }
    
    public ExObj( ObjetoVar exceptionObj, Lance lance ) {
        this.exceptionObj = exceptionObj;
        this.lance = lance;
    }

    public ObjetoVar getExeceptionObj() {
        return exceptionObj;
    }

    public void setExeceptionObj(ObjetoVar exceptionObj) {
        this.exceptionObj = exceptionObj;
    }

    public Lance getLance() {
        return lance;
    }

    public void setLance(Lance lance) {
        this.lance = lance;
    }
            
}
