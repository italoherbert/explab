package italo.explab.analisador.sintatico;

import italo.explab.ErroMSGs;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;

public class ParametrosAnalisadorSintatico implements AnalisadorSintatico {
    
    private final AnalisadorSintaticoManager manager;

    public ParametrosAnalisadorSintatico(AnalisadorSintaticoManager manager) {
        this.manager = manager;
    }
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int j = 0;
        int charI = 0;
        char ch;
        
        ch = codigo.getSEGCH( i+j );
        if ( ch != '(' )          
            return new AnaliseResult();

        boolean ehParametros = false;
        
        while( codigo.isCHValido( i+j ) ) {            
            j += manager.getContUtil().contaEsps( codigo, i+j );
            switch ( charI ) {                
                case 0:                             
                    ehParametros = true;

                    int k = j+1;
                    k += manager.getContUtil().contaEsps( codigo, i+k );
                    if ( codigo.getSEGCH( i+k ) == ')' ) {
                        return new AnaliseResult( k+1 );
                    } else {
                        charI = 1;
                    }                                       
                    break;
                case 1:
                    AnaliseResult result = manager.getValorAnalisador().analisa( codigo, i+j );
                    if ( result.getJ() == 0 ) {
                        return result;
                    } else {                        
                        j += result.getJ()-1;
                        charI = 2;
                    }
                    break;
                case 2:
                    ch = codigo.getSEGCH( i+j );
                    if ( ch == ',' || ch == ')' ) {
                        if ( ch == ')' ) {
                            return new AnaliseResult( j+1 );
                        } else {
                            charI = 1;
                        }
                    } else {
                        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, "fecha.parenteses.ou.virgula.esperado" );
                        return new AnaliseResult( erro );
                    }
                    break;                
            }
            j++;
        }
        
        
        if ( ehParametros ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_PARENTESES_ESPERADO );
            return new AnaliseResult( erro );
        }
        
        return new AnaliseResult();
    }

}
