package italo.explab.var.num;

import italo.explab.var.Var;

public class NumeroRealVar implements RealVar {

    private double valor;
    
    public NumeroRealVar( double valor ) {
        this.valor = valor;
    }
    
    @Override
    public Var nova() {
        return new NumeroRealVar( valor );
    }
        
    @Override
    public boolean iguais(Var var) {
        if ( !(var instanceof RealVar ) )
            return false;
        if ( Double.isNaN( valor ) ) 
            return Double.isNaN( valor ) && Double.isNaN( ((RealVar)var).getValor() );
        return valor == ((RealVar)var).getValor();
    }

    @Override
    public int getTipo() {
        return REAL;
    }

    @Override
    public int getTipoCompativel() {
        return TC_NUMERICO;
    }

    @Override
    public String getStringTipo() {
        return TIPO_REAL;
    }
    
    @Override
    public double getValor() {
        return valor;
    }

    @Override
    public void setValor(double valor) {
        this.valor = valor;
    }
    
}
