package italo.explab.func.numerica;

import italo.explab.var.num.NumericaVar;

public class NFuncResult {

    private NumericaVar valor;
    private NFuncErro erro;
    private boolean divisaoPorZero = false;
    
    public NFuncResult( NumericaVar valor ) {
        this.valor = valor;
    }
    
    public NFuncResult( NFuncErro erro ) {
        this.erro = erro;
    }
    
    public NumericaVar getValor() {
        return valor;
    }
            
    public NFuncErro getErro() {
        return erro;
    }

    public void setValor(NumericaVar valor) {
        this.valor = valor;
    }

    public void setErro(NFuncErro erro) {
        this.erro = erro;
    }

    public boolean isDivisaoPorZero() {
        return divisaoPorZero;
    }

    public void setDivisaoPorZero(boolean divisaoPorZero) {
        this.divisaoPorZero = divisaoPorZero;
    }
    
}
