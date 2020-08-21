package italo.explab.func.numerica;

public class Log10NFunc extends UmParametroNFunc {

    @Override
    public double calcula(double p) {
        return Math.log10( p );
    }

    @Override
    public String getNome() {
        return NFuncFactory.LOG10;
    }
    
}
