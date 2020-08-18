package italo.explab.analisador.sintatico.construtor;

import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.PalavrasReservadas;

public class SuperChamadaConstrutorAnalisadorSintatico extends AbstractChamadaConstrutorAnalisadorSintatico {

    public SuperChamadaConstrutorAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        super( manager );
    }

    @Override
    protected String getPalavraReservada() {
        return PalavrasReservadas.SUPER;
    }

}
