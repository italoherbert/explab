package italo.explab.analisador.sintatico.mat;

import italo.explab.ErroMSGs;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;

public class VetN1IncN2AnalisadorSintatico  implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;
    
    public VetN1IncN2AnalisadorSintatico( AnalisadorSintaticoManager analisador ) {
        this.manager = analisador;
    }
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int j = 0;
        int charI = 0;
        char ch;
        int ncont = 0;
        while( codigo.isCHValido( i+j ) ) {
            switch( charI ) {
                case 0:
                    ch = codigo.getCH( i+j ); 
                    if ( ch == '[' ) {
                        charI = 1;
                    } else {
                        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, "abra.colchetes.esperado" );
                        return new AnaliseResult( erro );
                    }
                    break;
                case 1:
                    AnaliseResult result = manager.getNumericaExpAnalisador().analisa( codigo, i+j );
                    if ( result.getJ() > 0 ) {                        
                        j += result.getJ()-1;
                        ncont++;
                        if ( ncont >= 3 ) {
                            charI = 3;
                        } else {
                            charI = 2;
                        }
                    } else {
                        return result;
                    }
                    break;
                case 2:
                    j += manager.getContUtil().contaEsps( codigo, i+j );
                    ch = codigo.getSEGCH( i+j );                    
                    if ( ch == ':' ) {
                        charI = 1;
                    } else {
                        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.DOIS_PONTOS_ESPERADO );
                        return new AnaliseResult( erro );
                    }
                    break;
                case 3:
                    j += manager.getContUtil().contaEsps( codigo, i+j );
                    ch = codigo.getSEGCH( i+j );                    
                    if ( ch == ']' ) {
                        return new AnaliseResult( j+1 );
                    } else {
                        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_COLCHETES_ESPERADO );
                        return new AnaliseResult( erro );
                    }                
            }
            j++;
        }
        
        return new AnaliseResult();
    }
    
}
