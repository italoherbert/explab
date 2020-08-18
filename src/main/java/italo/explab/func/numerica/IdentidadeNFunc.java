package italo.explab.func.numerica;

import italo.explab.ErroMSGs;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;
import italo.explab.var.mat.NumericaMatrizVar;
import italo.explab.var.num.NumericaVar;
import italo.explab.var.num.NumeroRealVar;
import italo.explab.var.num.RealVar;

public class IdentidadeNFunc implements NFunc {
    
    private final int QUANT_PARAMS = 1;
    
    @Override
    public NFuncResult exec( NumericaVar... params ) {
        if ( params.length != QUANT_PARAMS ) {
            NFuncErro erro = new NFuncErro( this.getClass(), ErroMSGs.PARAMS_QUANT_INVALIDA, String.valueOf( QUANT_PARAMS ) );
            return new NFuncResult( erro );
        }
                
        NumericaVar p1 = params[0];                
        if ( p1.getTipo() != Var.REAL ) {
            NFuncErro erro = new NFuncErro( this.getClass(), "params.tipo.real.esperado" );
            return new NFuncResult( erro );
        }
                       
        int nl = (int)((RealVar)p1).getValor();
        int nc = (int)((RealVar)p1).getValor();
        
        MatrizVar mat = new MatrizVar( nl, nc );        
        for( int i = 0; i < nl; i++ ) {
            for( int j = 0; j < nc; j++ ) {
                if ( i == j ) {
                    mat.setElemento( i, j, new NumeroRealVar( 1 ) );                                   
                } else {
                    mat.setElemento( i, j, new NumeroRealVar( 0 ) );
                }
            }
        }
        
        return new NFuncResult( new NumericaMatrizVar( mat ) );
    }

    @Override
    public int getQuantParams() {
        return QUANT_PARAMS;
    }

    @Override
    public String getNome() {
        return NFuncFactory.IDENTIDADE;
    }
    
    
}
