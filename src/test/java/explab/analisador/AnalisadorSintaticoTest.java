package explab.analisador;

import explab.InterConsts;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import italo.explab.InterAplic;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.util.ContadorUtil;

public class AnalisadorSintaticoTest implements InterConsts {            
        
    private final InterAplic aplic = new InterAplic();
    private final ContadorUtil contUtil = new ContadorUtil();
    private final AnalisadorSintaticoManager manager = new AnalisadorSintaticoManager( contUtil );
    
    private final String VALIDA = " (valida)";
    private final String INVALIDA = " (invalida)";
        
    @Test
    public void analisaNumRealTest() {
        String rotulo = "REAL";
        for( String codigo : NUM_REAL_VALIDOS )
            this.analisa( manager.getNumRealAnalisador(), rotulo+VALIDA, codigo, true );                
        for( String codigo : NUM_REAL_INVALIDOS )
            this.analisa( manager.getNumRealAnalisador(), rotulo+INVALIDA, codigo, false );  
        System.out.println();               
    }    
    
    @Test
    public void analisaStringTest() {
        String rotulo = "STRING";
        for( String codigo : STRING_VALIDOS )
            this.analisa( manager.getStringAnalisador(), rotulo+VALIDA, codigo, true );                
        for( String codigo : STRING_INVALIDOS )
            this.analisa( manager.getStringAnalisador(), rotulo+INVALIDA, codigo, false );  
        System.out.println();               
    }
        
    @Test
    public void analisaMatrizRealTest() {
        String rotulo = "MATRIZ REAL";
        for( String codigo : MATRIZES_REAIS_VALIDAS )
            this.analisa( manager.getRealMatAnalisador(), rotulo+VALIDA, codigo, true );                
        for( String codigo : MATRIZES_REAIS_INVALIDAS )
            this.analisa( manager.getRealMatAnalisador(), rotulo+INVALIDA, codigo, false );  
        System.out.println();               
    }
    
    @Test
    public void analisaMatrizTest() {
        String rotulo = "MATRIZ GENERICA";
        for( String codigo : MATRIZES_VALIDAS )
            this.analisa( manager.getMatAnalisador(), rotulo+VALIDA, codigo, true );                
        for( String codigo : MATRIZES_INVALIDAS )
            this.analisa( manager.getMatAnalisador(), rotulo+INVALIDA, codigo, false );  
        System.out.println();               
    }
    
    @Test
    public void analisaChamadaFuncTest() {
        String rotulo = "CAMADA FUNC";
        for( String codigo : CHAMADA_FUNCS_VALIDAS )
            this.analisa( manager.getChamadaFuncAnalisador(), rotulo+VALIDA, codigo, true );                
        for( String codigo : CHAMADA_FUNCS_INVALIDAS )
            this.analisa( manager.getChamadaFuncAnalisador(), rotulo+INVALIDA, codigo, false );  
        System.out.println();               
    }
    
    @Test
    public void analisaVarTest() {
        String rotulo = "VARS";
        for( String codigo : VARS_VALIDAS )
            this.analisa( manager.getVarOuChamadaFuncAnalisador(), rotulo+VALIDA, codigo, true );                
        for( String codigo : VARS_INVALIDAS )
            this.analisa( manager.getVarOuChamadaFuncAnalisador(), rotulo+INVALIDA, codigo, false );  
        System.out.println();               
    }
    
    @Test
    public void analisaIncDecTest() {
        String rotulo = "INC DEC";
        for( String codigo : INC_DEC_VALIDOS )
            this.analisa( manager.getIncDecAnalisador(), rotulo+VALIDA, codigo, true );                
        for( String codigo : INC_DEC_INVALIDOS )
            this.analisa( manager.getIncDecAnalisador(), rotulo+INVALIDA, codigo, false );  
        System.out.println();               
    } 
    
    @Test
    public void analisaLeituraVarTest() {
        String rotulo = "LEITURA VAR";
        for( String codigo : LEITURA_VAR_VALIDAS )
            this.analisa( manager.getLeituraVarAnalisador(), rotulo+VALIDA, codigo, true );                
        for( String codigo : LEITURA_VAR_INVALIDAS )
            this.analisa( manager.getLeituraVarAnalisador(), rotulo+INVALIDA, codigo, false );  
        System.out.println();               
    } 
        
    @Test
    public void analisaNumExpTest() {
        String rotulo = "Num EXP";
        for( String codigo : NUM_EXPS_VALIDAS )
            this.analisa( manager.getNumericaExpAnalisador(), rotulo+VALIDA, codigo, true );                
        for( String codigo : NUM_EXPS_INVALIDAS )
            this.analisa( manager.getNumericaExpAnalisador(), rotulo+INVALIDA, codigo, false );  
        System.out.println();
    }  
    
    @Test
    public void analisaBoolExpTest() {        
        String rotulo = "Bool EXP";
        for( String codigo : BOOL_EXPS_VALIDAS )
            this.analisa( manager.getBoolExpAnalisador(), rotulo+VALIDA, codigo, true );                
        for( String codigo : BOOL_EXPS_INVALIDAS )
            this.analisa( manager.getBoolExpAnalisador(), rotulo+INVALIDA, codigo, false );  
        System.out.println();
    }    
    
    @Test
    public void analisaStringExpTest() {
        String rotulo = "String EXP";
        for( String codigo : STRING_EXP_VALIDAS )
            this.analisa( manager.getTalvezSemStrIniStringExpAnalisador(), rotulo+VALIDA, codigo, true );                
        for( String codigo : STRING_EXP_INVALIDAS )
            this.analisa( manager.getTalvezSemStrIniStringExpAnalisador(), rotulo+INVALIDA, codigo, false );  
        System.out.println();
    }
        
    @Test
    public void analisaVetN1IncN2Test() {
        String rotulo = "VetN1IncN2";
        for( String codigo : N1_INC_N2_VETS_VALIDOS )
            this.analisa( manager.getVetN1IncN2Analisador(), rotulo+VALIDA, codigo, true );                
        for( String codigo : N1_INC_N2_VETS_INVALIDOS )
            this.analisa( manager.getVetN1IncN2Analisador(), rotulo+INVALIDA, codigo, false );      
        System.out.println();     
    }
    
    @Test
    public void analisaNovoObjTest() {
        String rotulo = "Novo OBJ";
        for( String codigo : NOVO_OBJ_VALIDAS )
            this.analisa( manager.getNovoObjetoAnalisador(), rotulo+VALIDA, codigo, true );                
        for( String codigo : NOVO_OBJ_INVALIDAS )
            this.analisa( manager.getNovoObjetoAnalisador(), rotulo+INVALIDA, codigo, false );      
        System.out.println();     
    }
    
    @Test
    public void analisaVarEntreParentesesTest() {
        String rotulo = "Var entre parenteses";
        for( String codigo : VAR_ENTRE_PARENTESES_VALIDAS )
            this.analisa( manager.getVarTalvezComParentesesAnalisador(), rotulo+VALIDA, codigo, true );                
        for( String codigo : VAR_ENTRE_PARENTESES_INVALIDAS )
            this.analisa( manager.getVarTalvezComParentesesAnalisador(), rotulo+INVALIDA, codigo, false );      
        System.out.println();     
    }
    
    @Test
    public void analisaSeTest() {
        String rotulo = "SE";
        for( String codigo : SE_VALIDOS )
            this.analisa( manager.getSeAnalisador(), rotulo+VALIDA, codigo, true );                
        for( String codigo : SE_INVALIDOS )
            this.analisa( manager.getSeAnalisador(), rotulo+INVALIDA, codigo, false );      
        System.out.println();     
    }
    
    @Test
    public void analisaCasoTest() {
        String rotulo = "CASO";
        for( String codigo : CASO_VALIDOS )
            this.analisa( manager.getCasoAnalisador(), rotulo+VALIDA, codigo, true );                
        for( String codigo : CASO_INVALIDOS )
            this.analisa( manager.getCasoAnalisador(), rotulo+INVALIDA, codigo, false );      
        System.out.println();     
    }
    
    @Test
    public void analisaEnquantoTest() {
        String rotulo = "ENQUANTO";
        for( String codigo : ENQUANTO_VALIDOS )
            this.analisa( manager.getEnquantoAnalisador(), rotulo+VALIDA, codigo, true );                
        for( String codigo : ENQUANTO_INVALIDOS )
            this.analisa( manager.getEnquantoAnalisador(), rotulo+INVALIDA, codigo, false );      
        System.out.println();     
    }
    
    @Test
    public void analisaFacaEnquantoTest() {
        String rotulo = "FACA ENQUANTO";
        for( String codigo : FACA_ENQUANTO_VALIDOS )
            this.analisa( manager.getFacaEnquantoAnalisador(), rotulo+VALIDA, codigo, true );                
        for( String codigo : FACA_ENQUANTO_INVALIDOS )
            this.analisa( manager.getFacaEnquantoAnalisador(), rotulo+INVALIDA, codigo, false );      
        System.out.println();     
    }
    
    @Test
    public void analisaParaTest() {
        String rotulo = "PARA";
        for( String codigo : PARA_VALIDOS )
            this.analisa( manager.getParaAnalisador(), rotulo+VALIDA, codigo, true );                
        for( String codigo : PARA_INVALIDOS )
            this.analisa( manager.getParaAnalisador(), rotulo+INVALIDA, codigo, false );      
        System.out.println();     
    }
    
    @Test
    public void analisaTenteCaptureTest() {
        String rotulo = "TENTE CAPTURE";
        for( String codigo : TENTE_CAPTURE_VALIDAS )
            this.analisa( manager.getTenteCaptureAnalisador(), rotulo+VALIDA, codigo, true );                
        for( String codigo : TENTE_CAPTURE_INVALIDAS )
            this.analisa( manager.getTenteCaptureAnalisador(), rotulo+INVALIDA, codigo, false );      
        System.out.println();
    }
        
    @Test
    public void analisaCMDsTest() {
        String rotulo = "CMDs";
        for( String codigo : CMDS_VALIDOS )
            this.analisa( manager.getCMDsAnalisador(), rotulo+VALIDA, codigo, true );                              
        System.out.println();     
    }
    
    @Test
    public void analisaInstrucaoTest() {
        String rotulo = "INSTRUÇÃO: ";
        for( String codigo : INSTRUCOES_VALIDAS )
            this.analisa( manager.getInstrucaoAnalisador(), rotulo+VALIDA, codigo, true );                              
        System.out.println();     
    }        
    
    private void analisa( AnalisadorSintatico analisador, String rotulo, String cod, boolean fimEsperado ) {
        Codigo codigo = new Codigo( aplic, cod ); 

        int limite = 60;
        
        AnaliseResult result = null;
        try {
            result = analisador.analisa( codigo, 0 );
        } catch ( Exception e ) {
            System.out.println( rotulo+":   "+ codigo+"  exception" );
            throw e;
        }
        String erro = "";
        int j = -1;
        String classeNome = "";
        if ( result != null ) {
            j = result.getJ();
            if ( result.getErro() != null ) {
                classeNome = result.getErro().getClasse().getName();
                int pos = result.getErro().getPos();
                String posinvalidomsg = "pos maior que o tamanho do código: pos="+pos+"; codlen="+codigo.getCodlen();
                String subcod = ( pos < codigo.getCodlen() ? codigo.getCodigo().substring( pos ) : posinvalidomsg );
                
                erro = result.getErro().getChave() + "  "+subcod.substring( 0, ( limite < subcod.length() ? limite : subcod.length() ) );   
            }
        }                
        
        boolean ok = ( fimEsperado ? j == codigo.getCodlen() : j == 0 );         
        
        String okmsg = ( ok ? "Ok" : "Falha" );
        
        String ccod = cod.substring( 0, ( limite < cod.length() ? limite : cod.length() ) );
        
        System.out.println( rotulo+" ("+okmsg+"):   "+ccod+"  "+erro+"  "+( ok ? "" : classeNome+"  J="+j+"  Codlen="+codigo.getCodlen()+"  fimesperado="+fimEsperado ) );
        
        assertTrue( ok, cod+"  J="+j );
    }
    
}
