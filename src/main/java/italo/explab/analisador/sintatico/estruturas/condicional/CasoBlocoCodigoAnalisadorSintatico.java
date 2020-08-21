package italo.explab.analisador.sintatico.estruturas.condicional;

import italo.explab.ErroMSGs;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.analisador.sintatico.instrucoes.CMDsAnalisadorSintatico;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.PalavrasReservadas;

public class CasoBlocoCodigoAnalisadorSintatico implements AnalisadorSintatico {
    
    private final AnalisadorSintaticoManager manager;

    public CasoBlocoCodigoAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        this.manager = manager;
    }
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {        
        CMDsAnalisadorSintatico cmdsAnalisador = (CMDsAnalisadorSintatico)manager.getCMDsAnalisador();
        
        int j = 0;                
        
        while( codigo.isCHValido( i+j ) ) {
            j += manager.getContUtil().contaEsps( codigo, i+j );
            
            if ( codigo.getSEGCH( i+j ) == '}' )
                return new AnaliseResult( j );            
            
            int cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.CASO );
            if ( cont == 0 )
                cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.PADRAO );                        
            
            if ( cont > 0 )
                return new AnaliseResult( j );
                           
            AnaliseResult result = cmdsAnalisador.getPareAnalisador().analisa( codigo, i+j );
            if ( result.getJ() > 0 ) {
                j += result.getJ();
                j += manager.getContUtil().contaEspsEPontoEVirgulas( codigo, i+j );
                return new AnaliseResult( j );
            }            
            
            result = manager.getInstrucaoAnalisador().analisa( codigo, i+j );
            if ( result.getJ() == 0 ) {
                if ( result.getErro() != null )
                    return result;
                
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.INSTRUCAO_DESCONHECIDA );
                return new AnaliseResult( erro );
            }                                     
            
            j += result.getJ();                      
        }

        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_CHAVES_ESPERADO );
        return new AnaliseResult( erro );        
    }
    
}
