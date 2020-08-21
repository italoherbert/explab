package italo.explab_ide;

import italo.explab.InterException;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.recursos.GlobalRecursoManager;
import italo.explab.util.ArquivoUtil;
import italo.explab.util.ContadorUtil;
import italo.explab_ide.config.IDEConfig;
import italo.explab_ide.controller.ctrl.AutoCompleteControllerCtrl;
import italo.explab_ide.controller.ctrl.JanelaControllerCtrl;
<<<<<<< HEAD
import italo.explab_ide.logica.arquivo.projeto.ProjetosXMLLeitor;
=======
import italo.explab_ide.logica.arquivo.projeto.ProjetosXMLManager;
>>>>>>> origin/versao-2.2
import italo.explab_ide.ctrl.CodigoFonteCtrl;
import italo.explab_ide.ctrl.ProjetoCtrl;
import italo.explab_ide.controller.ctrl.ProjetoOuArquivoControllerCtrl;
import italo.explab_ide.ctrl.ArquivoCtrl;
import italo.explab_ide.ctrl.exec.ExecCtrl;
import italo.explab_ide.gui.IDEGUI;
import italo.explab_ide.logica.CodigoFonteUtil;
import italo.explab_ide.logica.arquivo.ArquivoManager;
import italo.explab_ide.logica.arquivo.projeto.ProjetoConfigLeitor;
import italo.explab_ide.logica.arquivo.recursos_abertos.RecursosAbertosManager;
import italo.explab_ide.logica.format.CodigoFonteManager;
import italo.explab_ide.msg.IDEMSGManager;
import java.io.IOException;

public class ExpLabIDEAplic {
        
    private final IDEGUI gui;
    private final IDEConfig config = new IDEConfig();
    private final IDEMSGManager msgManager = new IDEMSGManager();
<<<<<<< HEAD
    private final ProjetosXMLLeitor projetosXMLLeitor = new ProjetosXMLLeitor( this );
=======
    private final ProjetosXMLManager projetosXMLLeitor = new ProjetosXMLManager( this );
>>>>>>> origin/versao-2.2
    
    private final ProjetoCtrl projetoCtrl = new ProjetoCtrl( this );
    private final ArquivoCtrl arquivoCtrl = new ArquivoCtrl( this );
    private final ExecCtrl execCtrl = new ExecCtrl( this );
    private final CodigoFonteCtrl codigoFonteCtrl;
    
    private final JanelaControllerCtrl janelaControllerCtrl = new JanelaControllerCtrl( this );
    private final ProjetoOuArquivoControllerCtrl projetoOuArquivoControllerCtrl = new ProjetoOuArquivoControllerCtrl( this );
    private final AutoCompleteControllerCtrl autoCompleteControllerCtrl = new AutoCompleteControllerCtrl( this );
    
    private final CodigoFonteManager codigoFonteManager = new CodigoFonteManager( this );
    private final ArquivoManager arquivoManager = new ArquivoManager( this );
    private final ProjetoConfigLeitor projetoConfigLeitor = new ProjetoConfigLeitor( this );
    private final RecursosAbertosManager recursosAbertosManager = new RecursosAbertosManager( this );
    
    private final CodigoFonteUtil codigoFonteUtil = new CodigoFonteUtil();
            
    private final ArquivoUtil arquivoUtil = new ArquivoUtil();
    private final ContadorUtil contadorUtil = new ContadorUtil();
    private final AnalisadorSintaticoManager asManager = new AnalisadorSintaticoManager( contadorUtil );
    
    private final GlobalRecursoManager globalRecursoManager = new GlobalRecursoManager();
    
    public ExpLabIDEAplic( String idePropArq, String guiPropArq, String infosPropArq, String errosPropArq, String projetosXML ) throws IOException, InterException {
        gui = new IDEGUI( guiPropArq );
        codigoFonteCtrl = new CodigoFonteCtrl( this, gui );
        
        config.carregaIDEConfig( idePropArq ); 
        msgManager.carrega( infosPropArq, errosPropArq );         
        projetosXMLLeitor.carrega( projetosXML );                
    }
        
    public GlobalRecursoManager getGlobalRecursoManager() {
        return globalRecursoManager;
    }
    
    public AnalisadorSintaticoManager getAnalisadorSintaticoManager() {
        return asManager;
    }
    
    public ContadorUtil getContadorUtil() {
        return contadorUtil;
    }
    
    public ArquivoUtil getArquivoUtil() {
        return arquivoUtil;
    }        

    public CodigoFonteUtil getCodigoFonteUtil() {
        return codigoFonteUtil;
    }
    
    public ArquivoManager getArquivoManager() {
        return arquivoManager;
    }
    
    public IDEGUI getGUI() {
        return gui;
    }

    public IDEConfig getConfig() {
        return config;
    }

<<<<<<< HEAD
    public ProjetosXMLLeitor getProjetosXMLLeitor() {
=======
    public ProjetosXMLManager getProjetosXMLManager() {
>>>>>>> origin/versao-2.2
        return projetosXMLLeitor;
    }
    
    public ProjetoConfigLeitor getProjetoConfigLeitor() {
        return projetoConfigLeitor;
    }

    public RecursosAbertosManager getRecursosAbertosManager() {
        return recursosAbertosManager;
    }

    public IDEMSGManager getMSGManager() {
        return msgManager;
    }

    public ProjetoCtrl getProjetoCtrl() {
        return projetoCtrl;
    }
    
    public ArquivoCtrl getArquivoCtrl() {
        return arquivoCtrl;
    }

    public CodigoFonteCtrl getCodigoFonteCtrl() {
        return codigoFonteCtrl;
    }
    
    public ExecCtrl getExecCtrl() {
        return execCtrl;
    }

    public ProjetoOuArquivoControllerCtrl getProjetoOuArquivoControllerCtrl() {
        return projetoOuArquivoControllerCtrl;
    }

    public AutoCompleteControllerCtrl getAutoCompleteControllerCtrl() {
        return autoCompleteControllerCtrl;
    }
    
    public JanelaControllerCtrl getJanelaControllerCtrl() {
        return janelaControllerCtrl;
    }

    public CodigoFonteManager getCodigoFonteManager() {
        return codigoFonteManager;
    }

}