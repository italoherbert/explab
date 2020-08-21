package italo.explab_ide.logica.arquivo.recursos_abertos.codigosfonte;

public class ArquivoAberto {
    
    private final String caminho;
    private final int cursorPos;   

    public ArquivoAberto(String caminho, int cursorPos) {
        this.caminho = caminho;
        this.cursorPos = cursorPos;
    }

    public String getCaminho() {
        return caminho;
    }

    public int getCursorPos() {
        return cursorPos;
    }
    
}
