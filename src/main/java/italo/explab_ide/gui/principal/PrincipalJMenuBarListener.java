package italo.explab_ide.gui.principal;

public interface PrincipalJMenuBarListener {
    
    public void novoProjetoMIAcionado( PrincipalGUITO guiTO );
    public void novoArquivoMIAcionado( PrincipalGUITO guiTO );
    public void abrirProjetoMIAcionado( PrincipalGUITO guiTO );
    public void salvarMIAcionado( PrincipalGUITO guiTO );
    public void sairMIAcionado( PrincipalGUITO guiTO );
    
    public void desfazerMIAcionado( PrincipalGUITO guiTO );
    public void refazerMIAcionado( PrincipalGUITO guiTO );    
    public void copiarMIAcionado( PrincipalGUITO guiTO );
    public void moverMIAcionado( PrincipalGUITO guiTO );
    public void colarMIAcionado( PrincipalGUITO guiTO );
    public void selecionarTudoMIAcionado( PrincipalGUITO guiTO );
        
    public void moverFrenteMIAcionado( PrincipalGUITO guiTO );
    public void moverTrazMIAcionado( PrincipalGUITO guiTO );
    public void completarCodigoMIAcionado( PrincipalGUITO guiTO );
    
    public void ajudaLinkMIAcionado( PrincipalGUITO guiTO );
    public void ajudaPorTermoMIAcionado( PrincipalGUITO guiTO );
    
}
