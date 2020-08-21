package italo.explab.func.numerica;

import italo.explab.var.mat.MatrizVar;

public class XMalhaGradeNFunc extends MalhaGradeNFunc {
    
    @Override
    protected MatrizVar calcula( MatrizVar xvet, MatrizVar yvet ) {
        int nl = yvet.getNC();
        int nc = xvet.getNC();
        MatrizVar mat = new MatrizVar( nl, nc );
        for( int i = 0; i < nl; i++ )
            for( int j = 0; j < nc; j++)
                mat.setElemento( i, j, xvet.getElemento( 0, j ) );
        return mat;
    }
    
    @Override
    public String getNome() {
        return NFuncFactory.XMALHAGRADE;
    }
    
}
