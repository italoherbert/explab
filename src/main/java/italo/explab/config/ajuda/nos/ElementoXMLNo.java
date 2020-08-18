package italo.explab.config.ajuda.nos;

public abstract class ElementoXMLNo {
    
    public final static int AJUDA = 1;
    public final static int PAGINA = 2;
    public final static int CONTEUDO = 3;
    public final static int CHAVE = 4;
    public final static int PROPRIEDADE = 5;
    
    protected ElementoXMLNo parente;

    public ElementoXMLNo( ElementoXMLNo parente ) {
        this.parente = parente;
    }        

    public abstract int getTipo();
    
    public AjudaXMLNo ajudaNo() {
        ElementoXMLNo ajudaELNo = this;
        while( ajudaELNo != null ) {
            if ( ajudaELNo.getTipo() == ElementoXMLNo.AJUDA )
                return (AjudaXMLNo)ajudaELNo;                 
            ajudaELNo = ajudaELNo.getParente();
        } 
        return null;
    }
    
    public ElementoXMLNo getParente() {
        return parente;
    }

    public void setParente(ElementoXMLNo parente) {
        this.parente = parente;
    }
    
    
    
}
