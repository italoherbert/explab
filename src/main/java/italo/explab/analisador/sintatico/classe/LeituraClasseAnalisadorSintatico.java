package italo.explab.analisador.sintatico.classe;

import italo.explab.ErroMSGs;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.PalavrasReservadas;

public class LeituraClasseAnalisadorSintatico implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;

    public LeituraClasseAnalisadorSintatico(AnalisadorSintaticoManager manager) {
        this.manager = manager;
    }
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int cont = manager.getContUtil().contaTextoValor( codigo, i, PalavrasReservadas.CLASSE );
        if ( cont == 0 )
            return new AnaliseResult();                                 
        
        int j = cont;
        j += manager.getContUtil().contaEsps( codigo, i+j );
                
        cont = manager.getContUtil().contaClasseOuPacoteNomeTam( codigo, i+j );
        if ( cont == 0 ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.CLASSE_NOME_ESPERADO );
            return new AnaliseResult( erro ); 
        }
                
        j += cont;                                                                       
        j += manager.getContUtil().contaEsps( codigo, i+j );
        
        cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.EXTENDE );           
        if ( cont > 0 ) {
            j += cont;
            j += manager.getContUtil().contaEsps( codigo, i+j );
            
            cont = manager.getContUtil().contaClasseOuPacoteNomeTam( codigo, i+j );
            if ( cont == 0 ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.CLASSE_NOME_ESPERADO );
                return new AnaliseResult( erro ); 
            }
            
            j += cont;                                                                       
            j += manager.getContUtil().contaEsps( codigo, i+j );
        }
        
        char ch = codigo.getSEGCH( i+j );
        if ( ch != '{' ) {                
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.ABRE_CHAVES_ESPERADO ); 
            return new AnaliseResult( erro );
        }
        
        j++;
        j += manager.getContUtil().contaEsps( codigo, i+j );
        
        while( codigo.isCHValido( i+j ) ) {                    
            j += manager.getContUtil().contaEsps( codigo, i+j );
                
            ch = codigo.getSEGCH( i+j );
            if ( ch == '}' )
                return new AnaliseResult( j+1 );            
            
            cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.FUNC );
            if ( cont > 0 ) {
                AnaliseResult funcResult = manager.getLeituraFuncAnalisador().analisa( codigo, i+j );                    
                if ( funcResult.getJ() == 0 )
                    return funcResult;                             

                cont = funcResult.getJ();
            } else {
                cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.CONSTRUTOR );
                if ( cont > 0 ) {
                    AnaliseResult construtorResult = manager.getLeituraConstrutorAnalisador().analisa( codigo, i+j );                    
                    if ( construtorResult.getJ() == 0 )
                        return construtorResult;                    

                    cont = construtorResult.getJ();                                                                                           
                } else {
                    cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.CLASSE );
                    if ( cont > 0 ) {
                        AnaliseResult lclasseResult = this.analisa( codigo, i+j );
                        if ( lclasseResult.getJ() == 0 )
                            return lclasseResult;

                        cont = lclasseResult.getJ();
                    } 
                }
            }
            
            
            j += cont;

            if ( cont == 0 ) {                                                         
                boolean achouFimVarsBloco = false;
                while ( !achouFimVarsBloco && codigo.isCHValido( i+j ) ) {  
                    j += manager.getContUtil().contaEsps( codigo, i+j );
                    
                    ch = codigo.getSEGCH( i+j );
                    switch ( ch ) {
                        case '=':
                            j++;
                            j += manager.getContUtil().contaEsps( codigo, i+j );

                            cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.FUNC ); 
                            if ( cont > 0 ) {
                                j += cont;

                                cont = manager.getContUtil().contaSequenciaCHs( codigo, i+j, '{' );
                                if ( cont == 0 ) {
                                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j+cont, ErroMSGs.ABRE_CHAVES_ESPERADO );
                                    return new AnaliseResult( erro );
                                }
                                j += cont;
                                j++;

                                cont = manager.getContUtil().contaBlocoCodigoTam( codigo, i+j );
                                if ( cont == -1 ) {
                                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j+cont, ErroMSGs.FECHA_CHAVES_ESPERADO );
                                    return new AnaliseResult( erro );
                                }                                
                                j += cont + 1;                                                            }   
                            break;
                        case '}':
                            return new AnaliseResult( j+1 );                            
                        default:
                            cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.FUNC );
                            if ( cont == 0 )
                                cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.CONSTRUTOR );
                            if ( cont == 0 )
                                cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.CLASSE );
                            if ( cont > 0 ) {
                                achouFimVarsBloco = true;
                            } else {
                                j++;
                            }   
                            break;
                    }
                }            
            }            
        }
        
        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_CHAVES_ESPERADO );
        return new AnaliseResult( erro );
    }
    
}
