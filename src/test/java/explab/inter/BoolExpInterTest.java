package explab.inter;

import italo.explab.InterAplic;
import italo.explab.arvore.ExecResult;
import italo.explab.codigo.Codigo;
import italo.explab.inter.InterResult;
import italo.explab.inter.exp.bool.BoolExpInter;
import italo.explab.var.BooleanVar;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoolExpInterTest {
        
    private final String[] EXPS = { 
        "((rquad( 4)^2)-1)*2 > pi",        
        "(1<pi & \"xxx\"== \"xxy\")",                
        "!falso",
        "!verdade",
        "verdade & verdade | falso",        
        "verdade | falso",                                   
        "!(2>4) | 2>3 & 4<5",
        "!falso & ( (2>1) | (2>2 | 2>3) )",                
        "2<=2",
        "3>=3",         
        "rquad(4)>=2 | ( 2> abs( -1^10 ) )",                
        "(((2>4)))",            
        "1 == rquad(1)",
        "2 != 3 ;",             
    };
    
    private final String[] EXPS_NULL = {          
        "verdade | (!2 > 4)",
        "((2 > 4 ||4>2))",
        "6 = !( 2>4 ) > 5"                        
    };
    
    private final boolean[] RESULTS_ESPERADOS = {
        true,
        false,
        true,
        false,
        true,
        true,
        true,
        true,
        true,
        true,        
        true,
        false,
        true,
        true
    };
        
    @Test
    public void execTest() {
        InterAplic aplic = new InterAplic();
        BoolExpInter inter = new BoolExpInter();
                                        
        for( int i = 0; i < EXPS.length; i++ ) {
            String exp = EXPS[ i ];
            boolean esperado = RESULTS_ESPERADOS[ i ];
           
            Codigo codigo = new Codigo( aplic, exp, "arq("+i+")" );
            InterResult result = inter.interpreta( aplic.getGrupoRaiz(), aplic.getGrupoRaiz(), aplic, codigo, 0, codigo.getCodlen() );
            if ( result.getErro() != null )
                System.out.println( result.getErro().getChave()+"  "+result.getErro().getClasse().getName() );

            assertNotNull( result, exp );
            assertNotNull( result.getInstrucaoOuExp(), exp );
            
            ExecResult er = aplic.getExecutor().exec( result.getInstrucaoOuExp() );
            if ( er.getErro() != null )
                System.out.println( er.getErro().getChave()+"  "+er.getErro().getClasse().getName() );

            System.out.println();
            
            assertNotNull( er, exp );
            assertNull( er.getErro(), exp );
            assertNotNull( er.getVar(), exp );
            assertEquals( ((BooleanVar)er.getVar()).getValor(), esperado, exp );
            
            System.out.println( "OK - "+exp );
        }
        
        for( String exp : EXPS_NULL ) {
            Codigo codigo = new Codigo( aplic, exp );
            InterResult result = (InterResult)inter.interpreta( aplic.getGrupoRaiz(), aplic.getGrupoRaiz(), aplic, codigo, 0, codigo.getCodlen() );
            assertFalse( result.getJ() == codigo.getCodlen(), exp );
            assertNotNull( result.getErro(), exp );            
        }
    }
    
}
