package italo.explab.execproc;

public class ExecProcManager {
    
    private final CMDsExecProc cmdsExecProc = new CMDsExecProc();
    private final NumericaExpExecProc numericaExpExecProc = new NumericaExpExecProc();

    public CMDsExecProc getCMDsExecProc() {
        return cmdsExecProc;
    }

    public NumericaExpExecProc getNumericaExpExecProc() {
        return numericaExpExecProc;
    }
    
    
    
}
