package italo.explab.inter.exp.string.to;

import italo.explab.inter.InterTO;

public class StringExpInterVO implements InterTO {
    
    private final boolean fechaParentesesEsperado;

    public StringExpInterVO( boolean fechaParentesesEsperado ) {
        this.fechaParentesesEsperado = fechaParentesesEsperado;
    }
    
    public boolean isFechaParentesesEsperado() {
        return fechaParentesesEsperado;
    }
    
}
