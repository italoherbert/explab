package italo.explab.inter.leitura.result;

import italo.explab.func.UsuarioFunc;
import italo.explab.msg.Erro;
import italo.explab.AnaliseOuInterResult;
import italo.explab.inter.InterResult;

public class LeituraFuncInterResult extends InterResult {

    private UsuarioFunc func;
    
    public LeituraFuncInterResult() {}

    public LeituraFuncInterResult( UsuarioFunc func, int j ) {
        super( j );
        this.func = func;
    }

    public LeituraFuncInterResult( Erro erro ) {
        super( erro );
    }

    public LeituraFuncInterResult( AnaliseOuInterResult result ) {
        super( result );
    }

    public UsuarioFunc getFunc() {
        return func;
    }
    
}
