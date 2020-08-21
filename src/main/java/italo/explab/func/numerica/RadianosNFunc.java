package italo.explab.func.numerica;

public class RadianosNFunc extends UmParametroNFunc {

    @Override
    public double calcula( double p ) {
        return Math.toRadians( p );
    }

    @Override
    public String getNome() {
        return NFuncFactory.RADIANOS;
    }    
    
}
