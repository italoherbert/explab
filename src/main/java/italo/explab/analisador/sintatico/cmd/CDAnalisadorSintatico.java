package italo.explab.analisador.sintatico.cmd;

import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.PalavrasReservadas;

public class CDAnalisadorSintatico implements AnalisadorSintatico {

    protected final AnalisadorSintaticoManager manager;

    public CDAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        this.manager = manager;
    }
        
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int j = 0;        
                    
        j += manager.getContUtil().contaEsps( codigo, i+j );
        int cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.CD );
        if ( cont == 0 )
            return new AnaliseResult();
        
        j += cont;
        int jj = manager.getContUtil().contaEsps( codigo, i+j );        
        if ( jj == 0 && i+j < codigo.getCodlen() )
            return new AnaliseResult();
                
        j += jj;
        
        AnaliseResult result = manager.getStringExpAnalisador().analisa( codigo, i+j );
        if ( result.getJ() > 0 ) {
            cont = result.getJ();
        } else {                    
            cont = manager.getContUtil().contaSequenciaCHs( codigo, i+j, true, ' ', '\r', '\t', '\n', ';' );                    
        }
        
        j += cont;
        
        return new AnaliseResult( j );
    }   
    
}
