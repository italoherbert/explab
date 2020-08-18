package italo.explab_ide.gui.principal;

import italo.explab_ide.gui.principal.codigofonte.CodigoFonteGUITO;
import italo.explab_ide.gui.principal.codigofonte.tppainel.CodigoFonteTPPainelGUITO;
import italo.explab_ide.gui.principal.saida.SaidaTPPainelGUITO;
import libs.gui.arv.ArvGUITO;

public class PrincipalGUITO {

    private final PrincipalGUI gui;
    
    public PrincipalGUITO( PrincipalGUI gui ) {
        this.gui = gui;
    }    
    
    public CodigoFonteGUITO getCodigoFonteGUITO() {
        return gui.getCodigoFonteGUI().getGUITO();
    }
    
    public CodigoFonteTPPainelGUITO getSelecionadoCodigoFonteEditorGUITO() {
        return gui.getCodigoFonteGUI().getSelecionadoTPPainelGUITO();
    }
    
    public ArvGUITO getProjsArvGUITO() {
        return gui.getProjetosGUI().getArvGUITO();
    }
    
    public SaidaTPPainelGUITO getSaidaSelecionadaGUITO() {
        return gui.getSaidaGUI().getSelecionadaSaidaTPPainelGUITO();
    }
    
    public void salvoIcone() {
       gui.salvoIcone();        
    }
    
    public void salvarIcone() {
        gui.salvarIcone();
    }
    
}
