package italo.explab.func.numerica;

import italo.explab.ErroMSGs;
import italo.explab.var.mat.MatrizVar;
import italo.explab.var.num.NumericaVar;
import italo.explab.var.num.NumeroRealVar;
import italo.explab.var.num.RealVar;
import italo.explab.var.mat.NumericaMatrizVar;

public class NaoEscalarMultNFunc implements NFunc {

    private final int QUANT_PARAMS = 2;
    
    @Override
    public NFuncResult exec( NumericaVar... params ) {
        NFuncErro erro = this.valida( params );
        if ( erro != null )
            return new NFuncResult( erro );
        
        MatrizVar p1 = (MatrizVar)params[0];
        MatrizVar p2 = (MatrizVar)params[1];
        
        int nl = p1.getNL();
        int nc = p1.getNC();
        
        int nc2 = p2.getNC();
                
        MatrizVar mat = new MatrizVar( nl, nc2 );        
        for( int i = 0; i < nl; i++ ) {
            for( int j = 0; j < nc2; j++ ) {
                double v = 0;
                for( int k = 0; k < nc; k++ ) {
                    double n1 = ((RealVar)p1.getElemento( i, k )).getValor();
                    double n2 = ((RealVar)p2.getElemento( k, j )).getValor();
                    v += n1 * n2;                                    
                }
                mat.setElemento( i, j, new NumeroRealVar( v ) );              
            }
        }
        
        return new NFuncResult( new NumericaMatrizVar( mat ) );
    }

    @Override
    public int getQuantParams() {
        return QUANT_PARAMS;
    }

    private NFuncErro valida( NumericaVar... params ) {
        if ( params.length != QUANT_PARAMS )
            return new NFuncErro( this.getClass(), ErroMSGs.PARAMS_QUANT_INVALIDA, String.valueOf( QUANT_PARAMS ) );
        
        NumericaVar p1 = params[0];                
        NumericaVar p2 = params[1];
        if ( p1.getTipo() != NumericaVar.MATRIZ || p2.getTipo() != NumericaVar.MATRIZ )
            return new NFuncErro( this.getClass(), 1, ErroMSGs.OPER_MULT_NAO_MATRIZES );
        
        MatrizVar m1 = (MatrizVar)p1;
        MatrizVar m2 = (MatrizVar)p2;
        if ( m1.getNC() != m2.getNL() )
            return new NFuncErro( this.getClass(), 1, ErroMSGs.OPER_MULT_DIM_INCOMPATIVEL, "**" );        
        return null;
    }

    @Override
    public String getNome() {
        return NFuncFactory.NAO_ESCALAR_MULT;
    }
    
}
