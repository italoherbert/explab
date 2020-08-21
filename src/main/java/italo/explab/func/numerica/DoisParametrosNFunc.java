package italo.explab.func.numerica;

import italo.explab.ErroMSGs;
import italo.explab.var.mat.MatrizVar;
import italo.explab.var.num.NumericaVar;
import italo.explab.var.num.NumeroRealVar;
import italo.explab.var.num.RealVar;
import italo.explab.var.mat.NumericaMatrizVar;

public abstract class DoisParametrosNFunc implements NFunc {

    private final int QUANT_PARAMS = 2;
    
    public abstract double calcula( double p1, double p2 );
    
    public boolean isDivisaoPorZero( double p2 ) {
        return false;
    }

    @Override
    public NFuncResult exec( NumericaVar... params ) {                
        NFuncErro erro = this.valida( params );
        if ( erro != null )
            return new NFuncResult( erro );
        
        NFuncResult result;
        boolean divisaoPorZero = false;
                
        NumericaVar p1 = params[0];
        NumericaVar p2 = params[1];
        if ( p1.getTipo() == NumericaVar.REAL && p2.getTipo() == NumericaVar.REAL ) {            
            double v = this.calcula( ((RealVar)p1).getValor(), ((RealVar)p2).getValor() );            
            
            divisaoPorZero = this.isDivisaoPorZero( ((RealVar)p2).getValor() ); 
            result = new NFuncResult( new NumeroRealVar( v ) );
        } else {                        
            MatrizVar mat;
            if ( p1.getTipo() == NumericaVar.MATRIZ && p2.getTipo() == NumericaVar.REAL )  {
                int nl = ((MatrizVar)p1).getNL();
                int nc = ((MatrizVar)p1).getNC();
                mat = new MatrizVar( nl, nc );
                        
                double v2 = ((RealVar)p2).getValor();

                for( int k = 0; k < nl; k++ ) {
                    for( int k2 = 0; k2 < nc; k2++ ) {
                        double m1_el = ((RealVar)((MatrizVar)p1).getElemento( k, k2 )).getValor();
                        double res = this.calcula( m1_el, v2 );
                        mat.setElemento(k, k2, new NumeroRealVar( res ) );
                        
                        if ( !divisaoPorZero )
                            divisaoPorZero = this.isDivisaoPorZero( v2 );
                    }
                }
            } else if ( p1.getTipo() == NumericaVar.REAL && p2.getTipo() == NumericaVar.MATRIZ )  {
                double v1 = ((RealVar)p1).getValor();

                int nl = ((MatrizVar)p2).getNL();
                int nc = ((MatrizVar)p2).getNC();                
                mat = new MatrizVar( nl, nc );
                
                for( int k = 0; k < nl; k++ ) {
                    for( int j = 0; j < nc; j++ ) {
                        double m2_el = ((RealVar)((MatrizVar)p2).getElemento( k, j )).getValor();
                        double res = this.calcula( v1, m2_el );
                        mat.setElemento( k, j, new NumeroRealVar( res ) );
                        
                        if ( !divisaoPorZero )
                            divisaoPorZero = this.isDivisaoPorZero( m2_el );
                    }
                }
            } else {
                int nl = ((MatrizVar)p1).getNL();
                int nc = ((MatrizVar)p1).getNC();
                
                mat = new MatrizVar( nl, nc );
                for( int k = 0; k < nl; k++ ) {
                    for( int j = 0; j < nc; j++ ) {
                        double m1_el = ((RealVar)((MatrizVar)p1).getElemento( k, j )).getValor();
                        double m2_el = ((RealVar)((MatrizVar)p2).getElemento( k, j )).getValor();
                        double res = this.calcula( m1_el, m2_el );
                        mat.setElemento(k, j, new NumeroRealVar( res ) );
                        
                        if ( !divisaoPorZero )
                            divisaoPorZero = this.isDivisaoPorZero( m2_el );
                    }
                }
            }
            result = new NFuncResult( new NumericaMatrizVar( mat ) );
        }        
        result.setDivisaoPorZero( divisaoPorZero );
        return result;
    }

    @Override
    public int getQuantParams() {
        return QUANT_PARAMS;
    }
   
    private NFuncErro valida(NumericaVar... params) {                
        if ( params.length != QUANT_PARAMS )
            return new NFuncErro( this.getClass(), ErroMSGs.PARAMS_QUANT_INVALIDA, String.valueOf( QUANT_PARAMS ) );
        NumericaVar p1 = params[0];
        NumericaVar p2 = params[1];
        if ( p1.getTipo() == NumericaVar.MATRIZ && p2.getTipo() == NumericaVar.MATRIZ ) {
            if ( ((MatrizVar)p1).getNL() != ((MatrizVar)p2).getNL() || ((MatrizVar)p1).getNC() != ((MatrizVar)p2).getNC() ) {
                String nl1 = String.valueOf( ((MatrizVar)p1).getNL() );
                String nc1 = String.valueOf( ((MatrizVar)p1).getNC() );
                String nl2 = String.valueOf( ((MatrizVar)p2).getNL() );
                String nc2 = String.valueOf( ((MatrizVar)p2).getNC() );
                return new NFuncErro( this.getClass(), 1, ErroMSGs.MATRIZES_DIMS_INCOMPATIVEIS, nl1, nc1, nl2, nc2 );
            }
        }
        return null;
    }
    
}
