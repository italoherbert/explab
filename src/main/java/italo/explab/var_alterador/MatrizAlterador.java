package italo.explab.var_alterador;

import italo.explab.matriz.MatrizUtil;
import italo.explab.matriz.MatrizException;
import italo.explab.msg.InterImpr;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;

public class MatrizAlterador implements VarAlterador {
           
    private final MatrizVar matriz;
    private final int i;
    private final int j;
 
    private final MatrizUtil matrizUtil;

    public MatrizAlterador( MatrizVar matriz, int i, int j, MatrizUtil matrizUtil ) {
        this.matriz = matriz;
        this.i = i;
        this.j = j;
        this.matrizUtil = matrizUtil;
    }

    @Override
    public void alteraVar( Var var ) throws VarAlteradorException {
        try {
            matrizUtil.setElemento( matriz, i, j, var );            
        } catch ( MatrizException ex ) {
            throw new VarAlteradorException( ex );
        }
    }
    
}
