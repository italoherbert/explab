package italo.explab.analisador.sintatico.num;

import italo.explab.ErroMSGs;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;

public class NumRealAnalisadorSintatico implements AnalisadorSintatico {

    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int j = 0; 
        boolean prim = true;
        boolean ehPonto = false;
        int contPontos = 0;
        boolean menos = false;
        boolean umalgarismo = false;
        
        while( codigo.isCHValido( i+j ) ) {
            char ch = codigo.getCH( i+j );
            
            switch( ch ) {
                case '-':
                    if ( !prim ) {
                        if ( umalgarismo ) {
                            return new AnaliseResult( j );                      
                        } else {
                            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.SINAL_MENOS_POS_INVALIDA );
                            return new AnaliseResult( erro );
                        }
                    }
                    menos = true;
                    ehPonto = false;
                    break;
                case '.':
                    if ( prim ) {
                        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.PONTO_EM_POS_INVALIDA );                        
                        return new AnaliseResult( erro );
                    } else if ( contPontos > 0 ) {
                        return new AnaliseResult( j );
                    }
                    contPontos++;  
                    ehPonto = true;                    
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    umalgarismo = true;
                    ehPonto = false;
                    break;
                default:
                    if ( ehPonto || ( menos && j == 1 ) ) {
                        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.ALGARISMO_NUMERICO_ESPERADO );
                        return new AnaliseResult( erro );       
                    }
                    return new AnaliseResult( j );                     
            }
            j++;
            prim = false;
        }
        
        if ( ehPonto || ( menos && j == 1 ) ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.ALGARISMO_NUMERICO_ESPERADO );
            return new AnaliseResult( erro );
        }
        
        return new AnaliseResult( j );
    }
    
}
