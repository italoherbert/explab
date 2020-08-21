package italo.explab.func.numerica;

public class ArredondaNFunc extends UmParametroNFunc {
    
    @Override
    public double calcula(double p) {
        return Math.round( p ); 
    }

    @Override
    public String getNome() {
        return NFuncFactory.ARREDONDA;
    }
    
}
