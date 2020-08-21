package italo.explab.func.numerica;

public class SenNFunc extends UmParametroNFunc {

    @Override
    public double calcula(double p) {
        return Math.sin( p );
    }

    @Override
    public String getNome() {
        return NFuncFactory.SEN;
    }
    
}
