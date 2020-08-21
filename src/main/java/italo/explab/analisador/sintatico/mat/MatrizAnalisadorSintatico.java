package italo.explab.analisador.sintatico.mat;

import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;

public class MatrizAnalisadorSintatico extends AbstractMatrizAnalisadorSintatico {
   
    public MatrizAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        super( manager );
    }

    @Override
    protected AnalisadorSintatico getELValorAnalisador() {
        return manager.getValorAnalisador();
    }
    
}
