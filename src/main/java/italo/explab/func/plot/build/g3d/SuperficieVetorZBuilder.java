package italo.explab.func.plot.build.g3d;

import italo.explab.ErroMSGs;
import italo.explab.recursos.RecursoManager;
import italo.explab.recursos.classe.util.ClasseUtil;
import italo.explab.recursos.classe.util.ClasseUtilException;
import italo.explab.recursos.classe.util.VariavelNome;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;

public class SuperficieVetorZBuilder {
    
    public double[][] build( RecursoManager dadosObjRecursos, 
            MatrizVar xvetor, MatrizVar yvetor, 
            VariavelNome xvetorVN, VariavelNome yvetorVN, VariavelNome zvetorVN, 
            ClasseUtil classeUtil ) throws ClasseUtilException {
        
        MatrizVar zmat = classeUtil.matrizValor( dadosObjRecursos, zvetorVN );

        if ( zmat == null ) {
            String vc = classeUtil.variavelCompleta( zvetorVN );
            throw new ClasseUtilException( ClasseUtilException.VALOR_NAO_NULO_ESPERADO, vc );
        }

        if ( xvetor.getNC() != zmat.getNC() ) {
            String xvetorVC = classeUtil.variavelCompleta( xvetorVN );
            String zmatVC = classeUtil.variavelCompleta( zvetorVN );
            throw new ClasseUtilException( ErroMSGs.PLOT3D_XVET_ZMAT_DIMS_INCOMPATIVEIS, xvetorVC, zmatVC );
        }
        
        if ( yvetor.getNC() != zmat.getNL() ) {
            String yvetorVC = classeUtil.variavelCompleta( yvetorVN );
            String zmatVC = classeUtil.variavelCompleta( zvetorVN );
            throw new ClasseUtilException( ErroMSGs.PLOT3D_YVET_ZMAT_DIMS_INCOMPATIVEIS, yvetorVC, zmatVC );
        }
        
        double[][] matz = new double[ zmat.getNL() ][ zmat.getNC() ];
        for( int j = 0; j < matz.length; j++ ) {
            for( int k = 0; k < matz[ j ].length; k++ ) {
                Var zel = zmat.getElemento( j, k );
                matz[ j ][ k ] = classeUtil.realValor( zel, new VariavelNome( "("+j+","+k+")", zvetorVN ) ).getValor();
            }
        }
        
        return matz;
    }
    
}
