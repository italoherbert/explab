package italo.explab.analisador.sintatico.valor;

import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;

public class ValorAnalisadorSintatico extends AbstractValorAnalisadorSintatico {
    
    public ValorAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
       super( manager );
    }

    @Override
    protected AnaliseResult analisaEXPs(Codigo codigo, int i) {        
        AnaliseResult boolResult = manager.getBoolExpAnalisador().analisa( codigo, i );        
        AnaliseResult numExpResult = manager.getNumericaExpAnalisador().analisa( codigo, i );            
        
        AnaliseResult maxJResult;
        if ( boolResult.getJ() > 0 ) {
            maxJResult = ( boolResult.getJ() > numExpResult.getJ() ? boolResult : numExpResult );
        } else {
            maxJResult = numExpResult;
        }     

        if ( maxJResult.getJ() > 0 )
            return maxJResult;    
                                     
        if ( numExpResult.getErro() != null ) {
            return numExpResult;
        } else {
            if ( boolResult != null )
                if ( boolResult.getErro() != null )
                    return boolResult;
        }
                
        return manager.getStringExpAnalisador().analisa( codigo, i );                                    
    }
    
}
