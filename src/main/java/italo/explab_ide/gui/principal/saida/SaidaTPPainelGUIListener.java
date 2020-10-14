package italo.explab_ide.gui.principal.saida;

public interface SaidaTPPainelGUIListener {
        
    public boolean verificaSeFechar( SaidaTPPainelGUITO guiTO );
            
    public void antesTabRemovida( SaidaTPPainelGUITO guiTO, int i );
    
    public void leituraConcluida( SaidaTPPainelGUITO guiTO, String valorLido );
        
}
