package italo.explab.func.ext.mat;

import italo.explab.func.Func;
import italo.explab.func.FuncFactory;
import italo.explab.var.mat.MatrizVar;

public class MatNCFunc extends AbstractMatQuantFunc {
        
    private final String NOME = FuncFactory.MAT_NC;

    @Override
    public int quant(MatrizVar mat) {
        return mat.getNC();
    }
    
    @Override
    public Func nova() {
        return new MatNCFunc();
    }
        
    @Override
    public String getNome() {
        return NOME;
    }    
    
}

