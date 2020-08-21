package italo.explab;

import italo.explab.config.classes.ClassesXMLConfig;
import italo.explab.msg.InterImpr;
import italo.explab.inter.InterManager;
import italo.explab.msg.MSGManager;
import italo.explab.recursos.ClasseManager;
import italo.explab.recursos.classe.util.ClasseUtil;
import italo.explab.util.ArquivoUtil;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.arvore.Executor;
import italo.explab.arvore.grupo.Grupo;
import italo.explab.codigo.CodigoDriver;
import italo.explab.config.ajuda.AjudaXMLManager;
import italo.explab.config.msg.MSGConfig;
import italo.explab.execproc.ExecProcManager;
import italo.explab.func.FuncManager;
import italo.explab.gui.ExpLabGUIManager;
import italo.explab.matriz.MatrizManager;
import italo.explab.plotador.Plotador;
import italo.explab.recursos.GlobalRecursoManager;
import italo.explab.util.ContadorUtil;
import italo.explab.util.InterUtil;
import italo.explab.util.TenteCaptureUtil;

public class InterAplic implements CodigoDriver {
    
    private final ArquivoUtil arquivoUtil = new ArquivoUtil();
    private final ContadorUtil contUtil = new ContadorUtil();
    private final InterUtil interUtil = new InterUtil();            
    private final TenteCaptureUtil tenteCaptureUtil = new TenteCaptureUtil( this );
        
    private final AnalisadorSintaticoManager analisadorSintaticoManager = new AnalisadorSintaticoManager( contUtil );
    private final MatrizManager matrizManager = new MatrizManager();   
    private final FuncManager funcManager = new FuncManager();
    private final ExecProcManager execProcManager = new ExecProcManager();
    private final InterManager interManager = new InterManager();   
    private final Executor executor = new Executor( this );
            
    private final PalavrasReservadas palavrasReservadas = new PalavrasReservadas();
    
    private final ClasseManager classeManager = new ClasseManager();
    private final ClasseUtil classeUtil = new ClasseUtil();
    
    private final MSGConfig msgConfig = new MSGConfig( this );
    private final ClassesXMLConfig classesXMLConfig = new ClassesXMLConfig( this );
    private final AjudaXMLManager ajudaXMLManager = new AjudaXMLManager( contUtil );
    
    private final MSGManager msgManager = new MSGManager( msgConfig );
    private final InterImpr interImpr = new InterImpr( this );

    private final InterConfig config = new InterConfig();
                            
    private final InterSessaoManager isessaoManager = new InterSessaoManager( this );
    private final InterLeitura ileitura = new InterLeitura( this );
    
    private final ExpLabGUIManager explabGUIManager = new ExpLabGUIManager();
    private final Plotador plotador = new Plotador( explabGUIManager );
    
    private InterIni interIni = new InterIni();
    
    private ExpLabAplic expLabAplic;    
            
    private Grupo grupoRaiz;
    
    public InterAplic() {
        grupoRaiz = executor.getExecManager().getExecNoFactory().novoGrupoRaiz( 0, null );
    }
                
    public GlobalRecursoManager getGlobalRecursoManager() {
        return (GlobalRecursoManager)grupoRaiz.getBloco().getRecursos();
    }
    
    public InterUtil getInterUtil() {
        return interUtil;
    }

    public AnalisadorSintaticoManager getAnalisadorSintaticoManager() {
        return analisadorSintaticoManager;
    }
    
    public ArquivoUtil getArquivoUtil() {
        return arquivoUtil;
    }
    
    public MatrizManager getMatrizManager() {
        return matrizManager;
    }

    @Override
    public ContadorUtil getContUtil() {
        return contUtil;
    }

    public TenteCaptureUtil getTenteCaptureUtil() {
        return tenteCaptureUtil;
    }
        
    public FuncManager getFuncManager() {
        return funcManager;
    }
    
    public InterManager getInterManager() {
        return interManager;
    }

    public ExecProcManager getExecProcManager() {
        return execProcManager;
    }

    public InterImpr getImpr() {
        return interImpr;
    }
    
    public MSGManager getMSGManager() {
        return msgManager;
    }

    public ClasseManager getClasseManager() {
        return classeManager;
    }

    public ClasseUtil getClasseUtil() {
        return classeUtil;
    }

    public ClassesXMLConfig getClassesXMLConfig() {
        return classesXMLConfig;
    }

    public MSGConfig getMSGConfig() {
        return msgConfig;
    }

    public AjudaXMLManager getAjudaXMLManager() {
        return ajudaXMLManager;
    }
            
    public PalavrasReservadas getPalavrasReservadas() {
        return palavrasReservadas;
    }
    
    public InterConfig getConfig() {
        return config;
    }
    
    public InterSessaoManager getSessaoManager() {
        return isessaoManager;
    }

    public ExpLabGUIManager getGUIManager() {
        return explabGUIManager;
    }

    public Plotador getPlotador() {
        return plotador;
    }

    public InterLeitura getILeitura() {
        return ileitura;
    }
    
    public InterIni getInterIni() {
        return interIni;
    }

    public void setInterIni(InterIni interIni) {
        this.interIni = interIni;
    }

    public Executor getExecutor() {
        return executor;
    }
    
    public ExpLabAplic getExpLabAplic() {
        return expLabAplic;
    }

    public void setExpLabAplic(ExpLabAplic expLabAplic) {
        this.expLabAplic = expLabAplic;
    }

    public Grupo getGrupoRaiz() {
        return grupoRaiz;
    }

    public void setGrupoRaiz(Grupo blocoRaiz) {
        this.grupoRaiz = blocoRaiz;
    }   
    
}
