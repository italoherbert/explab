package italo.explab.func.numerica;

import italo.explab.var.num.NumericaVar;

public interface NFunc {
                
    public NFuncResult exec( NumericaVar... params );
    
    public int getQuantParams();
    
    public String getNome();    
            
}
