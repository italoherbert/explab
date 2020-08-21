package italo.explab.func.numerica;

public class RandomNFunc extends ZeroParametrosNFunc {

    @Override
    public double calcula() {
        return Math.random();
    }
    
    @Override
    public String getNome() {
        return NFuncFactory.RANDOM;
    }
    
}
