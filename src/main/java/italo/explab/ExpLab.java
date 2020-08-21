package italo.explab;

import italo.explab.arvore.ExecResult;
import italo.explab.arvore.grupo.Grupo;
import italo.explab.config.XMLConfigException;
import italo.explab.codigo.Codigo;
import italo.explab.config.ajuda.AjudaXMLResult;
import italo.explab.config.classes.ClasseXMLNo;
import italo.explab.execproc.NumericaExpResult;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.recursos.var.GlobalVarLista;
import italo.explab.recursos.var.Variavel;
import italo.explab.var.StringVar;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;
import java.io.FileInputStream;
import java.io.IOException;

public class ExpLab implements CFGConstantes {
                    
    private final InterAplic aplic = new InterAplic(); 
    
    public void inicializa() throws InterException {
        this.inicializa( new InterIni() );        
    }
        
    public void inicializa( InterIni ini ) throws InterException {
        System.setProperty( "file.encoding", "UTF-8" );                                
        
        aplic.setInterIni( ini );
        try {
            aplic.getMSGConfig().carrega( INFOS_ARQ, ERROS_ARQ, EXCEPTIONS_XML_ARQ ); 
                       
            if ( ini.getErrStream() != null )
                aplic.getMSGManager().setErrStream( ini.getErrStream() ); 
            if ( ini.getOutStream() != null )
                aplic.getMSGManager().setOutStream( ini.getOutStream() );            
            
            if ( ini.getVetorX() != null ) {
                MatrizVar matriz = aplic.getMatrizManager().getMatrizUtil().matriz( ini.getVetorX() );
                aplic.getGrupoRaiz().getBloco().getRecursos().getVarLista().addVar( "x", matriz ); 
            }                     
            
            Grupo raizNo = aplic.getGrupoRaiz();
            
            aplic.getClassesXMLConfig().carrega( CLASSES_XML_ARQ ); 
            for( ClasseXMLNo no : aplic.getClassesXMLConfig().getClasses() ) {
                String caminho = no.getDir();
                String arquivo = no.getArq();
                try {
                    String cod = aplic.getArquivoUtil().ler( new FileInputStream( caminho + arquivo ) );
                    
                    Codigo codigoCompleto = new Codigo( aplic, cod, arquivo );
                    Codigo classesCodigo = codigoCompleto.codigoSemComentarios();
                                        
                    InterResult result = aplic.getInterManager().getLeituraClassesPacoteInter().interpreta( raizNo, raizNo, aplic, classesCodigo, 0, classesCodigo.getCodlen() );
                    if ( result.getJ() == 0 )
                        if ( result.getErro() != null )
                            aplic.getMSGManager().enviaErro( result.getErro() );                     
                } catch ( IOException e ) {
                    String erro = aplic.getMSGManager().getErroMSG( ErroMSGs.ARQ_CLASSE_NATIVA_ERRO_LEITURA, caminho+arquivo );
                    throw new InterException( erro, e );
                }
            }                        
            
            AjudaXMLResult ajudaCFGResult = aplic.getAjudaXMLManager().carrega( AJUDA_MAP_XML_ARQ ); 
            if ( ajudaCFGResult.getErro() != null )
                aplic.getMSGManager().enviaErro( ajudaCFGResult.getErro() );                         
                        
            String usuarioDir = System.getProperty( "user.home" ).replaceAll( "\\\\", "/" ); 
            
            String userDir = System.getProperty( "user.dir" ).replaceAll( "\\\\", "/" ); 
            String barra = ( !userDir.endsWith( "/" ) ? "/" : "" );
            
            String scriptDir = userDir + barra + aplic.getConfig().getScriptsPasta();            
                                                
            aplic.getConfig().setUsuarioDir( usuarioDir ); 
            aplic.getConfig().setDiretorioCorrente( scriptDir );
            aplic.getConfig().setScriptDir( scriptDir );
            
            Variavel usuarioDirVar = new Variavel( GlobalVarLista.USUARIO_DIR, new StringVar( aplic.getConfig().getUsuarioDir() ) );
            usuarioDirVar.setConstante( true );            
            aplic.getGrupoRaiz().getBloco().getRecursos().getVarLista().addVar( usuarioDirVar );  
            
            Variavel scriptDirVar = new Variavel( GlobalVarLista.SCRIPT_DIR, new StringVar( aplic.getConfig().getScriptDir() ) );
            scriptDirVar.setConstante( true );            
            aplic.getGrupoRaiz().getBloco().getRecursos().getVarLista().addVar( scriptDirVar );  
                         
            aplic.getSessaoManager().nova();
        } catch ( IOException e ) {
            throw new InterException( "Arquivo de erros em formato inválido.", e );
        } catch ( XMLConfigException ex) {
            throw new InterException( ex.getMessage(), ex );
        }
    }
            
    public void exec( String cod ) {        
        Codigo codigo = new Codigo( aplic, cod );
        aplic.getSessaoManager().nova();
        aplic.getExecProcManager().getCMDsExecProc().exec( aplic, codigo );    
        aplic.getSessaoManager().fim();
    }
            
    public NumericaExpResult execNumericaExp( String exp ) {
        return this.execNumericaExp( exp, null );
    }
        
    public NumericaExpResult execNumericaExp( String exp, double[] vetorX ) {          
        Codigo codigo = new Codigo( aplic, exp );        
        aplic.getSessaoManager().nova();      
        
        Grupo grupoRaiz = aplic.getGrupoRaiz();
        aplic.getExecutor().getExecManager().getExecNoFactory().setGrupoRaizCodigo( grupoRaiz, codigo );

        NumericaExpResult result = aplic.getExecProcManager().getNumericaExpExecProc().exec( aplic, codigo, vetorX );
        
        aplic.getSessaoManager().fim();        
        return result;
    }
        
    public Var execEXP( String exp ) {
        Codigo codigo = new Codigo( aplic, exp );
        return this.execEXP( codigo, exp );
    }
    
    public Var execEXP( Codigo codigo, String exp ) {
        InterManager manager = aplic.getInterManager();
        
        aplic.getSessaoManager().nova();
                
        Grupo grupoRaiz = aplic.getGrupoRaiz();
        aplic.getExecutor().getExecManager().getExecNoFactory().setGrupoRaizCodigo( grupoRaiz, codigo );
        
        InterResult ir = manager.getValorInter().interpreta( grupoRaiz, grupoRaiz, aplic, codigo, 0, codigo.getCodlen() );
        
        Var result = null;
        if ( ir.getJ() > 0 ) {
            ExecResult er = aplic.getExecutor().exec( ir.getInstrucaoOuExp() );                        
            result = er.getVar();
        }
        
        aplic.getSessaoManager().fim();

        return result;
    }
    
    public void setExpLabAplic( ExpLabAplic elaplic ) {
        aplic.getExecutor().getExecManager().getCMDExecManager().getSairExec().setSairExecListener( elaplic ); 
        aplic.getILeitura().setLeituraCMDListener( elaplic ); 
        aplic.setExpLabAplic( elaplic ); 
    }
    
    public void finalizaExecucao() {
        aplic.getSessaoManager().fim();
    }
    
    public void leituraConcluida( String valor ) {
        aplic.getILeitura().leituraConcluida( valor ); 
    }
    
    public InterAplic getAplic() {
        return aplic;
    }
    
}
