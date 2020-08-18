package italo.explab_ide.controller;

import italo.explab_ide.ExpLabIDEAplic;
import italo.explab_ide.IDEErroMSGs;
import italo.explab_ide.IDEInfoMSGs;
import italo.explab_ide.ctrl.exec.cmd.CMD;
import italo.explab_ide.gui.IDEGUI;
import italo.explab_ide.gui.principal.PrincipalGUITO;
import italo.explab_ide.gui.principal.PrincipalJMenuBarListener;
import italo.explab_ide.gui.principal.codigofonte.tppainel.CodigoFonteTPPainelGUITO;
import java.awt.Desktop;
import java.awt.Point;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JOptionPane;

public class PrincipalJMenuBarController implements PrincipalJMenuBarListener {

    private final ExpLabIDEAplic aplic;
    
    public PrincipalJMenuBarController( ExpLabIDEAplic aplic ) {
        this.aplic = aplic;
    }

    @Override
    public void novoProjetoMIAcionado( PrincipalGUITO guiTO ) {
        aplic.getProjetoOuArquivoControllerCtrl().novoProjetoAcionado();
    }

    @Override
    public void novoArquivoMIAcionado( PrincipalGUITO guiTO ) {
        aplic.getProjetoOuArquivoControllerCtrl().novoArqELAcionado();
    }

    @Override
    public void abrirProjetoMIAcionado( PrincipalGUITO guiTO ) {
        aplic.getProjetoOuArquivoControllerCtrl().abrirProjetoAcionado();
    }

    @Override
    public void salvarMIAcionado( PrincipalGUITO guiTO ) {
        aplic.getProjetoOuArquivoControllerCtrl().salvarTudoAcionado( guiTO ); 
    }

    @Override
    public void sairMIAcionado( PrincipalGUITO guiTO ) {
        aplic.getJanelaControllerCtrl().sairBTAcionado();
    }

    @Override
    public void desfazerMIAcionado( PrincipalGUITO guiTO ) {
        guiTO.getSelecionadoCodigoFonteEditorGUITO().anteriorCodigoFonte();
    }

    @Override
    public void refazerMIAcionado( PrincipalGUITO guiTO ) {
        guiTO.getSelecionadoCodigoFonteEditorGUITO().proximoCodigoFonte();
    }
    
    @Override
    public void copiarMIAcionado( PrincipalGUITO guiTO ) {
        guiTO.getSelecionadoCodigoFonteEditorGUITO().copiar();
    }

    @Override
    public void moverMIAcionado( PrincipalGUITO guiTO ) {
        guiTO.getSelecionadoCodigoFonteEditorGUITO().mover();
    }

    @Override
    public void colarMIAcionado( PrincipalGUITO guiTO ) {
        guiTO.getSelecionadoCodigoFonteEditorGUITO().colar();
    }

    @Override
    public void selecionarTudoMIAcionado( PrincipalGUITO guiTO ) {
        guiTO.getSelecionadoCodigoFonteEditorGUITO().selecionaTudo();
    }

    @Override
    public void moverFrenteMIAcionado( PrincipalGUITO guiTO ) {
        guiTO.getSelecionadoCodigoFonteEditorGUITO().moveParaFrente();
    }

    @Override
    public void moverTrazMIAcionado( PrincipalGUITO guiTO ) {
        guiTO.getSelecionadoCodigoFonteEditorGUITO().moveParaTraz();
    }

    @Override
    public void completarCodigoMIAcionado( PrincipalGUITO guiTO ) {       
        CodigoFonteTPPainelGUITO cfGUITO = guiTO.getSelecionadoCodigoFonteEditorGUITO();
        Point p = cfGUITO.cursorCHPonto();
        aplic.getAutoCompleteControllerCtrl().execAutoComplete( cfGUITO, p);
    }

    @Override
    public void ajudaLinkMIAcionado( PrincipalGUITO guiTO ) {
        String uri = aplic.getConfig().getDocURL();
        try {                
            if ( Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported( Desktop.Action.BROWSE ) ) {
                Desktop.getDesktop().browse( new URI( uri ) );
            } else {
                aplic.getMSGManager().mostraErro( IDEErroMSGs.AJUDA_BROWSER_NAO_STARTADO, uri ); 
            }
        }  catch ( URISyntaxException | IOException ex ) {
            aplic.getMSGManager().mostraErro( IDEErroMSGs.AJUDA_BROWSER_NAO_STARTADO, uri );             
        }  
    }

    @Override
    public void ajudaPorTermoMIAcionado( PrincipalGUITO guiTO ) {
        String msg = aplic.getMSGManager().getInfo( IDEInfoMSGs.ENTRADA_TERMOS_PARA_BUSCA );
        String titulo = aplic.getGUI().getTextoRotulo( IDEGUI.PRINCIPAL_JFRAME_TITULO );
        String termos = JOptionPane.showInputDialog( null, msg, titulo, JOptionPane.QUESTION_MESSAGE );
        if ( termos.isEmpty() ) {
            aplic.getMSGManager().mostraErro( IDEErroMSGs.AJUDA_TERMO_ESPERADO ); 
        } else {
            String comando = "ajuda "+termos;
            String rotulo = "Ajuda";
            CMD cmd = new CMD( comando, rotulo );

            aplic.getExecCtrl().executaCMD( cmd ); 
        }
    }
    
}
