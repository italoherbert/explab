package italo.explab.analisador.sintatico.string;

import italo.explab.ErroMSGs;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;

public class TalvezSemStrIniStringExpAnalisadorSintatico implements AnalisadorSintatico {
    
    private final AnalisadorSintaticoManager manager;

    public TalvezSemStrIniStringExpAnalisadorSintatico(AnalisadorSintaticoManager manager) {
        this.manager = manager;
    }

    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        return this.analisa( codigo, i, false );
    }
    
    public AnaliseResult analisa( Codigo codigo, int i, boolean esperadoFechaParenteses ) {
        int j = 0;
        boolean valorEsperado = false;
        char ch;

        boolean fim = false;
        while( !fim && codigo.isCHValido( i+j ) ) {
            j += manager.getContUtil().contaEsps( codigo, i+j );
                       
            AnaliseResult result = manager.getVarOuChamadaFuncAnalisador().analisa( codigo, i+j ); 
            if ( result.getJ() == 0 )
                result = manager.getValorNaoStringExpAnalisador().analisa( codigo, i+j );                        
            
            int k = j;
            
            if ( result.getJ() == 0 ) {
                ch = codigo.getSEGCH( i+j );
                if ( ch == '(' ) {        
                    j++;
                    result = this.analisa( codigo, i+j, true );
                    if ( result.getJ() == 0 )
                        return result;                    

                    j += result.getJ();                    
                    j += manager.getContUtil().contaEsps( codigo, i+j );
                    
                    if ( codigo.getSEGCH( i+j ) != ')' ) {
                        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_PARENTESES_ESPERADO );
                        return new AnaliseResult( erro );
                    }
                                        
                    j++; 
                    k = j;
                } else {                    
                    if ( result.getErro() != null )
                        return result;
                    
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.VALOR_ESPERADO );
                    return new AnaliseResult( erro );                    
                }
            } else {
                j += result.getJ();
                k = j;
                j += manager.getContUtil().contaEsps( codigo, i+j );
            }
                        
            valorEsperado = false;
            ch = codigo.getSEGCH( i+j );
            switch ( ch ) {
                case '+':
                    valorEsperado = true;
                    j++;
                    break;                
                default:   
                    fim = true;
                    j = k;
                    break;
            }
        }
        
        if ( valorEsperado ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.VALOR_ESPERADO );
            return new AnaliseResult( erro );
        }
                
        return new AnaliseResult( j );
    }
    
}
