package italo.explab.inter.exp.bool.to;

import italo.explab.inter.InterTO;

public class BoolExpInterVO implements InterTO {
            
    private final boolean fechaParentesesEsperado;

    public BoolExpInterVO( boolean fechaParentesesEsperado ) {
        this.fechaParentesesEsperado = fechaParentesesEsperado;
    }
    
    public boolean isFechaParentesesEsperado() {
        return fechaParentesesEsperado;
    }
    
}
