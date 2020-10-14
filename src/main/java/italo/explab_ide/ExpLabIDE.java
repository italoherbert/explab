package italo.explab_ide;

import italo.explab.InterException;
import italo.explab.gui.splash.SplashGUI;
import italo.explab_ide.controller.AutoCompleteController;
import italo.explab_ide.controller.CodigoFonteGUIController;
import italo.explab_ide.controller.NovoProjetoController;
import italo.explab_ide.controller.PrincipalGUIController;
import italo.explab_ide.controller.PrincipalJMenuBarController;
import italo.explab_ide.controller.ProjetoPopupMenuController;
import italo.explab_ide.controller.ProjetosGUIController;
import java.io.File;
import java.io.IOException;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import libs.gui.msg.JOptionPaneMSGGUI;
import libs.gui.msg.MSGUtil;

public class ExpLabIDE {
        
    private final static String GUI_MSGS_ARQ = "conf/ide/gui.properties";
    private final static String ERRO_MSGS_ARQ = "conf/ide/erros.properties";
    private final static String INFO_MSGS_ARQ = "conf/ide/infos.properties";
    
    private final static String IDE_CONFIG = "conf/ide/ide-config.properties";
    private final static String PROJETOS_XML_CONFIG = "conf/ide/projetos.xml";
    private final static String ARQUIVOS_ABERTOS_CONFIG = "conf/ide/arquivos-abertos.cfg";
    private final static String PROJ_NOS_PARA_EXPANDIR_CONFIG = "conf/ide/proj-nos-para-expandir.cfg";
    private final static String NO_SELECIONADO_CONFIG = "conf/ide/no-selecionado.cfg";
    
    private final static String PROJETO_DIR = "projetos";
    private final static String PROJETO_CONFIG_ARQ_PADRAO = "config.proj";
    private final static String PROJETO_EXEC_SCRIPT_PADRAO = "exec.exp";
    private final static String PROJETO_CHARSET_PADRAO = "UTF-8";
    private final static String SCRIPT_EL_EXT = ".exp";
        
    static {
        UIManager.put( "FileChooser.lookInLabelText", "Buscar em:" );
        UIManager.put( "FileChooser.fileNameLabelText", "Nome do Arquivo:" );
        UIManager.put( "FileChooser.filesOfTypeLabelText", "Arquivos do Tipo:" );
        UIManager.put( "FileChooser.upFolderToolTipText", "Pasta Mãe" );
        UIManager.put( "FileChooser.homeFolderToolTipText", "Pasta Inicio" );
        UIManager.put( "FileChooser.newFolderToolTipText", "Nova Pasta" );
        UIManager.put( "FileChooser.listViewButtonToolTipText", "Visão em Lista" );
        UIManager.put( "FileChooser.detailsViewButtonToolTipText", "Visão em Detalhes" );
        UIManager.put( "FileChooser.foldersLabelText", "Pastas" );
        UIManager.put( "FileChooser.cancelButtonText", "Cancelar" );
        UIManager.put( "FileChooser.cancelButtonToolTipText", "Cancelar" );
        UIManager.put( "FileChooser.openButtonText", "Abrir" );
        UIManager.put( "FileChooser.openButtonToolTipText", "Abrir" );
        UIManager.put( "FileChooser.saveButtonText", "Salvar" );                
        UIManager.put( "FileChooser.saveButtonToolTipText", "Salvar" );
        UIManager.put( "FileChooser.acceptAllFileFilterText", "Todos os arquivos" );
        UIManager.put( "FileChooser.openDialogTitleText", "Abertura de arquivos" );
        UIManager.put( "FileChooser.saveDialogTitleText", "Salvamento de arquivos" );
        
        UIManager.put( "OptionPane.cancelButtonText", "Cancelar" );
        UIManager.put( "OptionPane.okButtonText", "Ok" );
        UIManager.put( "OptionPane.yesButtonText", "Sim" );
        UIManager.put( "OptionPane.noButtonText", "Não" );        
    }

    public static void main( String[] args ) {               
        SplashGUI splash = new SplashGUI();
        splash.setVisible( true );
        
        ExpLabIDEAplic aplic;
        try {
            String projdir = System.getProperty( "user.dir" ); 
            if ( !projdir.endsWith( File.separator ) )
                projdir += File.separator;
            projdir += PROJETO_DIR;
                        
            aplic = new ExpLabIDEAplic( IDE_CONFIG, GUI_MSGS_ARQ, INFO_MSGS_ARQ, ERRO_MSGS_ARQ, PROJETOS_XML_CONFIG );
            
            aplic.getConfig().setScriptELExt( SCRIPT_EL_EXT ); 
            aplic.getConfig().setProjetosXMLConfigCaminho( PROJETOS_XML_CONFIG ); 
            aplic.getConfig().setArquivosAbertosConfigCaminho( ARQUIVOS_ABERTOS_CONFIG );
            aplic.getConfig().setProjNosParaExpandirConfigCaminho( PROJ_NOS_PARA_EXPANDIR_CONFIG ); 
            aplic.getConfig().setNoSelecionadoConfigCaminho(NO_SELECIONADO_CONFIG ); 
            
            aplic.getConfig().setProjetoBaseDirPadrao( projdir );
            aplic.getConfig().setProjetoConfigArqPadrao( PROJETO_CONFIG_ARQ_PADRAO );
            aplic.getConfig().setProjetoExecScriptPadrao( PROJETO_EXEC_SCRIPT_PADRAO ); 
            aplic.getConfig().setProjetoCharsetPadrao( PROJETO_CHARSET_PADRAO ); 
                                            
            aplic.getGUI().getPrincipalGUI().getProjetosGUI().getArvCellRenderer().setELExt( SCRIPT_EL_EXT ); 
            aplic.getGUI().getPrincipalGUI().getProjetosGUI().getArvGUITO().setConfig( aplic.getConfig() ); 
            
            aplic.getGUI().getPrincipalGUI().setPrincipalGUIListener( new PrincipalGUIController( aplic ) );
            aplic.getGUI().getPrincipalGUI().setPrincipalJMenuBarListener( new PrincipalJMenuBarController( aplic ) );
            aplic.getGUI().getNovoProjetoGUI().setNovoProjetoGUIListener( new NovoProjetoController( aplic ) );
                        
            aplic.getGUI().getPrincipalGUI().getProjetosGUI().setProjetosGUIListener( new ProjetosGUIController( aplic ) );
            aplic.getGUI().getPrincipalGUI().getProjetosGUI().getProjetosPopupMenu().setProjetoPopupMenuListener( new ProjetoPopupMenuController( aplic ) );            
            
            aplic.getGUI().getPrincipalGUI().getCodigoFonteGUI().setCodigoFonteGUIListener( new CodigoFonteGUIController( aplic ) );
                        
            aplic.getGUI().getAutoCompleteGUI().setAutoCompleteGUIListener( new AutoCompleteController( aplic ) ); 
            
            aplic.getProjetoCtrl().carregaProjs();                        
                                  
            SwingUtilities.invokeLater( () -> {
                aplic.getGUI().getPrincipalGUI().setVisible( true );
                splash.setVisible( false );                                                 
                
                aplic.getCodigoFonteCtrl().recuperaRecursosAbertos( aplic.getGUI().getPrincipalGUI().getProjetosGUI().getArvGUITO() );                
            });
        } catch ( IOException | InterException ex ) {
            splash.setVisible( false ); 
            new MSGUtil( new JOptionPaneMSGGUI() ).mostraErro( ex.getMessage() ); 
        }                                                        
    }
    
}
