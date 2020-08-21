package italo.explab.analisador.sintatico.mat;

import italo.explab.ErroMSGs;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;

public class MatrizIndicesAnalisadorSintatico implements AnalisadorSintatico {
    
    private final AnalisadorSintaticoManager analisador;
    
    public MatrizIndicesAnalisadorSintatico( AnalisadorSintaticoManager analisador ) {
        this.analisador = analisador;
    }
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int j = 0;       
        int charI = 0;
        char ch;
        while( codigo.isCHValido( i+j ) ) {
            switch( charI ) {                
                case 0:
                    ch = codigo.getCH( i+j );
                    if ( ch == '(' ) {
                        charI = 1;
                    } else {
                        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.ABRE_PARENTESES_ESPERADO );
                        return new AnaliseResult( erro ); 
                    }                        
                    break;
                case 1:
                    AnaliseResult result = analisador.getNumericaExpAnalisador().analisa( codigo, i+j );
                    if ( result.getJ() > 0 ) {                        
                        j += result.getJ()-1;
                        charI = 2;
                    } else {
                        j += analisador.getContUtil().contaEsps( codigo, i+j );
                        ch = codigo.getSEGCH( i+j );
                        if ( ch == ':' ) {
                            charI = 2;    
                        } else {
                            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.MATRIZ_INDICE_VALIDO_ESPERADO );
                            return new AnaliseResult( erro ); 
                        }
                    }
                    break;        
                case 2:
                    j += analisador.getContUtil().contaEsps( codigo, i+j );
                    ch = codigo.getSEGCH( i + j );
                    switch ( ch ) {
                        case ')':
                            return new AnaliseResult( j+1 );
                        case ',':
                            charI = 1;
                            break;
                        default:
                            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_PARENTESES_OU_VIRGULA_ESPERADO );
                            return new AnaliseResult( erro ); 
                    }
                    break;
            }
            j++;
        }
        return new AnaliseResult();
    }
    
}
