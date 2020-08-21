package italo.explab_cmd;

import italo.explab_cmd.gui.CMDGUI;
import italo.explab.ExpLab;
import java.text.DecimalFormat;

public class Aplic {

    private final ExpLab explab = new ExpLab();
    private final CMDGUI gui = new CMDGUI( explab.getAplic().getGUIManager() );
    
    private final DecimalFormat decimalFormat = new DecimalFormat( "0.####" );
    
    private final CMDLeituraManager leitura = new CMDLeituraManager();
    
    public CMDGUI getGUI() {
        return gui;
    }

    public CMDLeituraManager getLeituraManager() {
        return leitura;
    }

    public ExpLab getExpLab() {
        return explab;
    }
    
    public DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }
    
}
