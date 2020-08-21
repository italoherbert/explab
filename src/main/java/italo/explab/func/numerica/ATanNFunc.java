package italo.explab.func.numerica;

public class ATanNFunc extends UmParametroNFunc {

    @Override
    public double calcula(double p) {
        return Math.atan( p );
    }

    @Override
    public String getNome() {
        return NFuncFactory.ATAN;
    }
    
}
