package italo.explab.inter.leitura.result;

import italo.explab.AnaliseOuInterResult;
import italo.explab.inter.InterResult;
import italo.explab.msg.Erro;
import java.util.List;

public class LeituraExceptionsInterResult extends InterResult {
 
    private List<String> exceptionClasses;
        
    public LeituraExceptionsInterResult() {}
    
    public LeituraExceptionsInterResult( List<String> exceptionClasses, int j ) {
        super( j );
        this.exceptionClasses = exceptionClasses;
    }

    public LeituraExceptionsInterResult( Erro erro ) {
        super( erro );
    }

    public LeituraExceptionsInterResult( AnaliseOuInterResult result ) {
        super( result );
    }

    public List<String> getExceptionClasses() {
        return exceptionClasses;
    }

    
}
