package italo.explab.inter.leitura.result;

import italo.explab.construtor.Construtor;
import italo.explab.msg.Erro;
import italo.explab.AnaliseOuInterResult;
import italo.explab.inter.InterResult;

public class LeituraConstrutorInterResult extends InterResult {

    private Construtor construtor;
    
    public LeituraConstrutorInterResult() {}
    
    public LeituraConstrutorInterResult( Construtor construtor, int j ) {
        super( j );
        this.construtor = construtor;
    }

    public LeituraConstrutorInterResult( Erro erro ) {
        super( erro );
    }

    public LeituraConstrutorInterResult(AnaliseOuInterResult result) {
        super( result );
    }
    
    public Construtor getConstrutor() {
        return construtor;
    }
    
}
