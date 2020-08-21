package italo.explab.analisador.sintatico.estruturas;

import italo.explab.ErroMSGs;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;

public class BlocoCodigoAnalisadorSintatico implements AnalisadorSintatico {
    
    private final AnalisadorSintaticoManager manager;

    public BlocoCodigoAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        this.manager = manager;
    }
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        if ( codigo.getSEGCH( i ) != '{' ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i, ErroMSGs.ABRE_CHAVES_ESPERADO );
            return new AnaliseResult( erro );
        }
        
        int j = 1;
        
        while( codigo.isCHValido( i+j ) ) {
            j += manager.getContUtil().contaEsps( codigo, i+j );
            
            if ( codigo.getSEGCH( i+j ) == '}' )
                return new AnaliseResult( j+1 );            
                        
            AnaliseResult result = manager.getInstrucaoAnalisador().analisa( codigo, i+j );
            if ( result.getJ() == 0 )
                return result;            
            
            j += result.getJ();            
        }

        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_CHAVES_ESPERADO );
        return new AnaliseResult( erro );        
    }
    
}
