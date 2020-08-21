package italo.explab.analisador.sintatico.cmd;

import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.PalavrasReservadas;

public class RetorneAnalisadorSintatico implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;

    public RetorneAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        this.manager = manager;
    }
    
    @Override
    public AnaliseResult analisa(Codigo codigo, int i) {
        int j = 0;      
                
        int cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.RETORNE );
        if ( cont == 0 )
            return new AnaliseResult();
                
        j += cont;
        j += manager.getContUtil().contaEsps( codigo, i+j );
                
        char ch = codigo.getSEGCH( i+j );
        if ( ch == ';' )  
            return new AnaliseResult( j );
        
                
        AnaliseResult result = manager.getValorAnalisador().analisa( codigo, i+j );
        if ( result.getJ() == 0 )
            return result;        
        
        j += result.getJ();
        j += manager.getContUtil().contaEsps( codigo, i+j );
                                
        return new AnaliseResult( j );
    }
            
}
