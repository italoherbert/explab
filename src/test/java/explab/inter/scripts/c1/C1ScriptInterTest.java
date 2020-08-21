package explab.inter.scripts.c1;

import explab.inter.scripts.AbstractScriptInterTest;
import italo.explab.var.Var;
import italo.explab.var.num.NumeroRealVar;

public class C1ScriptInterTest extends AbstractScriptInterTest {        
            
    private final String[] EXPS = {        
        "w",
        "x",
        "y",
        
        "z",        
        "ww",
        "xx",
        "zz"      
    };
    
    private final Var[] EXPS_RESULTS = {        
        new NumeroRealVar( 100 ),
        new NumeroRealVar( 1000 ),
        
        new NumeroRealVar( 256 ),
        new NumeroRealVar( 64 ),
        new NumeroRealVar( 25 ),
        new NumeroRealVar( 81 ),
        new NumeroRealVar( 10000 )
    };
    
    @Override
    public Class getTesteClasse() {
        return C1ScriptInterTest.class;
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
