package italo.explab.analisador.sintatico.bool;

import italo.explab.ErroMSGs;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.PalavrasReservadas;

public class BooleanExpAnalisadorSintatico implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;
    
    public BooleanExpAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        this.manager = manager;
    }

    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        return this.analisa( codigo, i, false );
    }
    
    public AnaliseResult analisa( Codigo codigo, int i, boolean espFechaParenteses ) {
        int j = 0;
        char ch;
        boolean opBoolEncontrado = false;
        
        boolean esperadoFechaParenteses = espFechaParenteses;
        
        boolean fim = false;
        while( !fim && codigo.isCHValido( i+j ) ) {
            j += manager.getContUtil().contaEsps( codigo, i+j );
            
            ch = codigo.getSEGCH( i+j );
            
            AnaliseResult result;
            switch ( ch ) {
                case '!':
                    opBoolEncontrado = true;
                    
                    result = this.analisaNotBoolComp( codigo, i+j );
                    if ( result.getJ() == 0 )
                        return result;
                    
                    j += result.getJ();
                    break;                
                case ')':
                    if ( esperadoFechaParenteses ) {
                        esperadoFechaParenteses = false;
                        j++;
                    } 
                    fim = true;
                    continue;
                default:
                    result = this.analisaBoolComp( codigo, i+j, opBoolEncontrado );
                    if ( result.getJ() == 0 ) {
                        int cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.VERDADE );
                        if ( cont == 0 ) 
                            cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.FALSO );
                        if ( cont == 0 ) {                            
                            if ( codigo.getCH( i+j ) == '(' ) {
                                j++;
                                result = this.analisa( codigo, i+j, true );
                                if ( result.getJ() == 0 )
                                    return result;                    
                                
                                j += result.getJ();
                            } else {
                                if ( result.getJ() == 0 && opBoolEncontrado ) {
                                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.OPERANDO_BOOLEANO_ESPERADO );
                                    return new AnaliseResult( erro );        
                                }
                                return result;
                            }                           
                        } else {
                            j += cont;
                        }
                    } else {
                        j += result.getJ();
                    }
                    break;
            }
                        
            int k = manager.getContUtil().contaEsps( codigo, i+j );
            ch = codigo.getSEGCH( i+j+k );
            switch( ch ) {
                case '&':
                case '|':
                case '#':
                    opBoolEncontrado = true;
                    j += k + 1;
                    break;
                case ')':
                    if ( esperadoFechaParenteses ) {
                        esperadoFechaParenteses = false;
                        j += k + 1;
                    }
                    fim = true;
                    break;
                case '!':
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j+k, ErroMSGs.CH_INVALIDO );
                    return new AnaliseResult( erro );                    
                default:                                                                                
                    fim = true;
                    break;
            }
        }
        
        if ( esperadoFechaParenteses ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_PARENTESES_ESPERADO );
            return new AnaliseResult( erro );        
        }
                
        return new AnaliseResult( j );
    }        
    
    private AnaliseResult analisaNotBoolComp( Codigo codigo, int i ) {
        int j = 0;
        if ( codigo.getSEGCH( i+j ) != '!' )
            return new AnaliseResult();
        
        j++;
        j += manager.getContUtil().contaEsps( codigo, i+j );
        if ( codigo.getSEGCH( i+j ) == '(' ) {
            j++;
            AnaliseResult result = this.analisa( codigo, i+j, true );
            if ( result.getJ() == 0 ) {
                return result;          
            } else {
                j += result.getJ();
                return new AnaliseResult( j );
            }
        } else {  
            int cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.VERDADE );
            if ( cont == 0 ) 
                cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.FALSO );
            if ( cont == 0 ) {
                AnaliseResult result = manager.getVarOuChamadaFuncAnalisador().analisa( codigo, i+j );
                if ( result.getJ() == 0 ) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.EXP_VAR_OU_VALOR_BOOL_EXPERADO );
                    return new AnaliseResult( erro );
                }
                cont = result.getJ();
            }            
            j += cont;
            return new AnaliseResult( j );
        }
    }
    
    private AnaliseResult analisaBoolComp( Codigo codigo, int i, boolean opBoolEncontrado ) {
        boolean op1EhVarOuConst;
        int cont = manager.getContUtil().contaTextoValor( codigo, i, PalavrasReservadas.VERDADE );
        if ( cont == 0 )
            cont = manager.getContUtil().contaTextoValor( codigo, i, PalavrasReservadas.FALSO );
        
        if ( cont == 0 ) {
            AnaliseResult result = manager.getValorNaoBooleanExpAnalisador().analisa( codigo, i );
            if ( result.getJ() == 0 ) {
                if ( result.getErro() != null )
                    return result;         
                
                if ( opBoolEncontrado ) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, i, ErroMSGs.VALOR_INVALIDO );
                    return new AnaliseResult( erro );
                } 
                return new AnaliseResult();
            }
             
            AnaliseResult result2 = manager.getVarOuChamadaFuncAnalisador().analisa( codigo, i );
            op1EhVarOuConst = ( result.getJ() == result2.getJ() );
            
            cont += result.getJ();
        } else {
            op1EhVarOuConst = true;
        }
                   
        int j = cont;
        int posVar = cont;
        j += manager.getContUtil().contaEsps( codigo, i+j );
                
        boolean instanciaDe = false;
        
        char ch = codigo.getSEGCH( i+j );                
        switch( ch ) {
            case '=':
                if ( codigo.getSEGCH( i+j+1 ) == '=' ) {
                    j+=2;
                } else {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j+1, ErroMSGs.IGUAL_ESPERADO );
                    return new AnaliseResult( erro );
                }
                break;
            case '<':                
            case '>':
                j++;
                if ( codigo.getSEGCH( i+j ) == '=' )
                    j++;
                break;
            case '!':
                if ( codigo.getSEGCH( i+j+1 ) == '=' ) {
                    j+=2;
                } else {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j+1, ErroMSGs.IGUAL_ESPERADO );
                    return new AnaliseResult( erro );
                }
                break;
            default:                                
                cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.INSTANCIADE );
                if ( cont == 0 ) {
                    if ( op1EhVarOuConst ) {
                        return new AnaliseResult( posVar );
                    } else {
                        if ( opBoolEncontrado ) {
                            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.OPERADOR_COMP_ESPERADO );
                            return new AnaliseResult( erro );
                        } else {
                            return new AnaliseResult();
                        }
                    }
                } else {
                    j += cont;
                    instanciaDe = true;
                }                
                break;
        }
        
        j += manager.getContUtil().contaEsps( codigo, i+j );
        
        if ( instanciaDe ) {
            cont = manager.getContUtil().contaClasseOuPacoteNomeTam( codigo, i+j );
            if ( cont == 0 ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.CLASSE_NOME_ESPERADO );
                return new AnaliseResult( erro );
            } else {
                j += cont;
            }
        } else {
            cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.VERDADE );
            if ( cont == 0 ) 
                cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.FALSO );
            if ( cont == 0 ) {
                AnaliseResult result = manager.getValorNaoBooleanExpAnalisador().analisa( codigo, i+j );
                if ( result.getJ() == 0 ) {
                    if ( result.getErro() != null )
                        return result;         

                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.VALOR_INVALIDO );
                    return new AnaliseResult( erro );
                }
                
                j += result.getJ();
            } else {
                j += cont;
            }
        }
                
        return new AnaliseResult( j );
    }
        
}
