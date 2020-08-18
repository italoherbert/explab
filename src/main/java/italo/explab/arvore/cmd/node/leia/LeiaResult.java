package italo.explab.arvore.cmd.node.leia;

import italo.explab.arvore.exp.Exp;
import italo.explab.msg.Erro;

public class LeiaResult {
    
    private Exp valorLidoExp;
    private Erro erro;

    public LeiaResult() {}
    
    public LeiaResult( Exp valorLidoExp ) {
        this.valorLidoExp = valorLidoExp;
    }
    
    public LeiaResult( Erro erro ) {
        this.erro = erro;
    }
    
    public Exp getValorLidoExp() {
        return valorLidoExp;
    }

    public void setValorLidoExp(Exp valorLidoExp) {
        this.valorLidoExp = valorLidoExp;
    }

    public Erro getErro() {
        return erro;
    }

    public void setErro(Erro erro) {
        this.erro = erro;
    }
    
}
