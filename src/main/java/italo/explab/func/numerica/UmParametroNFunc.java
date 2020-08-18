package italo.explab.func.numerica;

import italo.explab.ErroMSGs;
import italo.explab.var.mat.MatrizVar;
import italo.explab.var.num.NumericaVar;
import italo.explab.var.num.NumeroRealVar;
import italo.explab.var.num.RealVar;
import italo.explab.var.mat.NumericaMatrizVar;

public abstract class UmParametroNFunc implements NFunc {

    private final int QUANT_PARAMS = 1;

    public abstract double calcula( double p );
        
    @Override
    public NFuncResult exec( NumericaVar... params ) {
        NFuncErro erro = this.valida( params );
        if ( erro != null )
            return new NFuncResult( erro );

        NumericaVar tv = params[0];
        if ( tv.getTipo() == NumericaVar.REAL ) {
            double v = this.calcula( ((RealVar)tv).getValor() );
            return new NFuncResult( new NumeroRealVar( v ) );
        } else {
            MatrizVar p = (MatrizVar)params[0];
            int nl = p.getNL();
            int nc = p.getNC();

            MatrizVar mat = new MatrizVar( nl, nc );
            for( int k = 0; k < nl; k++ ) {
                for( int j = 0; j < nc; j++ ) {
                    double m_el = ((RealVar)p.getElemento(k, j ) ).getValor();
                    double res = this.calcula( m_el );
                    mat.setElemento( k, j, new NumeroRealVar( res ) );
                }
            }
            return new NFuncResult( new NumericaMatrizVar( mat ) );
        }
    }

    @Override
    public int getQuantParams() {
        return QUANT_PARAMS;
    }
    
    private NFuncErro valida( NumericaVar... params ) {        
        if( params.length != QUANT_PARAMS )
            return new NFuncErro( this.getClass(), ErroMSGs.PARAMS_QUANT_INVALIDA, String.valueOf( QUANT_PARAMS ) );
        return null;
    }
    
}
