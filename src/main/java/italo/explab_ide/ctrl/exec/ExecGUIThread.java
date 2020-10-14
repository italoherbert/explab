package italo.explab_ide.ctrl.exec;

import italo.explab.ExpLab;
import italo.explab.ExpLabAplic;
import italo.explab.InterException;
import italo.explab_ide.ExpLabIDEAplic;
import italo.explab_ide.IDEInfoMSGs;
import italo.explab_ide.IDELeituraManager;
import italo.explab_ide.ctrl.CodigoFonteCtrl;
import italo.explab_ide.ctrl.exec.cmd.CMD;
import italo.explab_ide.ctrl.exec.cmd.ExecCMD;
import italo.explab_ide.gui.principal.saida.SaidaTPPainelGUITO;
import italo.explab_ide.gui.principal.saida.SaidaTPPainelGUIListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExecGUIThread extends Thread implements ExpLabAplic, SaidaTPPainelGUIListener {
    
    private final ExpLabIDEAplic aplic;       
    private final ExecCMD execCMD;
    private String charset = null;
    
    private final ExpLab explab = new ExpLab();
    private final IDELeituraManager leituraManager = new IDELeituraManager();            
            
    private SaidaTPPainelGUITO guiTO = null;
    
    private boolean execucaoConcluida = false;                
    
    public ExecGUIThread( ExpLabIDEAplic aplic, ExecCMD execCMD ) {
        this( aplic, execCMD, null );
    }
    
    public ExecGUIThread( ExpLabIDEAplic aplic, ExecCMD execCMD, String charset ) {
        this.aplic = aplic;                                
        this.charset = charset;
        this.execCMD = execCMD;
        
        explab.setExpLabAplic( this ); 
    }
        
    @Override
    public void run() {   
        execucaoConcluida = false;
                        
        try {
            explab.inicializa();
        } catch (InterException ex) {
            Logger.getLogger(CodigoFonteCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }                        
                
        CMD result = execCMD.configuraEConstroiCMD( explab );
        String rotulo = result.getRotulo();
        String cmd = result.getCMD();        
                        
        guiTO = aplic.getGUI().getPrincipalGUI().getSaidaGUI().addTabPainel( rotulo );
        guiTO.getSaidaTPPainelGUI().setSaidaTPPainelGUIListener( this );
                
        explab.getAplic().getMSGManager().setErrStream( guiTO.getErroStream() );
        explab.getAplic().getMSGManager().setOutStream( guiTO.getOutStream() ); 
        
        guiTO.limpa(); 
        if ( charset != null )
            guiTO.setCharset( charset );
        guiTO.append( cmd+"\n\n" );        

        explab.exec( cmd ); 
                        
        execucaoConcluida = true;
        
        String concluidaMSG = aplic.getMSGManager().getInfo( IDEInfoMSGs.EXEC_CONCLUIDA );        
        guiTO.getOutStream().envia( concluidaMSG ); 
    }
    
    @Override
    public boolean verificaSeFechar( SaidaTPPainelGUITO guiTO) {
        return true;
    }

    @Override
    public void antesTabRemovida( SaidaTPPainelGUITO guiTO, int i) {
        explab.finalizaExecucao();
        aplic.getExecCtrl().removeExec( this );
    }
        
    @Override
    public void sair() {
        explab.finalizaExecucao();
        aplic.getExecCtrl().removeExec( this );
    }    

    @Override
    public void iniciarLeitura() {
        int textoPos = guiTO.iniciaLeitura();
        leituraManager.iniciarLeitura( textoPos );        
    }

    @Override
    public void finalizarLeitura() {
        leituraManager.finalizarLeitura();
    }
    
    @Override       
    public void limpaTela() {
        guiTO.limpa();
    }
    
    @Override
    public void leituraConcluida( SaidaTPPainelGUITO guiTO, String valorLido ) {
        explab.leituraConcluida( valorLido ); 
    }
    
    public ExpLab getExpLab() {
        return explab;
    }

    public boolean isExecucaoConcluida() {
        return execucaoConcluida;
    }

    public SaidaTPPainelGUITO getSaidaTPPainelGUITO() {
        return guiTO;
    }
    
}
