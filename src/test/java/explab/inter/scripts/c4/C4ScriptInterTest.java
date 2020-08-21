package explab.inter.scripts.c4;

import explab.inter.scripts.AbstractScriptInterTest;
import italo.explab.var.Var;
import italo.explab.var.num.NumeroRealVar;

public class C4ScriptInterTest extends AbstractScriptInterTest {        
            
    private final String[] EXPS = {        
        "xx",
        "yy",
        "zz",        
    };
    
    private final Var[] EXPS_RESULTS = {        
        new NumeroRealVar( 4 ),
        new NumeroRealVar( 40 ),
        
        new NumeroRealVar( 1 )
    };
    
    @Override
    public Class getTesteClasse() {
        return C4ScriptInterTest.class;
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
