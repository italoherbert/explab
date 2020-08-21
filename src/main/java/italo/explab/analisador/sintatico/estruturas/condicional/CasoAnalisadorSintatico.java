package italo.explab.analisador.sintatico.estruturas.condicional;

import italo.explab.ErroMSGs;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.PalavrasReservadas;

public class CasoAnalisadorSintatico implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;

    public CasoAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        this.manager = manager;
    }
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int j = 0;
                
        int cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.COMPARE );
        if ( cont == 0 )
            return new AnaliseResult();                
        
        j += cont;        
        j += manager.getContUtil().contaEsps( codigo, i+j );
        
        boolean esperaFechaParenteses = false;
        if ( codigo.getSEGCH( i+j ) == '(' ) {
            j++;
            j += manager.getContUtil().contaEsps( codigo, i+j );
            esperaFechaParenteses = true;
        }
        
        AnaliseResult result = manager.getValorAnalisador().analisa( codigo, i+j );
        if ( result.getJ() == 0 ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.VALOR_ESPERADO );
            return new AnaliseResult( erro );
        }
        
        j += result.getJ();
        j += manager.getContUtil().contaEsps( codigo, i+j );
        
        
        if ( esperaFechaParenteses ) {
            if ( codigo.getSEGCH( i+j ) == ')' ) {
                j++;
                j += manager.getContUtil().contaEsps( codigo, i+j );
            } else {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_PARENTESES_ESPERADO );
                return new AnaliseResult( erro );
            }
        }
        
        char ch = codigo.getSEGCH( i+j );
        if ( ch != '{' ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.ABRE_CHAVES_ESPERADO );
            return new AnaliseResult( erro );
        }
               
        j++;
        j += manager.getContUtil().contaEsps( codigo, i+j );
        
        boolean prim = true;
        while( codigo.isCHValido( i+j ) ) {                      
            cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.PADRAO );
            if ( cont > 0 ) {
                if ( prim ) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.PALAVRA_RESERVADA_INESPERADA, PalavrasReservadas.PADRAO );
                    return new AnaliseResult( erro );
                }
                
                j += cont;
                j += manager.getContUtil().contaEsps( codigo, i+j );
                
                ch = codigo.getSEGCH( i+j );
                if ( ch != ':' ) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.DOIS_PONTOS_ESPERADO );
                    return new AnaliseResult( erro );
                }
                j++;
                j += manager.getContUtil().contaEsps( codigo, i+j );
                                  
                result = manager.getCasoBlocoCodigoAnalisador().analisa( codigo, i+j );                
                if ( result.getJ() == 0 )
                    return result;

                j += result.getJ();
                ch = codigo.getSEGCH( i+j );
                if ( ch != '}' ) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_CHAVES_ESPERADO );
                    return new AnaliseResult( erro );
                }
                return new AnaliseResult( j+1 ); 
            }
            
            j += manager.getContUtil().contaEsps( codigo, i+j );
            cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.CASO );
            if ( cont == 0 ) {
                String palavras = PalavrasReservadas.CASO +" ou "+PalavrasReservadas.PADRAO;
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.PALAVRAS_RESERVADAS_ESPERADAS, palavras );
                return new AnaliseResult( erro );
            }
            j += cont;
            j += manager.getContUtil().contaEsps( codigo, i+j );
                        
            result = manager.getValorAnalisador().analisa( codigo, i+j );
            if ( result.getJ() == 0 ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.VALOR_ESPERADO );
                return new AnaliseResult( erro );
            } 
            
            j += result.getJ();            
            j += manager.getContUtil().contaEsps( codigo, i+j );
                                   
            ch = codigo.getSEGCH( i+j );
            if ( ch != ':' ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.DOIS_PONTOS_ESPERADO );
                return new AnaliseResult( erro );
            }
            j++;
            j += manager.getContUtil().contaEsps( codigo, i+j );
            
            result = manager.getCasoBlocoCodigoAnalisador().analisa( codigo, i+j );            
            if ( result.getJ() == 0 )
                if ( result.getErro() != null )
                    return result;                                           

            j += result.getJ();            
            j += manager.getContUtil().contaEsps( codigo, i+j );
                        
            ch = codigo.getSEGCH( i+j );
            if ( ch == '}' )
                return new AnaliseResult( j+1 );                 
            
            prim = false;
        }
        
        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_CHAVES_ESPERADO );
        return new AnaliseResult( erro ); 
    }        
    
}
