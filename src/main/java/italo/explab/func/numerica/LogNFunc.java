package italo.explab.func.numerica;

public class LogNFunc extends UmParametroNFunc {

    @Override
    public double calcula(double p) {
        return Math.log( p );
    }

    @Override
    public String getNome() {
        return NFuncFactory.LOG;
    }
    
}
