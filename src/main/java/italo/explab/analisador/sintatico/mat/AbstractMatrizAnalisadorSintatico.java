package italo.explab.analisador.sintatico.mat;

import italo.explab.ErroMSGs;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;

public abstract class AbstractMatrizAnalisadorSintatico implements AnalisadorSintatico {

    protected final AnalisadorSintaticoManager manager;
    
    public AbstractMatrizAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        this.manager = manager;
    }
    
    protected abstract AnalisadorSintatico getELValorAnalisador();
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int j = 0; 
        
        int charI = 0;
        int cont;
        char ch;
        while( codigo.isCHValido( i+j ) ) {
            switch ( charI ) {
                case 0:
                    ch = codigo.getCH( i+j );
                    if ( ch == '[' ) {
                        ch = codigo.getSEGCH( i+j+1 );
                        if ( ch == ']' )
                            return new AnaliseResult( j+2 );
                        charI = 1;
                    } else {
                        return new AnaliseResult();
                    }
                    break;
                case 1:
                    j += manager.getContUtil().contaEsps( codigo, i+j );
                    
                    AnaliseResult result = this.getELValorAnalisador().analisa( codigo, i+j );
                    if ( result.getJ() > 0 ) {
                        j += result.getJ()-1;
                        charI = 2;
                    } else {
                        ch = codigo.getSEGCH( i+j );
                        if ( ch == '[' ) {
                            result = this.analisa( codigo, i+j );
                            if ( result.getJ() == 0 ) {
                                return result;
                            } else {
                                j += result.getJ()-1;
                                charI = 2;
                            }
                        } else {
                            return result;
                        }                        
                    }                       
                    break;
                case 2:
                    cont = manager.getContUtil().contaEsps( codigo, i+j );
                    ch = codigo.getSEGCH( i+j+cont );
                    switch( ch ) {
                        case ',':
                        case ';':
                            j += cont;
                            charI = 1;
                            break;
                        case ']':
                            return new AnaliseResult( j+cont+1 );
                        default:
                            if ( cont > 0 ) {
                                j += cont-1;
                            } else {
                                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.ESPACO_ESPERADO );
                                return new AnaliseResult( erro ); 
                            }
                            charI = 1;
                            break;
                    }   
                    break;
            }
            j++;
        }
        
        return new AnaliseResult();
    }
    
}

