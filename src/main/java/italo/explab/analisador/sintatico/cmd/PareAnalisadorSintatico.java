package italo.explab.analisador.sintatico.cmd;

import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.PalavrasReservadas;

public class PareAnalisadorSintatico extends SimplesCMDAnalisadorSintatico {

    public PareAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        super( manager );
    }

    @Override
    protected String getPalavraReservada() {
        return PalavrasReservadas.PARE;
    }    
    
}
