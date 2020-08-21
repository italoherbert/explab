package italo.explab.analisador.sintatico.estruturas.condicional;

import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.PalavrasReservadas;

public class SeAnalisadorSintatico implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;

    public SeAnalisadorSintatico(AnalisadorSintaticoManager manager) {
        this.manager = manager;
    }
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int j = 0;
        char ch;
                
        int cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.SE );
        if ( cont == 0 )
            return new AnaliseResult();
        
        j += cont;        
        j += manager.getContUtil().contaEsps( codigo, i+j );
                
        AnaliseResult result = manager.getBoolExpAnalisador().analisa( codigo, i+j );
        if ( result.getJ() == 0 )
            return result;        
        
        j += result.getJ();
        j += manager.getContUtil().contaEsps( codigo, i+j );
        
        ch = codigo.getSEGCH( i+j );
        if ( ch == '{' ) {
            result = manager.getBlocoCodigoAnalisador().analisa( codigo, i+j );                              
        } else {
            result = manager.getInstrucaoAnalisador().analisa( codigo, i+j );
        }
        
        if ( result.getJ() == 0 )
            return result;

        j += result.getJ();
        j += manager.getContUtil().contaEsps( codigo, i+j );
        
        cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.SENAO );
        if ( cont > 0 ) {
            j += cont;
            j += manager.getContUtil().contaEsps( codigo, i+j );
            cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.SE );
            if ( cont != 0 ) {
                result = this.analisa( codigo, i+j );
                if ( result.getJ() == 0 )
                    return result;
                
                j += result.getJ();
            } else {            
                j += manager.getContUtil().contaEsps( codigo, i+j );

                ch = codigo.getSEGCH( i+j );
                if ( ch == '{' ) {            
                    result = manager.getBlocoCodigoAnalisador().analisa( codigo, i+j );
                } else {
                    result = manager.getInstrucaoAnalisador().analisa( codigo, i+j );
                }
                
                if ( result.getJ() == 0 )
                    return result;

                j += result.getJ();                
            }
        }
        
        return new AnaliseResult( j );
    }
    
}
