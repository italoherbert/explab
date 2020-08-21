package italo.explab.config.ajuda;

public class AjudaURLParams {
    
    private String pagina;
    private String ancoraID;

    public AjudaURLParams() {}
    
    public AjudaURLParams( String pagina, String ancoraID ) {
        this.pagina = pagina;
        this.ancoraID = ancoraID;
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    public String getAncoraID() {
        return ancoraID;
    }

    public void setAncoraID(String ancoraID) {
        this.ancoraID = ancoraID;
    }

}
