package italo.explab.analisador.sintatico.string;

import italo.explab.ErroMSGs;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;

public class StringAnalisadorSintatico implements AnalisadorSintatico {
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int j = 0;
        
        boolean apostrofoEsperado;
        
        boolean eh_inicio_string = true;
        if ( i-1 > 0 ) {
            boolean achou = false;
            int k = i-1;
            while ( k >= 0 && !achou ) {
                char ch = codigo.getSEGCH( k );
                switch( ch ) {
                    case ' ':
                    case '\r':
                    case '\t':
                    case '\n':
                        k--;
                        break;
                    case ']':
                    case ')':
                        eh_inicio_string = false;
                        achou = true;
                        break;
                    default:
                        achou = true;
                        break;
                }
            }
        }
        
        if ( !eh_inicio_string )
            return new AnaliseResult();
        
        char ch = codigo.getSEGCH( i+j );
        switch ( ch ) {                                    
            case '\"':
                apostrofoEsperado = false;
                break;
            case '\'':
                apostrofoEsperado = true;
                break;
            default:
                return new AnaliseResult();
        }
        j++;
                
        while( codigo.isCHValido( i+j ) ) {
            ch = codigo.getCH( i+j );
            switch( ch ) {         
                case '\\':
                    j++;
                    break;
                case '\"':
                    if ( apostrofoEsperado ) {
                        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.APOSTROFO_ESPERADO );
                        return new AnaliseResult( erro );
                    }
                    return new AnaliseResult( j+1 );
                case '\'':
                    if ( !apostrofoEsperado ) {
                        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.ASPAS_DUPLAS_ESPERADAS );
                        return new AnaliseResult( erro );
                    }
                    return new AnaliseResult( j+1 );
            }
            j++;
        }
        
        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.ASPAS_DUPLAS_ESPERADAS );
        return new AnaliseResult( erro );
    }
    
}
