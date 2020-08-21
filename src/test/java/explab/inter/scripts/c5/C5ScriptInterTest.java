package explab.inter.scripts.c5;

import explab.inter.scripts.AbstractScriptInterTest;
import italo.explab.var.Var;
import italo.explab.var.num.NumeroRealVar;

public class C5ScriptInterTest extends AbstractScriptInterTest {        
            
    private final String[] EXPS = {        
        "xx",
        "yy",
        "zz",        
        "ww", 
        
        "xxx",
        "yyy",
        "zzz",
    };
    
    private final Var[] EXPS_RESULTS = {        
        new NumeroRealVar( 5000 ),
        new NumeroRealVar( 125 ),
        
        new NumeroRealVar( 75 ),
        new NumeroRealVar( 150 ),
        new NumeroRealVar( 10000 ),
        new NumeroRealVar( Math.PI*100 ),
        new NumeroRealVar( Math.PI )
    };
    
    @Override
    public Class getTesteClasse() {
        return C5ScriptInterTest.class;
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
