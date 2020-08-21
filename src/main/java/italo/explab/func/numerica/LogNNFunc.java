package italo.explab.func.numerica;

public class LogNNFunc extends DoisParametrosNFunc {
    
    @Override
    public double calcula( double a, double b ) {
        return Math.log( b ) /  Math.log( a ); 
    }

    @Override
    public String getNome() {
        return NFuncFactory.LOGN;
    }
    
}


