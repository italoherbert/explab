package explab.inter.scripts.c2;

import explab.inter.scripts.AbstractScriptInterTest;
import italo.explab.var.Var;
import italo.explab.var.num.NumeroRealVar;

public class C2ScriptInterTest extends AbstractScriptInterTest {        
            
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
        
        new NumeroRealVar( 20 ),
        new NumeroRealVar( 10 ),
        new NumeroRealVar( 10 ),
        new NumeroRealVar( 5 ),
    };
    
    @Override
    public Class getTesteClasse() {
        return C2ScriptInterTest.class;
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
