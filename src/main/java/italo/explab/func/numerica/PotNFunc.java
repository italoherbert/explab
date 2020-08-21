package italo.explab.func.numerica;

public class PotNFunc extends DoisParametrosNFunc {

    @Override
    public double calcula(double p1, double p2) {
        return Math.pow( p1, p2 );
    }

    @Override
    public String getNome() {
        return NFuncFactory.POT;
    }
    
}
