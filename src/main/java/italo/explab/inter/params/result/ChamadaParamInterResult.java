package italo.explab.inter.params.result;

import italo.explab.msg.Erro;
import italo.explab.AnaliseOuInterResult;
import italo.explab.inter.InterResult;

public class ChamadaParamInterResult extends InterResult {
    
    private InterResult valorResult;
    private int i;

    public ChamadaParamInterResult( InterResult valorResult, int i, int j ) {
        super( j );
        this.i = i;
        this.valorResult = valorResult;
    }

    public ChamadaParamInterResult( Erro erro ) {
        super( erro );
    }

    public ChamadaParamInterResult( AnaliseOuInterResult result ) {
        super( result );
    }

    public InterResult getValorResult() {
        return valorResult;
    }

    public int getPos() {
        return i+j;
    }
        
}
