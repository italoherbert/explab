package italo.explab_cmd;

public class CMDLeituraManager {
    
    private boolean ler = false;
    private int textoPos = 0;
    
    public void iniciarLeitura( int textoPos ) {
        this.textoPos = textoPos;
        this.ler = true;
    }
    
    public void finalizarLeitura() {
        this.ler = false;
    }
     
    public boolean isLer() {
        return ler;
    }
    
    public int getTextoPos() {
        return textoPos;
    }
    
}
