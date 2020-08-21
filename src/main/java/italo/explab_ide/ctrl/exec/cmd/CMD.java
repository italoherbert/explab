package italo.explab_ide.ctrl.exec.cmd;

public class CMD {

    private final String cmd;
    private final String rotulo;

    public CMD( String cmd, String rotulo ) {
        this.cmd = cmd;
        this.rotulo = rotulo;
    }

    public String getCMD() {
        return cmd;
    }

    public String getRotulo() {
        return rotulo;
    }
    
}
