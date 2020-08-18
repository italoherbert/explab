package explab.inter;

import explab.InterConsts;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import italo.explab.InterAplic;
import italo.explab.arvore.ExecResult;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.inter.exp.bool.BoolExpInter;
import italo.explab.inter.exp.func.ChamadaFuncInter;
import italo.explab.inter.exp.mat.MatInter;
import italo.explab.inter.exp.mat.VetN1IncN2Inter;
import italo.explab.inter.exp.num.NumExpInter;
import italo.explab.inter.exp.num.RealInter;
import italo.explab.inter.exp.oo.OOVarOuMatELOuFuncInter;
import italo.explab.inter.exp.string.StringExpInter;
import italo.explab.inter.exp.string.StringInter;
import italo.explab.msg.CodigoErro;
import italo.explab.var.StringVar;
import italo.explab.var.Var;
import italo.explab.var.BooleanVar;
import italo.explab.var.mat.MatrizVar;
import italo.explab.var.num.NumeroRealVar;
import italo.explab.var.num.RealVar;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InterTest implements InterConsts {
    
   
    @Test
    public void interpretaNumRealTest() {
        String rotulo = "Num REAL";
                
        for( int i = 0; i < NUM_REAL_VALIDOS.length; i++ ) {
            String codigo = NUM_REAL_VALIDOS[ i ];
            Object esperado = NUM_REAL_ESPERADOS[ i ];            
            this.interpreta( new RealInter(), null, rotulo, codigo, esperado );                
        }
        
        System.out.println();
    }
    
    @Test
    public void interpretaStringTest() {
        String rotulo = "STRING";
                
        for( int i = 0; i < STRING_VALIDOS.length; i++ ) {
            String codigo = STRING_VALIDOS[ i ];
            Object esperado = STRING_ESPERADOS[ i ];            
            this.interpreta( new StringInter(), null, rotulo, codigo, esperado );                
        }
        
        System.out.println();
    }

    @Test
    public void interpretaNumExpTest() {
        String rotulo = "Num EXP";
                        
        for( int i = 0; i < NUM_EXPS_VALIDAS.length; i++ ) {
            String codigo = NUM_EXPS_VALIDAS[ i ];
            Object esperado = NUM_EXPS_VALIDAS_ESPERADO[ i ];            
            this.interpreta( new NumExpInter(), null, rotulo, codigo, esperado );                
        }
        
        System.out.println();
    }
    
    @Test
    public void interpretaStringExpTest() {
        String rotulo = "String EXP";
                        
        for( int i = 0; i < STRING_EXP_VALIDAS.length; i++ ) {
            String codigo = STRING_EXP_VALIDAS[ i ];
            Object esperado = STRING_EXP_VALIDAS_ESPERADAS[ i ];            
            this.interpreta( new StringExpInter(), null, rotulo, codigo, esperado );                
        }
        
        System.out.println();
    }
        
    @Test
    public void interpretaBoolExpTest() {
        String rotulo = "Bool EXP";
                        
        for( int i = 0; i < BOOL_EXPS_VALIDAS.length; i++ ) {
            String codigo = BOOL_EXPS_VALIDAS[ i ];
            Object esperado = BOOL_EXPS_VALIDAS_ESPERADO[ i ];            
            this.interpreta( new BoolExpInter(), null, rotulo, codigo, esperado );                
        }
        
        System.out.println();
    }        
        
    @Test
    public void interpretaMatrizRealTest() {
        String rotulo = "MATRIZ REAL";
                
        for( int i = 0; i < MATRIZES_REAIS_VALIDAS.length; i++ ) {
            String codigo = MATRIZES_REAIS_VALIDAS[ i ];
            Object esperado = MATRIZES_REAIS_VALIDAS_ESPERADAS[ i ];            
            this.interpreta( new MatInter(), null, rotulo, codigo, esperado );                
        }
        
        System.out.println();               
    }
            
    @Test
    public void interpretaVarTest() {
        String rotulo = "VAR";
        
        for( int i = 0; i < VARS_VALIDAS.length; i++ ) {
            String codigo = VARS_VALIDAS[ i ];
            Object esperado = VARS_VALIDAS_ESPERADAS[ i ];            
            this.interpreta( new OOVarOuMatELOuFuncInter(), null, rotulo, codigo, esperado );                
        }
        
        System.out.println();               
    }
    
    @Test
    public void interpretaChamadaFuncTest() {
        String rotulo = "Chamada FUNC";
                
        for( int i = 0; i < CHAMADA_FUNCS_VALIDAS.length; i++ ) {
            String codigo = CHAMADA_FUNCS_VALIDAS[ i ];
            Object esperado = CHAMADA_FUNCS_VALIDAS_ESPERADAS[ i ];            
            this.interpreta(new ChamadaFuncInter(), null, rotulo, codigo, esperado );                
        }
        
        System.out.println();               
    }
                
    @Test
    public void interpretaVetN1IncN2Test() {
        String rotulo = "VetN1IncN2";
                
        for( int i = 0; i < N1_INC_N2_VETS_VALIDOS.length; i++ ) {
            String codigo = N1_INC_N2_VETS_VALIDOS[ i ];
            Object esperado = N1_INC_N2_VETS_VALIDOS_ESPERADO[ i ];            
            this.interpreta( new VetN1IncN2Inter(), null, rotulo, codigo, esperado );                
        }
        
        System.out.println();
    }                                    

    private void interpreta( Inter inter, InterTO to, String rotulo, String cod, Object esperado ) {
        if ( esperado == null )
            return;
                
        InterAplic aplic = new InterAplic();
        
        Codigo codigo = new Codigo( aplic, cod );                
                        
        InterResult result;
        String errochavestr = null;
        int pos = -1;
        try {
            result = inter.interpreta( aplic.getGrupoRaiz(), aplic.getGrupoRaiz(), aplic, codigo, to, 0, codigo.getCodlen() ); 
            if ( result.getJ() == 0 ) {
                errochavestr = "Nó nulo!";
                pos = 0;
                if ( result.getErro() != null ) {
                    errochavestr = result.getErro().getChave()+"  "+result.getErro().getClasse().getName();
                    if ( result.getErro() instanceof CodigoErro )
                        pos = ((CodigoErro)result.getErro()).getPos();
                }
            } else {
                pos = result.getJ();
            }
        } catch ( Exception e ) {
            result = new InterResult();
            System.out.println( "exception" );
            throw e;
        }
        
        boolean ok = result.getJ() == codigo.getCodlen();
        
        assertNotNull( result.getInstrucaoOuExp(), cod );
        
        ExecResult er = aplic.getExecutor().exec( result.getInstrucaoOuExp() );
        if ( er.getErro() != null ) {
            errochavestr = "Nó nulo!";
            pos = 0;
            if ( result.getErro() != null ) {
                errochavestr = result.getErro().getChave()+"  "+result.getErro().getClasse().getName();
                if ( result.getErro() instanceof CodigoErro )
                    pos = ((CodigoErro)result.getErro()).getPos();
            }
        } 
        
        String okmsg = ( ok ? "Ok" : "Falha" );
        
        System.out.print( rotulo + ": ("+okmsg+") " + cod );
        if ( !ok )
            System.out.println( "  Pos("+pos+", "+codigo.getCodlen()+")= "+errochavestr );        
        else System.out.println();
        
        assertTrue( ok, cod );
        
        if ( result.getJ() == 0 )
            return;
        
        if ( errochavestr != null )
            return;
                
        Var varTipoEsperado = null;
        if ( esperado instanceof Double[][] ) {            
            if ( er.getVar().getTipo() == Var.MATRIZ ) {
                Var[][] mat = ((MatrizVar)er.getVar()).getMatriz();
                Double[][] espmat = (Double[][])esperado;
                for( int i = 0; i < mat.length; i++ ) {
                    for( int j = 0; j < mat[i].length; j++ ) {
                        assertEquals( ((RealVar)mat[i][j]).getValor(), espmat[i][j], cod );                                    
                        assertTrue( mat[i][j].iguais( new NumeroRealVar( espmat[i][j] ) ), cod );
                    }
                }                    
            } else {
                varTipoEsperado = new MatrizVar( 0, 0 );
            }         
        } else if ( esperado instanceof Double ) {
            if ( er.getVar().getTipo() == Var.REAL ) {
                double real = ((RealVar)er.getVar()).getValor();
                double espreal = (double)esperado;
                assertEquals( real, espreal, cod );                                    
            } else {
                varTipoEsperado = new NumeroRealVar( 0 ); 
            }
        } else if ( esperado instanceof String ) {
            if ( er.getVar().getTipo() == Var.STRING ) {
                String str = ((StringVar)er.getVar()).getValor();
                String espstr = (String)esperado;
                assertEquals( str, espstr, cod );                                    
            } else {
                varTipoEsperado = new StringVar( "" );
            }
        } else if ( esperado instanceof Boolean ) {
            if ( er.getVar().getTipo() == Var.BOOLEAN ) {
                boolean bool = ((BooleanVar)er.getVar()).getValor();
                boolean espbool = (boolean)esperado;
                assertEquals( bool, espbool, cod );                                    
            } else {
                varTipoEsperado = new BooleanVar( true ); 
            }
        } else {
            fail( "tipo inválido de variável \"esperado\"" );
        }    

        if ( varTipoEsperado != null ) {
            fail( "Esperado tipo "+varTipoEsperado.getStringTipo()+" mas encontrado "+er.getVar().getStringTipo() );
            System.err.println( inter );
        }        
        
    }
    
}
