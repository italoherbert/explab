package italo.explab.func.ext.mat;

import italo.explab.func.Func;
import italo.explab.func.FuncFactory;
import italo.explab.var.mat.MatrizVar;

public class MatCapNLFunc extends AbstractMatQuantFunc {
        
    private final String NOME = FuncFactory.MAT_CAP_NL;

    @Override
    public int quant(MatrizVar mat) {
        return mat.getCapacidadeNL();
    }
    
    @Override
    public Func nova() {
        return new MatCapNLFunc();
    }
        
    @Override
    public String getNome() {
        return NOME;
    }    
    
}

