package italo.explab.inter.cmd;

import italo.explab.PalavrasReservadas;

public class ExibaLNCMDInter extends AbstractExibaCMDInter {

    @Override
    protected String getPalavraChave() {
        return PalavrasReservadas.EXIBALN;
    }

    @Override
    protected boolean isPularLinha() {
        return true;
    }
  
}
