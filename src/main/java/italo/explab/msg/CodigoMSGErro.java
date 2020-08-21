package italo.explab.msg;

import italo.explab.codigo.Codigo;

public class CodigoMSGErro extends CodigoErro {
   
    private final String mensagem;

    public CodigoMSGErro( Class classe, Codigo codigo, int pos, String mensagem ) {
        super( classe, codigo, pos, null, new String[] {} );        
        this.mensagem = mensagem;
    }
    
    public String getMensagem() {
        return mensagem;
    }

}
