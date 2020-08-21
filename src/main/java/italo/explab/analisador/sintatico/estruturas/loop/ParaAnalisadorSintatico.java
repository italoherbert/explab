package italo.explab.analisador.sintatico.estruturas.loop;

import italo.explab.ErroMSGs;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.PalavrasReservadas;

public class ParaAnalisadorSintatico implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;

    public ParaAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        this.manager = manager;
    }
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int j = 0;
                
        int cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.PARA );
        if ( cont == 0 )
            return new AnaliseResult();
                
        j += cont;        
        j += manager.getContUtil().contaEsps( codigo, i+j );
        
        char ch = codigo.getSEGCH( i+j );
        if ( ch != '(' ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.ABRE_PARENTESES_ESPERADO );
            return new AnaliseResult( erro );
        }
                        
        j++;
        j += manager.getContUtil().contaEsps( codigo, i+j );
        
        AnaliseResult result = this.analisaIni( codigo, i+j );
        if ( result.getJ() == 0 )
            return result;
        
        j += result.getJ();
        j += manager.getContUtil().contaEsps( codigo, i+j );
        
        result = manager.getBoolExpAnalisador().analisa( codigo, i+j );
        if ( result.getJ() == 0 )
            return result;
        
        j += result.getJ();
        j += manager.getContUtil().contaEsps( codigo, i+j );
        ch = codigo.getSEGCH( i+j );
        if ( ch != ';' ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.PONTO_E_VIRGULA_ESPERADO );
            return new AnaliseResult( erro );
        }
        
        j++;
        j += manager.getContUtil().contaEsps( codigo, i+j );
        
        result = this.analiseIncs( codigo, i+j );
        if ( result.getJ() == 0 )
            return result;
        
        j += result.getJ();
        j += manager.getContUtil().contaEsps( codigo, i+j );
                
        if( codigo.getSEGCH( i+j ) == '{' ) {                
            result = manager.getBlocoCodigoAnalisador().analisa( codigo, i+j );
        } else {
            result = manager.getInstrucaoAnalisador().analisa( codigo, i+j );
        }
        
        if ( result.getJ() == 0 )
            return result;
        
        j += result.getJ();
                
        return new AnaliseResult( j );
    }
    
    private AnaliseResult analisaIni( Codigo codigo, int i ) {
        int j = 0;
            
        char ch = codigo.getSEGCH( i+j );
        if ( ch == ';' )
            return new AnaliseResult( j+1 );  
        
        while( codigo.isCHValido( i+j ) ) {                                  
            j += manager.getContUtil().contaEsps( codigo, i+j );
            AnaliseResult result = manager.getLeituraVarAnalisador().analisa( codigo, i+j );
            if ( result.getJ() == 0 )
                return result;
                        
            j += result.getJ();            
            j += manager.getContUtil().contaEsps( codigo, i+j );
            
            ch = codigo.getSEGCH( i+j );
            if ( ch == ',' || ch == ';' ) {
                if ( ch == ',' ) {
                    j++;              
                } else {
                    return new AnaliseResult( j+1 );
                }
            } else {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.VIRGULA_OU_PONTO_E_VIRGULA_ESPERADO );
                return new AnaliseResult( erro );
            }
        }
        
        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.PONTO_E_VIRGULA_ESPERADO );
        return new AnaliseResult( erro );
    }
    
    private AnaliseResult analiseIncs( Codigo codigo, int i ) {
        int j = 0;
                        
        char ch = codigo.getCH( i+j );
        if ( ch == ')' )
            return new AnaliseResult( j+1 );  
        
        while( codigo.isCHValido( i+j ) ) {            
            j += manager.getContUtil().contaEsps( codigo, i+j );
            AnaliseResult result = manager.getIncDecAnalisador().analisa( codigo, i+j );
            if ( result.getJ() == 0 )
                result = manager.getLeituraNumVarAnalisador().analisa( codigo, i+j );
            if ( result.getJ() == 0 ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.INC_OU_ATRIB_INST_ESPERADA );
                return new AnaliseResult( erro ); 
            }
                        
            j += result.getJ();            
            j += manager.getContUtil().contaEsps( codigo, i+j );
            
            ch = codigo.getSEGCH( i+j );
            if ( ch == ',' || ch == ')' ) {
                if ( ch == ',' ) {
                    j++;              
                } else {
                    return new AnaliseResult( j+1 );
                }
            } else {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_PARENTESES_OU_VIRGULA_ESPERADO );
                return new AnaliseResult( erro );
            }
        }
        
        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_PARENTESES_ESPERADO );
        return new AnaliseResult( erro );
    }
    
}
