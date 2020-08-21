package explab.inter.scripts.classes;

import java.io.IOException;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import italo.explab.CFGConstantes;
import italo.explab.ExpLab;
import italo.explab.InterAplic;
import italo.explab.arvore.ExecResult;
import italo.explab.codigo.Codigo;
import italo.explab.config.XMLConfigException;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.var.Var;
import italo.explab.var.BooleanVar;
import italo.explab.var.num.NumeroRealVar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AreaClasseInterTest {
    
    private final ExpLab explab = new ExpLab();       
    
    private final String[] CLASSES_ARQS = {
        "Area.exp"
    };        
    
    private final String[] INSTS = {
        "a = novo Area()",
        "y=10;",
        "y=a.l;",
        "a.l=100;",
    };
        
    private final String[] EXPS = {        
        "a.a",
        "a.b",
        "a.l",
        
        "a.raio",        
        "a.z",
        "a.w",
                
        "a.ret1.a",
        "a.ret1.b",
        "a.ret1.c",
        "a.ret2.a",
        "a.ret2.b",
        "a.ret2.c",
        "a.ret3.a",
        "a.ret3.b",
        "a.ret3.c", 
        
        "a.ret1.eh_quadrado()",
        "a.ret2.eh_quadrado()",
        "a.ret1.eh_retangulo()",
        "a.ret2.eh_retangulo()",
        
        "a.ret1.area()",
        "a.ret1.area( 5, 6 )",
        "a.quadrado()",
        
        "a.circulo.eh_menor()",                        
        "a.circulo.a.b.eh_maior()",
                        
        "pi * a.a^2"        
    };
    
    private final Var[] RESULTADOS_ESPERADOS = {        
        new NumeroRealVar( 1 ),
        new NumeroRealVar( 2 ),
        new NumeroRealVar( 100d ),
        
        new NumeroRealVar( 10 ),
        new NumeroRealVar( 4 ),
        new BooleanVar( true ),
        
        new NumeroRealVar( 5 ),
        new NumeroRealVar( 5 ),
        new NumeroRealVar( 5 ),
        new NumeroRealVar( 10 ),
        new NumeroRealVar( 10 ),
        new NumeroRealVar( 10 ),
        new NumeroRealVar( 100 ),
        new NumeroRealVar( 200 ),
        new NumeroRealVar( 300 ),
        
        new BooleanVar( true ),
        new BooleanVar( true ),
        new BooleanVar( false ),
        new BooleanVar( false ),
        
        new NumeroRealVar( 25 ),
        new NumeroRealVar( 30 ),                
        new NumeroRealVar( 10000 ),
        
        new BooleanVar( false ),                
        new BooleanVar( true ),
                        
        new NumeroRealVar( Math.PI * Math.pow( 1, 2 ) )
    };
        
    public AreaClasseInterTest() {
        try {
            explab.getAplic().getMSGConfig().carrega( CFGConstantes.INFOS_ARQ, CFGConstantes.ERROS_ARQ, CFGConstantes.EXCEPTIONS_XML_ARQ );           
        } catch ( XMLConfigException | IOException ex ) {
            Logger.getLogger(AreaClasseInterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    @Test
    public void execTest() {                        
        String[] scriptsArquivos = CLASSES_ARQS;
        String[] instrucoes = INSTS;
        String[] expressoes = EXPS;
        Var[] expressoesResultados = RESULTADOS_ESPERADOS;
        
        for( String arq : scriptsArquivos ) 
            this.execLeituraClasses( arq ); 
                
        InterAplic aplic = explab.getAplic();
                                      
        for( int i = 0; i < instrucoes.length; i++ ) {
            String inst = instrucoes[ i ];
            Codigo instCodigo = new Codigo( aplic, inst );
            InterResult result = aplic.getInterManager().getInstrucaoInter().interpreta( aplic.getGrupoRaiz(), aplic.getGrupoRaiz(), aplic, instCodigo, 0, instCodigo.getCodlen() );
            
            if ( result.getJ() == 0 && result.getErro() != null )
                explab.getAplic().getMSGManager().enviaErro( result.getErro() );                         

            assertTrue( result.getJ() > 0, instrucoes[ i ] );                        
            
            ExecResult er = aplic.getExecutor().exec( result.getInstrucaoOuExp() );
            if ( er.getErro() != null )
                explab.getAplic().getMSGManager().enviaErro( er.getErro() );                                    
        }        
        
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
    
    private void execLeituraClasses( String arq ) {
        InterAplic aplic = explab.getAplic();
        InterManager manager = aplic.getInterManager();
        
        String cod = "";
        try {
            cod = aplic.getArquivoUtil().ler( AreaClasseInterTest.class.getResourceAsStream( arq ) );
        } catch ( IOException ex ) {
            fail( ex.getMessage() );
        }                
        
        Codigo codigo = new Codigo( aplic, cod );
        codigo = codigo.codigoSemComentarios();
        InterResult result = manager.getLeituraClassesPacoteInter().interpreta( aplic.getGrupoRaiz(), aplic.getGrupoRaiz(), aplic, codigo, 0, codigo.getCodlen() );
        
        boolean ok = ( result.getJ() > 0 );
        String okmsg = ( ok ? "Ok" : "Falha" );
        
        System.out.println( arq+": ("+okmsg+")" );
                
        if ( !ok && result.getErro() != null )
            explab.getAplic().getMSGManager().enviaErro( result.getErro() );                    
                
        assertTrue( ok );
    }
    
}
