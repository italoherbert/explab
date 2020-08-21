package italo.explab.inter.leitura.result;

import italo.explab.AnaliseOuInterResult;
import italo.explab.inter.InterResult;
import italo.explab.msg.Erro;
import italo.explab.recursos.classe.Classe;

public class LeituraClasseInterResult extends InterResult {
        
    private Classe classe = null;

    public LeituraClasseInterResult() {}

    public LeituraClasseInterResult( Classe classe, int j ) {
        super( j );
        this.classe = classe;
    }

    public LeituraClasseInterResult( Erro erro ) {
        super( erro );
    }

    public LeituraClasseInterResult( AnaliseOuInterResult result ) {
        super( result );
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }
    
}