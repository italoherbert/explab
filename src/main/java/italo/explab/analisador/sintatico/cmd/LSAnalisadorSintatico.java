package italo.explab.analisador.sintatico.cmd;

import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.PalavrasReservadas;

public class LSAnalisadorSintatico implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;
    
    public LSAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        this.manager = manager;
    }
    
    @Override
    public AnaliseResult analisa(Codigo codigo, int i) {
        int j = 0;
        
        j += manager.getContUtil().contaEsps( codigo, i+j );
                
        int cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.LS );
        if ( cont == 0 )
            return new AnaliseResult();
        
        j += cont;
        
        int jj = manager.getContUtil().contaEsps( codigo, i+j );        
        if ( jj == 0 && i+j < codigo.getCodlen() )
            return new AnaliseResult();
        
        return new AnaliseResult( j );
    }
    
}
