package italo.explab.func.ext.mat;

import italo.explab.MetodoParam;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.func.FuncExp;
import italo.explab.codigo.Codigo;
import italo.explab.func.AbstractFunc;
import italo.explab.func.Func;
import italo.explab.func.FuncFactory;
import italo.explab.func.FuncResult;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;

public class TranspostaFunc extends AbstractFunc {

    private final int QUANT_PARAMS = 1;

    public MatrizVar transposta( MatrizVar matvar ) {        
        int nl = matvar.getNL();
        int nc = matvar.getNC();
                        
        MatrizVar mat = new MatrizVar( nc, nl );        
        for( int i = 0; i < nl; i++ ) {
            for( int j = 0; j < nc; j++ ) {                
                Var el = matvar.getElemento( i, j ).nova();                
                mat.setElemento( j, i, el );              
            }
        }
        
        return mat;
    }
    
    @Override
    public FuncResult exec(FuncExp fno, Executor executor, Codigo codigo, MetodoParam[] params) {
        MatrizVar p = (MatrizVar)params[0].getVar();        
        MatrizVar t = this.transposta( p );
        
        return new FuncResult( t, this );
    }
   
    @Override
    public Func nova() {
        return new TranspostaFunc();
    }
    
    @Override
    public int getQuantParametros() {
        return QUANT_PARAMS;
    }
   
    @Override
    public String getNome() {
        return FuncFactory.TRANSPOSTA;
    }
    
}
