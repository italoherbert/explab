package italo.explab.func.numerica;

public class InteiroNFunc extends UmParametroNFunc {
    
    @Override
    public double calcula(double p) {
        return (int)p; 
    }

    @Override
    public String getNome() {
        return NFuncFactory.INTEIRO;
    }
    
}

