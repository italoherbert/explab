package explab.inter.scripts2;

import italo.explab.ExpLab;
import italo.explab.InterAplic;
import italo.explab.InterException;
import italo.explab.arvore.ExecResult;
import italo.explab.codigo.Codigo;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.instrucao.to.ScriptInterVO;
import italo.explab.msg.CodigoErro;
import italo.explab.msg.Erro;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ScriptsTest {
                        
    private final boolean MOSTRAR_SAIDA = false;
    
    private final String CORRETOS_DIR = "corretos/";
    private final String ERROS_DIR = "erros/";
    
    private final String[] SCRIPT_ARQS = {                 
        "mat_s1.exp",
        "mat_s2.exp",
        "mat_s3.exp",
        "mat_s4.exp",
        "area.exp",
        "se.exp",
        "caso.exp",        
        "para.exp", 
        "enquanto.exp",
        "facaenquanto.exp",
        "caso.exp",
        "func.exp",
        "tentecapture.exp"         
    };
    
    private final String[] ERROS_SCRIPT_ARQS = {        
        "se1.exp",
        "se2.exp",
        "caso1.exp",
        "para1.exp",
        "para2.exp",
        "para3.exp",
        "enquanto1.exp",
        "facaenquanto1.exp",
        "facaenquanto2.exp",
        "func1.exp",
        "func2.exp",
        "func3.exp",        
        "tentecapture1.exp",        
        "tentecapture2.exp",
        "tentecapture3.exp",
        "tentecapture4.exp",
        "tentecapture5.exp"        
    };
                
    @Test
    public void scriptsTest() {
        for( String arg : SCRIPT_ARQS )
            this.execLeituraScript( CORRETOS_DIR + arg, false ); 
        for( String arg : ERROS_SCRIPT_ARQS )
            this.execLeituraScript( ERROS_DIR + arg, true );
    }
    
    private void execLeituraScript( String arq, boolean erroEsperado ) {                    
        InterAplic aplic = this.criaInterAplic();
        InterManager manager = aplic.getInterManager();
                
        String cod = "";
        try {
            cod = aplic.getArquivoUtil().ler( ScriptsTest.class.getResourceAsStream( arq ) );
        } catch ( IOException ex ) {
            fail( ex.getMessage() );
        }                
        
        Codigo codigo = new Codigo( aplic, cod );
        codigo = codigo.codigoSemComentarios();
        
        ScriptInterVO scriptIVO = new ScriptInterVO( arq, cod );
        InterResult result = manager.getScriptInter().interpreta( aplic.getGrupoRaiz(), aplic.getGrupoRaiz(), aplic, codigo, scriptIVO, 0, codigo.getCodlen() );
                                                             
        String erromsg = "";         
                
        if ( result.getErro() != null )
            erromsg = this.erroFormatado( aplic, result.getErro() );
              
        boolean ok = true;
        
        if ( result.getInstrucaoOuExp() != null ) {
            ExecResult er = aplic.getExecutor().exec( result.getInstrucaoOuExp() );

            ok = ( erroEsperado ? er.getErro() != null : er.getErro() == null );
            if ( !ok )
                erromsg = this.erroFormatado( aplic, er.getErro() );
        }

        String okmsg = ( ok ? "Ok" : "Falha" );
        System.out.println( arq+": ("+okmsg+") " + erromsg );
        
        assertTrue( ok );
    }
    
    private String erroFormatado( InterAplic aplic, Erro erro ) {
        String erromsg = "";
        if( erro != null ) {
            int pos = 0;
            Codigo erroCodigo = null;
            if ( erro instanceof CodigoErro ) {
                pos = ((CodigoErro)erro).getPos();                
                if ( pos >= ((CodigoErro)erro).getCodigo().getCodlen() )
                    pos = ((CodigoErro)erro).getCodigo().getCodlen()-1;
                erroCodigo = ((CodigoErro)erro).getCodigo();
            }
            String chave = erro.getChave();
            String params = Arrays.toString( erro.getParams() );                                
            String classeNome = erro.getClasse().getName();
            String codln = "";                
            if ( erroCodigo != null ) 
                codln = aplic.getContUtil().codigoLN( erroCodigo, pos );

            erromsg = chave+"  "+params+"  "+codln+"  "+classeNome;
        }
        return erromsg;
    }
    
    public InterAplic criaInterAplic() {
        ExpLab explab = new ExpLab();
        try {
            explab.inicializa();
        } catch (InterException ex) {
            Logger.getLogger(ScriptsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if ( !MOSTRAR_SAIDA )
            explab.getAplic().getMSGManager().setOutStream( ( msg ) -> {} );
        
        return explab.getAplic();
    }
    
}
