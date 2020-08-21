package italo.explab.arvore.estruturas;

import italo.explab.arvore.estruturas.exec.CompareCasoExec;
import italo.explab.arvore.estruturas.exec.EnquantoExec;
import italo.explab.arvore.estruturas.exec.FacaEnquantoExec;
import italo.explab.arvore.estruturas.exec.ParaExec;
import italo.explab.arvore.estruturas.exec.SeExec;
import italo.explab.arvore.estruturas.exec.TenteCaptureExec;

public class EstruturaExecManager {
    
    private final SeExec seExec = new SeExec();
    private final CompareCasoExec compareCasoExec = new CompareCasoExec();
    private final ParaExec paraExec = new ParaExec();
    private final EnquantoExec enquantoExec = new EnquantoExec();
    private final FacaEnquantoExec facaEnquantoExec = new FacaEnquantoExec();
    private final TenteCaptureExec tenteCaptureExec = new TenteCaptureExec();
    
    public SeExec getSeExec() {
        return seExec;
    }

    public CompareCasoExec getCompareCasoExec() {
        return compareCasoExec;
    }

    public ParaExec getParaExec() {
        return paraExec;
    }

    public EnquantoExec getEnquantoExec() {
        return enquantoExec;
    }

    public FacaEnquantoExec getFacaEnquantoExec() {
        return facaEnquantoExec;
    }

    public TenteCaptureExec getTenteCaptureExec() {
        return tenteCaptureExec;
    }        
    
}
