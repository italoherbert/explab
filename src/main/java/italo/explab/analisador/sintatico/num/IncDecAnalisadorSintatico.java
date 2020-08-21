package italo.explab.analisador.sintatico.num;

import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;

public class IncDecAnalisadorSintatico  implements AnalisadorSintatico {

    private final AnalisadorSintaticoManager manager;

    public IncDecAnalisadorSintatico(AnalisadorSintaticoManager manager) {
        this.manager = manager;
    }
        
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        AnaliseResult result = manager.getAntIncDecAnalisador().analisa( codigo, i );
        if ( result.getJ() == 0 )
            result = manager.getPosIncDecAnalisador().analisa( codigo, i );
        return result;        
    }
    
}
