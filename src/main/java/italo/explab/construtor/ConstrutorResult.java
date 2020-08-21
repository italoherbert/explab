package italo.explab.construtor;

import italo.explab.arvore.ExObj;
import italo.explab.msg.Erro;

public class ConstrutorResult {
    
    private Construtor construtor;
    private Erro erro;
    private ExObj exObj;
    
    public ConstrutorResult( Construtor construtor ) {
        this.construtor = construtor;
    }
    
    public ConstrutorResult( ExObj exObj ) {
        this.exObj = exObj;
    }
    
    public ConstrutorResult( Erro erro ) {
        this.erro = erro;
    }

    public Construtor getConstrutor() {
        return construtor;
    }

    public ExObj getExObj() {
        return exObj;
    }

    public Erro getErro() {
        return erro;
    }
    
}
