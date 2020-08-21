package italo.explab.analisador.sintatico.mat;

import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;

public class RealMatrizAnalisadorSintatico extends AbstractMatrizAnalisadorSintatico {
   
    public RealMatrizAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        super( manager );
    }

    @Override
    protected AnalisadorSintatico getELValorAnalisador() {
        return manager.getNumericaExpAnalisador();
    }
    
}
