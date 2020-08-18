package italo.explab.func.numerica;

public class ACosNFunc extends UmParametroNFunc {

    @Override
    public double calcula(double p) {
        return Math.acos( p );
    }

    @Override
    public String getNome() {
        return NFuncFactory.ACOS;
    }
    
}
