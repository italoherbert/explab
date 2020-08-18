package italo.explab.analisador.sintatico.cmd;

import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.PalavrasReservadas;

public class SairAnalisadorSintatico extends SimplesCMDAnalisadorSintatico {

    public SairAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        super( manager );
    }

    @Override
    protected String getPalavraReservada() {
        return PalavrasReservadas.SAIR;
    }    
    
}

