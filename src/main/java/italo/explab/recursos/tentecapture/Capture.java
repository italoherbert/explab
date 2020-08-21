package italo.explab.recursos.tentecapture;

import italo.explab.recursos.classe.Classe;
import java.util.List;

public class Capture {
    
    private final int capturePos;
    private final String exVarNome;
    private final List<Classe> exClasses;

    public Capture(String exVarNome, List<Classe> exClasses, int capturePos ) {
        this.exVarNome = exVarNome;
        this.exClasses = exClasses;
        this.capturePos = capturePos;
    }

    public String getExVarNome() {
        return exVarNome;
    }

    public List<Classe> getExClasses() {
        return exClasses;
    }

    public int getCapturePos() {
        return capturePos;
    }
    
}
