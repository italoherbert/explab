package italo.explab.func.ext.mat;

import italo.explab.func.Func;
import italo.explab.func.FuncFactory;
import italo.explab.var.mat.MatrizVar;

public class MatNLFunc extends AbstractMatQuantFunc {
        
    private final String NOME = FuncFactory.MAT_NL;

    @Override
    public int quant(MatrizVar mat) {
        return mat.getNL();
    }
    
    @Override
    public Func nova() {
        return new MatNLFunc();
    }
        
    @Override
    public String getNome() {
        return NOME;
    }    
    
}


