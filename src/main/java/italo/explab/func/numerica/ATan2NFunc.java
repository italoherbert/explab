package italo.explab.func.numerica;

public class ATan2NFunc extends DoisParametrosNFunc {

    @Override
    public double calcula(double p1, double p2) {
        return Math.atan2( p1, p2 );
    }

    @Override
    public String getNome() {
        return NFuncFactory.ATAN2;
    }
    
}
