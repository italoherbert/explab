package italo.explab_cmd;

import italo.explab_cmd.controlador.OutStreamControlador;
import italo.explab_cmd.controlador.InterGUIControlador;
import italo.explab_cmd.controlador.CMDExpLabControlador;
import italo.explab_cmd.gui.CMDGUI;
import italo.explab.gui.splash.SplashGUI;
import italo.explab.InterIni;
import italo.explab.InterException;
import javax.swing.SwingUtilities;

public class ExpLabCMD {
    
    public static void main(String[] args) {        
        SplashGUI splash = new SplashGUI();
        splash.setVisible( true );
        SwingUtilities.invokeLater( () -> {
            Aplic aplic = new Aplic();
            CMDGUI gui = aplic.getGUI();
                                    
            InterGUIControlador interControlador = new InterGUIControlador( aplic );            
            CMDExpLabControlador expLabCMDControlador = new CMDExpLabControlador( aplic );
            
            gui.getInterGUI().setInterGUIListener( interControlador );
            aplic.getExpLab().setExpLabAplic( expLabCMDControlador );
            
            try {       
                OutStreamControlador streamControlador = new OutStreamControlador( aplic );
                
                InterIni ini = new InterIni();
                ini.setErrStream( streamControlador );
                ini.setOutStream( streamControlador ); 
                                                
                aplic.getExpLab().inicializa( ini );                
            } catch ( InterException ex ) {
                gui.getInterGUI().getGUITO().addCMDTexto( ex.getMessage() ); 
            }                                    
            gui.getInterGUI().setVisible( true );
            splash.setVisible( false ); 
        } );
    }
    
}
