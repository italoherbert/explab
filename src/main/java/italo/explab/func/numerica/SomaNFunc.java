package italo.explab.func.numerica;

public class SomaNFunc extends DoisParametrosNFunc {
        
    @Override
    public double calcula(double p1, double p2) {
        return p1 + p2;
    }

    @Override
    public String getNome() {
        return NFuncFactory.SOMA;
    }
    
}
