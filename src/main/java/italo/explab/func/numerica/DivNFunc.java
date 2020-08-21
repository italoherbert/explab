package italo.explab.func.numerica;

public class DivNFunc extends DoisParametrosNFunc {

    @Override
    public double calcula(double p1, double p2) {                
        return p1 / p2;        
    }

    @Override
    public boolean isDivisaoPorZero( double p2 ) {
        return p2 == 0;
    }

    @Override
    public String getNome() {
        return NFuncFactory.DIV;
    }
    
}
