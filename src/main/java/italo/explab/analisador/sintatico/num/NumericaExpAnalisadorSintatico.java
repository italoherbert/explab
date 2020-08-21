package italo.explab.analisador.sintatico.num;

import italo.explab.ErroMSGs;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;

public class NumericaExpAnalisadorSintatico implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;

    public NumericaExpAnalisadorSintatico(AnalisadorSintaticoManager manager) {
        this.manager = manager;
    }
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        return this.analisaNumExp( codigo, i, false );
    }
    
    private NumAnaliseResult analisaNumExp( Codigo codigo, int i, boolean esperadoFechaParenteses ) {
        int j = 0;
        int ultJ = 0;
        char ch;

        j += manager.getContUtil().contaEsps( codigo, i+j );
        
        if ( codigo.getSEGCH( i+j ) == '-' && codigo.getSEGCH( i+j+1 ) != '-' )
            j++;       
                
        int cont = 0;
        boolean varOuFuncEncontrada = false;
        
        boolean prim = true;
        boolean operandoEsperado = false;
        boolean apenasUmaFuncaoEntreParenteses = false;
        
        boolean fim = false;
        while( !fim && codigo.isCHValido( i+j ) ) {
            j += manager.getContUtil().contaEsps( codigo, i+j );
            
            boolean ehSubExp = false;
                        
            AnaliseResult result;
            if ( codigo.getSEGCH( i+j )== '(' ) {
                j++;
                NumAnaliseResult expResult = this.analisaNumExp( codigo, i+j, true );                
                apenasUmaFuncaoEntreParenteses = expResult.isApenasUmaVarOuFuncEntreParenteses();
                
                ehSubExp = true;                
                result = expResult;
            } else {
                result = manager.getIncDecAnalisador().analisa( codigo, i+j );                
                if ( result.getJ() == 0 )
                    result = manager.getLeituraNumVarAnalisador().analisa( codigo, i+j );                
                if ( result.getJ() == 0 )
                    result = manager.getNumRealAnalisador().analisa( codigo, i+j );                                    
                if ( result.getJ() == 0 )
                    result = manager.getRealMatAnalisador().analisa( codigo, i+j );                
                if ( result.getJ() == 0 )
                    result = manager.getVetN1IncN2Analisador().analisa( codigo, i+j );                                
                if ( result.getJ() == 0 ) {
                    result = manager.getVarOuChamadaFuncAnalisador().analisa( codigo, i+j );                                                    
                    if ( result.getJ() > 0 )
                        varOuFuncEncontrada = true;                    
                }
            }
            
            if ( result.getJ() == 0 ) {
                if ( ehSubExp ) {
                    return new NumAnaliseResult( result.getErro() );
                } else {
                    if ( prim ) {                        
                        return new NumAnaliseResult(); 
                    } else {
                        fim = true;
                        continue;
                    }
                }
            } else {    
                cont++;
                
                operandoEsperado = false;
                
                j += result.getJ();      
                
                ultJ = j;
                j += manager.getContUtil().contaEsps( codigo, i+j );
                ch = codigo.getSEGCH( i+j );
                if ( ch == '\'' ) {
                    j++;
                    ultJ = j;
                }
                
                switch( ch ) {
                    case '+':
                    case '-':
                        operandoEsperado = ( ch == '-' );
                        break;
                    case '*':
                        if ( codigo.getSEGCH( i+j+1 ) == '*' )
                            j++;                             
                        operandoEsperado = true;
                        break;
                    case '/':
                    case '%':                            
                    case '^':
                        operandoEsperado = true;
                        break;
                    case ')':
                        if ( esperadoFechaParenteses ) {
                            return new NumAnaliseResult( j+1, cont == 1 && ( varOuFuncEncontrada | apenasUmaFuncaoEntreParenteses ) );                            
                        } else {
                            return new NumAnaliseResult( ultJ, cont == 1 && ( varOuFuncEncontrada | apenasUmaFuncaoEntreParenteses ) );
                        }
                    default:
                        fim = true;
                        continue;
                } 
            }
            j++;
            prim = false;
        }
                
        if ( esperadoFechaParenteses ) {
            return new NumAnaliseResult();
        } else {
            if ( operandoEsperado ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.OPERANDO_NUMERICO_ESPERADO );
                return new NumAnaliseResult( erro );            
            }
        }
                
        return new NumAnaliseResult( ultJ, cont == 1 && ( varOuFuncEncontrada | apenasUmaFuncaoEntreParenteses ) );
    }    

    class NumAnaliseResult extends AnaliseResult {        
        boolean apenasUmaVarOuFuncEntreParenteses = false;

        public NumAnaliseResult() {}
        
        public NumAnaliseResult( int j, boolean apenasUmaVarOuFuncEntreParenteses ) {
            super( j );          
            this.apenasUmaVarOuFuncEntreParenteses = apenasUmaVarOuFuncEntreParenteses;            
        }

        public NumAnaliseResult( CodigoErro erro ) {
            super( erro );
        }

        public boolean isApenasUmaVarOuFuncEntreParenteses() {
            return apenasUmaVarOuFuncEntreParenteses;
        }
        
    }
    
}
