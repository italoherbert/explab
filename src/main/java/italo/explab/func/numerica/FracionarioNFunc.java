package italo.explab.func.numerica;

public class FracionarioNFunc extends UmParametroNFunc {
    
    @Override
    public double calcula(double p) {
        return p - (int)p; 
    }

    @Override
    public String getNome() {
        return NFuncFactory.FRACIONARIO;
    }
    
}
