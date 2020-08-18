package italo.explab.analisador.sintatico.cmd;

import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.PalavrasReservadas;

public class ContinueAnalisadorSintatico extends SimplesCMDAnalisadorSintatico {

    public ContinueAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        super( manager );
    }

    @Override
    protected String getPalavraReservada() {
        return PalavrasReservadas.CONTINUE;
    }    
    
}

