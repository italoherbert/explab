package italo.explab.func.numerica;

public class ExpNFunc extends UmParametroNFunc {

    private final String NOME = NFuncFactory.EXP;

    @Override
    public double calcula(double p) {
        return Math.exp( p );
    }

    @Override
    public String getNome() {
        return NOME;
    }
    
}
