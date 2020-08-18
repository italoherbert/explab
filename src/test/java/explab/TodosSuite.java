package explab;

import explab.analisador.AnalisadorSintaticoTest;
import explab.inter.BoolExpInterTest;
import explab.inter.ExpInterTest;
import explab.inter.InterTest;
import explab.inter.scripts.c1.C1ScriptInterTest;
import explab.inter.scripts.c2.C2ScriptInterTest;
import explab.inter.scripts.c3.C3ScriptInterTest;
import explab.inter.scripts.c4.C4ScriptInterTest;
import explab.inter.scripts.c5.C5ScriptInterTest;
import explab.inter.scripts.heranca.HerancaScriptInterTest;
import explab.inter.scripts.tentecapture.TenteCaptureInterTest;
import explab.inter.scripts.tentecapture_c6.C6TenteCaptureInterTest;
import org.junit.runner.RunWith;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;

@RunWith(JUnitPlatform.class)
@SelectClasses({
    AnalisadorSintaticoTest.class, 
    InterTest.class, 
    
    ExpInterTest.class,
    BoolExpInterTest.class, 
        
    C1ScriptInterTest.class,
    C2ScriptInterTest.class,
    C3ScriptInterTest.class,
    C4ScriptInterTest.class,
    C5ScriptInterTest.class,
        
    HerancaScriptInterTest.class,  
    TenteCaptureInterTest.class,
    C6TenteCaptureInterTest.class
})
@SelectPackages({
    "explab.inter.scripts.classes",
    "explab.inter.scripts2"
})
public class TodosSuite {
    
}
