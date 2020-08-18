package explab.inter.scripts.tentecapture_c6;

import explab.inter.scripts.AbstractScriptInterTest;
import italo.explab.var.Var;
import italo.explab.var.BooleanVar;

public class C6TenteCaptureInterTest extends AbstractScriptInterTest {        
            
    private final String[] EXPS = {        
        "xx",
        "yy"
    };
    
    private final Var[] EXPS_RESULTS = {        
        new BooleanVar( true ),
        new BooleanVar( true )
    };
    
    @Override
    public Class getTesteClasse() {
        return C6TenteCaptureInterTest.class;
    }    

    @Override
    public String[] getExpressoes() {
        return EXPS;
    }

    @Override
    public Var[] getExpressoesResultados() {
        return EXPS_RESULTS;
    }
    
}
