package italo.explab.func.numerica;

public class CosNFunc extends UmParametroNFunc {

    @Override
    public double calcula(double p) {
        return Math.cos( p );
    }

    @Override
    public String getNome() {
        return NFuncFactory.COS;
    }
    
}
