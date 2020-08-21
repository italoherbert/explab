package explab.inter;

import italo.explab.InterAplic;
import italo.explab.arvore.ExecResult;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterResult;
import italo.explab.inter.exp.ValorInter;
import italo.explab.inter.exp.num.NumExpInter;
import italo.explab.inter.exp.oo.OOVarOuMatELOuFuncInter;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;
import italo.explab.var.num.RealVar;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExpInterTest {   
    
    private final String[] FUNCS_TESTES = {
        "1",
        "sen( pi )",
        "cos(pi) ",
        "tan( pi )",
        "pot( 2, 4 )",
        "rquad( 2^6 )",
        "rquad( 64 )",
        "abs( -5 )",
        "abs( 5 )",
        "asen( 0 )",
        "acos( 0 )",
        "atan( 0 )",
        "atan2( 0, 100 )",
        "exp( e )",
        "log10( 100 )",
        "log( e^2 )",
        "e * 1^1000",        
        "1+ 2^rquad( 4) * -5",
        "pot( e , 2 )",
        "(rquad(3))",
        "-2^4",
        "(-2)^4"        
    };
            
    private final double[] FUNCS_RESULTS_ESPERADOS = {
        1,
        Math.sin( Math.PI ),
        Math.cos( Math.PI ),
        Math.tan( Math.PI ),
        Math.pow( 2, 4 ),
        Math.sqrt( Math.pow( 2, 6 ) ),
        Math.sqrt( 64 ),
        Math.abs( -5 ),
        Math.abs( 5 ),
        Math.asin( 0 ),
        Math.acos( 0 ),
        Math.atan( 0 ),
        Math.atan2( 0, 100 ),
        Math.exp( Math.E ),
        Math.log10( 100 ),
        Math.log( Math.pow( Math.E, 2 ) ),
        Math.E * Math.pow( 1, 1000 ),
        1 + Math.pow( 2, Math.sqrt( 4 ) ) * -5,
        Math.pow( Math.E, 2 ),
        Math.sqrt( 3 ),
        -16,
        16
    };
    
    public final String[] VARS_TESTES = {
        "pi ",
        "e ",
        "pi"
    };
    
    public final double[] VARS_RESULTS_ESPERADOS = {
        Math.PI, 
        Math.E,
        Math.PI
    };
    
    public final String[] MATRIZES_TESTES = {        
        "[ 1 2; 3 4; 5 6]**[ 1 2 3 4; abs( -5 ), 6, rquad( 7^2), 8]",                
        "[1 2]*[3 4]",        
        "[1 2]^[3 4]",        
        "[6 8]/[3 2]",
        "[1 2] + [3 4 ]",
        "[1 2] - [3 4]",        
        "2^[1 2 3;4 5 6 ]*( 3*rquad( 6*6+8^2 )*( ( 2*3+pot( 4, 2) ) / 2 ) )",        
        "[pi 0; pi, 0]",        
        "[pi pi, 1+pi, 2+pi; e+2, e e e]",
        "(2*rquad(4^4) * ( 2 * (1-[ 1 2 3;4 5 6]^2) ) - 4 )/2"
    };        
    
    public final double[][][] MATRIZES_RESULTS_ESPERADOS = {        
        { {11, 14, 17, 20}, { 23, 30, 37, 44}, {35, 46, 57, 68 } },        
        { {3, 8} },        
        { {1, 16} },        
        { {2, 4} },
        { {4, 6} },
        { { -2, -2 } },
        { { 660, 1320, 2640 }, { 5280, 10560, 21120 } },                
        { { Math.PI, 0}, {Math.PI, 0} },
        { { Math.PI, Math.PI, 1+Math.PI, 2+Math.PI}, {Math.E+2, Math.E, Math.E, Math.E} },
        { {-2, -98, -258}, {-482, -770, -1122} }            
    };
    
    private final InterAplic aplic = new InterAplic();
        
    @Test
    public void execFuncs() {  
        ValorInter inter = new ValorInter();
        
        for( int i = 0; i< FUNCS_TESTES.length; i++ ) {
            String cod = FUNCS_TESTES[ i ];
            double esperado = FUNCS_RESULTS_ESPERADOS[ i ];
            
            Var var = this.exec( inter, cod );
            
            assertTrue( var.getTipo() == Var.REAL, cod );
            assertTrue( var.getTipoCompativel() == Var.TC_NUMERICO, cod );

            double valor = ((RealVar)var).getValor();
            assertEquals( valor, esperado, cod );             
        }
    }
    
    @Test
    public void execVars() {  
        OOVarOuMatELOuFuncInter inter = new OOVarOuMatELOuFuncInter();
                
        for( int i = 0; i < VARS_TESTES.length; i++ ) {
            String cod = VARS_TESTES[ i ];
            double esperado = VARS_RESULTS_ESPERADOS[ i ];
            
            Var var = this.exec( inter, cod );
            
            assertTrue( var.getTipo() == Var.REAL, cod );
            assertTrue( var.getTipoCompativel() == Var.TC_NUMERICO, cod );

            double valor = ((RealVar)var).getValor();
            
            assertEquals( valor, esperado );             
        }
    }
    
    @Test
    public void execMats() {        
        NumExpInter inter = new NumExpInter();
        for( int i = 0; i < MATRIZES_TESTES.length; i++ ) {
            String cod = MATRIZES_TESTES[ i ];
            double[][] esperado = MATRIZES_RESULTS_ESPERADOS[ i ];
            
            Var var = this.exec( inter, cod );
            
            assertTrue( var instanceof MatrizVar, cod );
            assertTrue( var.getTipo() == Var.MATRIZ, cod );
            assertTrue( var.getTipoCompativel() == Var.TC_NUMERICO, cod );

            Var[][] mat = ((MatrizVar)var).getMatriz();
            double[][] realmat = new double[ mat.length ][];
            for( int j = 0; j < mat.length; j++ ) {
                int vtam = mat[ j ].length;
                double[] vetor = new double[ vtam ];
                for( int k = 0; k < vtam; k++ ) {
                    vetor[ k ] = ((RealVar)mat[j][k]).getValor();
                }
                realmat[ j ] = vetor;
            }                        
            
            assertArrayEquals( realmat, esperado, cod );             
        }        
    }    
    
    private Var exec( Inter inter, String cod ) {
        Codigo codigo = new Codigo( aplic, cod );
        InterResult result = (InterResult)inter.interpreta( aplic.getGrupoRaiz(), aplic.getGrupoRaiz(),aplic, codigo, 0, codigo.getCodlen() );

        assertNotNull( result, cod ); 
        assertNotNull( result.getInstrucaoOuExp(), cod );

        ExecResult er = aplic.getExecutor().exec( result.getInstrucaoOuExp() ); 

        assertNotNull( er.getVar(), cod );
        return er.getVar();        
    }
    
}
