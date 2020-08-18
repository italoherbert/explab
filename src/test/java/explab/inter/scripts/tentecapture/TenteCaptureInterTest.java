package explab.inter.scripts.tentecapture;

import explab.inter.scripts.AbstractScriptInterTest;
import italo.explab.var.Var;
import italo.explab.var.BooleanVar;

public class TenteCaptureInterTest extends AbstractScriptInterTest {        
            
    private final String[] EXPS = {        
        "xx",
        "yy",
        "zz",        
        "ww", 
        "xxx",
        "yyy",
        "zzz",
        "www",
        "xxxx"
    };
    
    private final Var[] EXPS_RESULTS = {        
        new BooleanVar( true ),
        new BooleanVar( true ),        
        new BooleanVar( true ),
        new BooleanVar( true ),
        new BooleanVar( true ),
        new BooleanVar( true ),
        new BooleanVar( true ),
        new BooleanVar( true ),
        new BooleanVar( true ) 
    };
    
    @Override
    public Class getTesteClasse() {
        return TenteCaptureInterTest.class;
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
