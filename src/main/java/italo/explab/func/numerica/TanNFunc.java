package italo.explab.func.numerica;

public class TanNFunc extends UmParametroNFunc {
    
    @Override
    public double calcula(double p) {
        return Math.tan( p );
    }

    @Override
    public String getNome() {
        return NFuncFactory.TAN;
    }
    
}
