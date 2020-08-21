package explab.inter.scripts;

import java.io.IOException;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import italo.explab.ExpLab;
import italo.explab.InterAplic;
import italo.explab.InterException;
import italo.explab.arvore.ExecResult;
import italo.explab.codigo.Codigo;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.instrucao.to.ScriptInterVO;
import italo.explab.var.Var;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractScriptInterTest {
    
    private final String SCRIPT_NOME_PADRAO = "script.exp";
        
    public abstract Class getTesteClasse();
                
    public abstract String[] getExpressoes();
    
    public abstract Var[] getExpressoesResultados();
    
    public void executaScripts( InterAplic aplic ) {}
        
    public AbstractScriptInterTest() {
        
    }
        
    @Test
    public void execTest() {                        
        Class testeClasse = this.getTesteClasse();
        String[] expressoes = this.getExpressoes();
        Var[] expressoesResultados = this.getExpressoesResultados();        
                
        InterAplic aplic = this.criaInterAplic();
                
        this.executaScripts( aplic ); 
        
        Arquivo arq = new Arquivo( SCRIPT_NOME_PADRAO, testeClasse.getResourceAsStream( SCRIPT_NOME_PADRAO ) );
        this.execLeituraScript( arq, aplic );
                
        for( int i = 0; i < expressoes.length; i++ ) {
            String exp = expressoes[ i ];

            Codigo expCodigo = new Codigo( aplic, exp );
            InterResult result = aplic.getInterManager().getValorInter().interpreta( aplic.getGrupoRaiz(), aplic.getGrupoRaiz(), aplic, expCodigo, 0, expCodigo.getCodlen() );
                                    
            assertTrue( result.getJ() > 0, exp );
            assertNotNull( result.getInstrucaoOuExp(), exp );
            
            ExecResult er = aplic.getExecutor().exec( result.getInstrucaoOuExp() );
            Var var = er.getVar();
            
            assertNotNull( var, exp );
            
            //System.out.println( exp+"  "+ aplic.getImpr().formataVar( var ) +"  "+aplic.getImpr().formataVar( expressoesResultados[i] ) );
                        
            assertTrue( var.iguais( expressoesResultados[ i ] ), exp );
        }         
    }
    
    protected void execLeituraScript( Arquivo arq, InterAplic aplic ) {
        InterManager manager = aplic.getInterManager();
        
        String cod = "";
        try {
            cod = aplic.getArquivoUtil().ler( arq.getInputStream() );
        } catch ( IOException ex ) {
            fail( ex.getMessage() );
        }                
        
        Codigo codigo = new Codigo( aplic, cod );
        
        ScriptInterVO scriptIVO = new ScriptInterVO( arq.getNome(), cod );
        InterResult result = manager.getScriptInter().interpreta( aplic.getGrupoRaiz(), aplic.getGrupoRaiz(), aplic, codigo, scriptIVO, 0, codigo.getCodlen() );
        
        boolean ok = ( result.getJ() > 0 );
        String okmsg = ( ok ? "Ok" : "Falha" );
        
        System.out.println( arq+": ("+okmsg+")" );
        
        if ( !ok && result.getErro() != null )
            aplic.getMSGManager().enviaErro( result.getErro() );                            
                
        assertTrue( ok );
        
        ExecResult er = aplic.getExecutor().exec( aplic.getGrupoRaiz() );
        if ( er.getErro() != null )
            aplic.getMSGManager().enviaErro( er.getErro() ); 
        
        assertNull( er.getErro() );
    }
    
    private InterAplic criaInterAplic() {
        ExpLab explab = new ExpLab();
        try {
            explab.inicializa();
        } catch (InterException ex) {
            Logger.getLogger(AbstractScriptInterTest.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return explab.getAplic();
    }
    
}
