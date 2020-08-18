package italo.explab.inter.params.result;

import italo.explab.msg.Erro;
import java.util.List;
import italo.explab.AnaliseOuInterResult;
import italo.explab.inter.InterResult;

public class ChamadaParamsInterResult extends InterResult {
    
    private List<ChamadaParamInterResult> params;

    public ChamadaParamsInterResult() {}

    public ChamadaParamsInterResult( List<ChamadaParamInterResult> params, int j ) {
        super( j );
        this.params = params;
    }

    public ChamadaParamsInterResult( Erro erro ) {
        super( erro );
    }

    public ChamadaParamsInterResult( AnaliseOuInterResult result ) {
        super(result);
    }

    public List<ChamadaParamInterResult> getParams() {
        return params;
    }
    
}
