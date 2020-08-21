package explab.inter.scripts.c3;

import explab.inter.scripts.AbstractScriptInterTest;
import italo.explab.var.Var;
import italo.explab.var.BooleanVar;
import italo.explab.var.num.NumeroRealVar;

public class C3ScriptInterTest extends AbstractScriptInterTest {        
            
    private final String[] EXPS = {        
        "xx",
        "yy",
        "zz",
        
        "ww",        
        "xxx",
        "yyy",
    };
    
    private final Var[] EXPS_RESULTS = {        
        new NumeroRealVar( 8 ),
        new NumeroRealVar( 4 ),
        
        new NumeroRealVar( 1 ),
        new NumeroRealVar( 20 ),
        new NumeroRealVar( 10 ),
        new BooleanVar( true ),
    };
    
    @Override
    public Class getTesteClasse() {
        return C3ScriptInterTest.class;
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
