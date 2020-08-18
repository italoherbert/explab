package italo.explab;

public class InterLeitura {
    
    private String textoLido = null;
        
    private LeituraCMDListener listener = null;
    private final InterAplic aplic;
    
    public InterLeitura( InterAplic aplic ) {
        this.aplic = aplic;
    }
    
    public void iniciarLeitura() {        
        if ( listener != null )
            listener.iniciarLeitura();
    }
    
    public void leituraConcluida( String textoLido ) {
        this.textoLido = textoLido;
        
        aplic.getExecutor().getExecManager().getCMDExecManager().getLeiaExec().leituraConcluida();        
    }
    
    public void finalizarLeitura() {        
        if ( listener != null )
            listener.finalizarLeitura();
    }

    public String getTextoLido() {
        return textoLido;
    }

    public void setLeituraCMDListener(LeituraCMDListener listener) {
        this.listener = listener;
    }
    
}
