package italo.explab.analisador.sintatico.cmd;

import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.PalavrasReservadas;

public class ExibaLNAnalisadorSintatico implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;

    public ExibaLNAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        this.manager = manager;
    }
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int j = 0;        
            
        j += manager.getContUtil().contaEsps( codigo, i+j );
        int cont = manager.getContUtil().contaTextoValor( codigo, i+j, PalavrasReservadas.EXIBALN );
        if ( cont == 0 )
            return new AnaliseResult();
        
        j += cont;
        int jj = manager.getContUtil().contaEsps( codigo, i+j );        
        char ch = codigo.getSEGCH( i+j );
        if ( jj == 0 && i+j < codigo.getCodlen() && ch != ';' )
            return new AnaliseResult();
                
        j += jj;
        

        if ( ch != ';' ) {
            AnaliseResult result = manager.getTalvezSemStrIniStringExpAnalisador().analisa( codigo, i+j );
            if ( result.getJ() == 0 )
                return result;                     
                            
            j += result.getJ();            
        }
        
        return new AnaliseResult( j );
    }
    
}

