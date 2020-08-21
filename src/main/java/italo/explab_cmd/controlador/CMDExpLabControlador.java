package italo.explab_cmd.controlador;

import italo.explab.ExpLabAplic;
import italo.explab_cmd.Aplic;

public class CMDExpLabControlador implements ExpLabAplic {
        
    private final Aplic aplic; 

    public CMDExpLabControlador( Aplic aplic ) {
        this.aplic = aplic;                        
    }
    
    @Override
    public void sair() {
        aplic.getExpLab().finalizaExecucao();
        aplic.getGUI().getInterGUI().dispose();
        System.exit( 0 );
    }    

    @Override
    public void iniciarLeitura() {
        int textoPos = aplic.getGUI().getInterGUI().getGUITO().getCMDTexto().length();
        aplic.getLeituraManager().iniciarLeitura( textoPos );
        aplic.getGUI().getInterGUI().getGUITO().setCMDTextoEditavel( true ); 
    }

    @Override
    public void finalizarLeitura() {
        aplic.getLeituraManager().finalizarLeitura();
        aplic.getGUI().getInterGUI().getGUITO().setCMDTextoEditavel( false );         
    }
    
    @Override       
    public void limpaTela() {
        aplic.getGUI().getInterGUI().getGUITO().limpaTela();
    }
    
}
