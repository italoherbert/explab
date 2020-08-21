package italo.explab.func.ext.mat;

import italo.explab.func.Func;
import italo.explab.func.FuncFactory;
import italo.explab.var.mat.MatrizVar;

public class MatCapNCFunc extends AbstractMatQuantFunc {
        
    private final String NOME = FuncFactory.MAT_CAP_NC;

    @Override
    public int quant( MatrizVar mat ) {
        return mat.getCapacidadeNC();
    }
    
    @Override
    public Func nova() {
        return new MatCapNCFunc();
    }
        
    @Override
    public String getNome() {
        return NOME;
    }    
    
}

