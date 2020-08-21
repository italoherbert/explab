package italo.explab.inter.cmd;

import italo.explab.PalavrasReservadas;

public class ExibaCMDInter extends AbstractExibaCMDInter {

    @Override
    protected String getPalavraChave() {
        return PalavrasReservadas.EXIBA;
    }

    @Override
    protected boolean isPularLinha() {
        return false;
    }
  
}
