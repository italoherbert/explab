package explab.inter.scripts.heranca;

import explab.inter.scripts.AbstractScriptInterTest;
import italo.explab.var.Var;
import italo.explab.var.num.NumeroRealVar;

public class HerancaScriptInterTest extends AbstractScriptInterTest {        
            
    private final String[] EXPS = {        
        "xx",
        "yy",
        "zz",        
        "ww",         
    };
    
    private final Var[] EXPS_RESULTS = {        
        new NumeroRealVar( 10000 ),
        new NumeroRealVar( 200 ),
        
        new NumeroRealVar( 300 ),
        new NumeroRealVar( 200 )
    };
    
    @Override
    public Class getTesteClasse() {
        return HerancaScriptInterTest.class;
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
