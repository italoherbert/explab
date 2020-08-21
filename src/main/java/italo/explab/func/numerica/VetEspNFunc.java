package italo.explab.func.numerica;

import italo.explab.ErroMSGs;
import italo.explab.var.num.NumericaVar;
import italo.explab.var.num.RealVar;
import italo.explab.var.mat.NumericaMatrizVar;

public class VetEspNFunc implements NFunc {

    private final int QUANT_PARAMS = 3;
    
    @Override
    public NFuncResult exec( NumericaVar... params ) {
        NFuncErro erro = this.valida( params );
        if ( erro != null )
            return new NFuncResult( erro );
                
        double p1 = ((RealVar)params[0]).getValor();
        double p2 = ((RealVar)params[1]).getValor();
        
        int nesps = (int) ((RealVar)params[2]).getValor();
                
        double[][] vet = new double[1][nesps];
        if ( nesps > 1 ) {
            double inc = (p2-p1) / ( nesps-1 );
            for( int i = 0; i < nesps; i++ )
                vet[ 0 ][ i ] = p1 + ( inc * i );        
        } else {
            vet[0][0] = p1;
        }
        
        return new NFuncResult( new NumericaMatrizVar( vet ) );
    }
    
    private NFuncErro valida( NumericaVar... params ) {
        if ( params.length != QUANT_PARAMS )
            return new NFuncErro( this.getClass(), ErroMSGs.PARAMS_QUANT_INVALIDA, String.valueOf( QUANT_PARAMS ) );
        
        NumericaVar p1 = params[0];                
        NumericaVar p2 = params[1];
        NumericaVar p3 = params[2];
        
        if ( p1.getTipo() != NumericaVar.REAL )
            return new NFuncErro( this.getClass(), 1, ErroMSGs.OPER_TIPO_REAL_ESPERADO );
        if ( p2.getTipo() != NumericaVar.REAL )
            return new NFuncErro( this.getClass(), 2, ErroMSGs.OPER_TIPO_REAL_ESPERADO );
        if ( p3.getTipo() != NumericaVar.REAL )
            return new NFuncErro( this.getClass(), 3, ErroMSGs.OPER_TIPO_REAL_ESPERADO );
                
        int nesps = (int) ((RealVar)params[2]).getValor();
        if ( nesps < 1 )
            return new NFuncErro( this.getClass(), 3, "func.param.maior.que.zero.esperado" );
        double n1 = ((RealVar)params[0]).getValor();
        double n2 = ((RealVar)params[1]).getValor();
        if ( n1 > n2 )
            return new NFuncErro( this.getClass(), 1, "func.param.n1.menor.ou.igual.a.n2.esperado", String.valueOf( n1 ), String.valueOf( n2 ) );
        
        return null;
    }

    @Override
    public int getQuantParams() {
        return QUANT_PARAMS;
    }

    @Override
    public String getNome() {
        return NFuncFactory.VET_ESP;
    }
    
}
