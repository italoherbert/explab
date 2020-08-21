package italo.explab.inter.exp.num.to;

import italo.explab.inter.InterTO;

public class NumExpInterVO implements InterTO {
    
    private final boolean fechaParentesesEsperado;

    public NumExpInterVO( boolean fechaParentesesEsperado ) {
        this.fechaParentesesEsperado = fechaParentesesEsperado;
    }
    
    public boolean isFechaParentesesEsperado() {
        return fechaParentesesEsperado;
    }
    
}
