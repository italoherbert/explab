package italo.explab.func.numerica;

public class ASenNFunc  extends UmParametroNFunc {


    @Override
    public double calcula(double p) {
        return Math.asin( p );
    }

    @Override
    public String getNome() {
        return NFuncFactory.ASEN;
    }
    
}
