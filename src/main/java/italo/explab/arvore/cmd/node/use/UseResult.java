package italo.explab.arvore.cmd.node.use;

import italo.explab.msg.Erro;

public class UseResult {
    
    private Erro erro;
    
    public UseResult() {}
    
    public UseResult( Erro erro ) {
        this.erro = erro;
    }

    public Erro getErro() {
        return erro;
    }

    public void setErro(Erro erro) {
        this.erro = erro;
    }
    
}
