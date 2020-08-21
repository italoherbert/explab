package italo.explab.analisador.sintatico.construtor;

import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.PalavrasReservadas;

public class EsteChamadaConstrutorAnalisadorSintatico extends AbstractChamadaConstrutorAnalisadorSintatico {

    public EsteChamadaConstrutorAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        super( manager );
    }

    @Override
    protected String getPalavraReservada() {
        return PalavrasReservadas.ESTE;
    }

}
