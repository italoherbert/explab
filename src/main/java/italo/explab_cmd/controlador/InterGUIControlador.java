package italo.explab_cmd.controlador;

import italo.explab_cmd.Aplic;
import italo.explab_cmd.gui.inter.InterGUITO;
import italo.explab_cmd.gui.inter.InterGUIListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;

public class InterGUIControlador implements InterGUIListener {
    
    private final Aplic aplic;      
    private boolean executando = false;
    
    private final List<String> comandos = new ArrayList();
    private int cmdI = 0;
    
    public InterGUIControlador( Aplic aplic ) {
        this.aplic = aplic;
    }
    
    @Override
    public void cmdTeclaPressionada( InterGUITO guiTO, KeyEvent e ) {                
        int cursorPos = guiTO.getCMDTextoCursorPos();
        int docInicioCMDPos = guiTO.getDocInicioCMDPos();
        int len = guiTO.getCMDTexto().length();
                        
        if ( executando && !aplic.getLeituraManager().isLer() ) {
            if ( e.getKeyCode() == KeyEvent.VK_C && e.getModifiersEx()== KeyEvent.CTRL_DOWN_MASK ) {
                aplic.getExpLab().finalizaExecucao();                 
            } else {
                return;
            }
        }
        
        switch( e.getKeyCode() ) { 
            case KeyEvent.VK_ENTER:                
                if ( aplic.getLeituraManager().isLer() ) {
                    String valor = guiTO.getCMDTexto( aplic.getLeituraManager().getTextoPos() );                                        
                    
                    guiTO.addCMDTexto( "\n" ); 
                    
                    aplic.getExpLab().leituraConcluida( valor.replace( "\\", "\\\\" ) );                    
                } else {              
                    String comando = guiTO.getCMDCorrente();
                    if ( comando.isEmpty() ) {
                        guiTO.novoCMD();
                    } else {
                        new Thread( () -> {
                            if ( docInicioCMDPos < len ) { 

                                executando = true;
                                guiTO.setCMDTextoEditavel( false ); 
                                guiTO.addCMDTexto( "\n" ); 
                                aplic.getExpLab().exec( comando ); 

                                comandos.add( comando );
                                cmdI = comandos.size();
                                SwingUtilities.invokeLater( () -> {
                                    guiTO.setCMDTextoEditavel( true ); 
                                    guiTO.novoCMD(); 
                                    executando = false;
                                } );                        
                           } else {
                                guiTO.novoCMD();                    
                           }
                        } ).start();                
                    }
                }
                break;
            case KeyEvent.VK_LEFT:
                if ( cursorPos > docInicioCMDPos )
                    guiTO.setCMDTextoCursorPos( cursorPos-1 ); 
                break;            
            case KeyEvent.VK_UP:
                if ( cmdI > 0 ) {                    
                    cmdI--;
                    String comando = comandos.get( cmdI );
                    guiTO.setCMDCorrente( comando );
                }
                break;
            case KeyEvent.VK_DOWN:
                if ( cmdI < comandos.size()-1 ) {                    
                    cmdI++;
                    String comando = comandos.get( cmdI );
                    guiTO.setCMDCorrente( comando );
                }                                
                break;
            case KeyEvent.VK_BACK_SPACE:
                if ( cursorPos > docInicioCMDPos )
                    guiTO.removeCMDCHAnteriorAoCursor();                
                break;
            case KeyEvent.VK_HOME:
                guiTO.setCMDTextoCursorPos( guiTO.getDocInicioCMDPos() ); 
                break;            
        }        
    }

    @Override
    public void cmdMouseClick( InterGUITO guiTO, int x, int y ) {
        guiTO.cursorParaUltimaPosic();
    }
    
}
