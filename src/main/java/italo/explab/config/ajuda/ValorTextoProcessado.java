
package italo.explab.config.ajuda;

import italo.explab.msg.Erro;

public class ValorTextoProcessado {
    
    private int j;
    private String valor;
    private Erro erro;

    public ValorTextoProcessado( String valor, int j ) {
        this.valor = valor;
        this.j = j;
    }    

    public ValorTextoProcessado( Erro erro ) {
        this.erro = erro;
    }

    public String getValor() {
        return valor;
    }

    public Erro getErro() {
        return erro;
    }

    public int getJ() {
        return j;
    }
    
}
