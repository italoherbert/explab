package italo.explab.func.numerica;

public class GrausNFunc extends UmParametroNFunc {

    @Override
    public double calcula( double p ) {
        return Math.toDegrees( p );
    }

    @Override
    public String getNome() {
        return NFuncFactory.GRAUS;
    }    
    
}

