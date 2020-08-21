package italo.explab.func.numerica;

import italo.explab.var.num.NumericaVar;
import italo.explab.var.num.NumeroRealVar;

public abstract class ZeroParametrosNFunc implements NFunc {

    private final int QUANT_PARAMS = 0;

    public abstract double calcula();
        
    @Override
    public NFuncResult exec( NumericaVar... params ) {
        double valor = this.calcula();
        return new NFuncResult( new NumeroRealVar( valor ) );        
    }

    @Override
    public int getQuantParams() {
        return QUANT_PARAMS;
    }
    
}

