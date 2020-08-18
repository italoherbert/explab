package italo.explab.analisador.sintatico.valor;

import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.PalavrasReservadas;

public class ValorNaoStringExpAnalisador extends AbstractValorAnalisadorSintatico {
    
    public ValorNaoStringExpAnalisador( AnalisadorSintaticoManager manager ) {
       super( manager );
    }

    @Override
    protected AnaliseResult analisaEXPs(Codigo codigo, int i) {
        AnaliseResult boolResult = null;
        if ( codigo.getSEGCH( i ) == '(' ) {
            boolResult = manager.getBoolExpAnalisador().analisa( codigo, i );        
        } else {
            int cont = manager.getContUtil().contaTextoValor( codigo, i, PalavrasReservadas.VERDADE );
            if ( cont > 0 )
                return new AnaliseResult( cont );

            cont = manager.getContUtil().contaTextoValor( codigo, i, PalavrasReservadas.FALSO );
            if ( cont > 0 )
                return new AnaliseResult( cont );                
        }
        AnaliseResult numExpResult = manager.getNumericaExpAnalisador().analisa( codigo, i );        

        AnaliseResult maxJResult;
        if ( boolResult != null ) {
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
        
        return manager.getStringAnalisador().analisa( codigo, i );                           
    }
    
}
