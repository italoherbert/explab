package italo.explab.func.numerica;

public class AbsNFunc extends UmParametroNFunc {

    @Override
    public double calcula(double p) {
        return Math.abs( p );
    }

    @Override
    public String getNome() {
        return NFuncFactory.ABS;
    }
    
}
