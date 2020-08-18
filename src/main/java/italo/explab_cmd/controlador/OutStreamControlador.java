package italo.explab_cmd.controlador;

import italo.explab_cmd.Aplic;
import italo.explab.InterStream;

public class OutStreamControlador implements InterStream {

    private final Aplic aplic;
    
    public OutStreamControlador( Aplic aplic ) {
        this.aplic = aplic;
    }
    
    @Override
    public void envia( String texto ) {
        aplic.getGUI().getInterGUI().getGUITO().addCMDTexto( texto ); 
    }
    
}
