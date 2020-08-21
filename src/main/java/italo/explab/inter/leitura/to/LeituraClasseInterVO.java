package italo.explab.inter.leitura.to;

import italo.explab.inter.InterTO;

public class LeituraClasseInterVO implements InterTO {
    
    private final String pacote;

    public LeituraClasseInterVO(String pacote) {
        this.pacote = pacote;
    }

    public String getPacote() {
        return pacote;
    }
    
}

