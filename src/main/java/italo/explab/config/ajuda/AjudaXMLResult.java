package italo.explab.config.ajuda;

import italo.explab.msg.Erro;
import italo.explab.config.ajuda.nos.ElementoXMLNo;

public class AjudaXMLResult {
    
    private ElementoXMLNo no = null;
    private Erro erro = null;

    public AjudaXMLResult() {}                

    public AjudaXMLResult( ElementoXMLNo no ) {
        this.no = no;
    }
        
    public AjudaXMLResult( Erro erro ) {
        this.erro = erro;
    }

    public ElementoXMLNo getNo() {
        return no;
    }
       
    public Erro getErro() {
        return erro;
    }

}
