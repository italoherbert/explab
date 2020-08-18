package italo.explab.execproc;

import italo.explab.var.num.NumericaVar;

public class NumericaExpResult {

    public NumericaVar valor = null;
    public boolean expressaoValida = true;

    public NumericaExpResult( NumericaVar valor, boolean valida ) {
        this.valor = valor;
        this.expressaoValida = valida;
    }
    
    public NumericaVar getValor() {
        return valor;
    }

    public void setValor(NumericaVar valor) {
        this.valor = valor;
    }

    public boolean isExpressaoValida() {
        return expressaoValida;
    }

    public void setExpressaoValida(boolean expressaoValida) {
        this.expressaoValida = expressaoValida;
    }
    
}
