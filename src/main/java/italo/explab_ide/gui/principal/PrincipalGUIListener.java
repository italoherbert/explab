package italo.explab_ide.gui.principal;

public interface PrincipalGUIListener {
    
    public void novoProjetoBTAcionado( PrincipalGUITO guiTO );

    public void abrirProjetoBTAcionado( PrincipalGUITO guiTO );

    public void salvarTudoBTAcionado( PrincipalGUITO guiTO );
        
    public void executarBTAcionado( PrincipalGUITO guiTO );
    
    public void sairBTAcionado( PrincipalGUITO guiTO );
    
}
