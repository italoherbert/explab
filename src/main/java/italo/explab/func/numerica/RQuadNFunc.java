package italo.explab.func.numerica;

public class RQuadNFunc extends UmParametroNFunc {
    
    @Override
    public double calcula(double p) {
        return Math.sqrt( p );
    }
    
    @Override
    public String getNome() {
        return NFuncFactory.RQUAD;
    }
        
}
