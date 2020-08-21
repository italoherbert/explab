package italo.explab.inter.exp.mat;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.matriz.indice.ExpMatrizIndice;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.inter.exp.mat.result.MatrizIndicesInterResult;
import italo.explab.msg.CodigoErro;
import italo.explab.util.ContadorUtil;

public class ChamadaMatIndicesInter extends Inter {

    @Override
    public MatrizIndicesInterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {                        
        char ch = codigo.getSEGCH( i );
        if ( ch != '(' )
            return new MatrizIndicesInterResult();        
        
        InterManager manager = aplic.getInterManager();
        ContadorUtil contUtil = aplic.getContUtil();
        
        ExpMatrizIndice indiceI = null;
        ExpMatrizIndice indiceJ = null;
        int nparams = 0;
        int indiceIPos = i;
        int indiceJPos = i;
        
        int j = 1;
                
        while ( i+j < i2 ) {
            j += contUtil.contaEsps( codigo, i+j );
            
            ch = codigo.getSEGCH( i+j ); 
            if ( ch == ':' ) {
                j++;

                if ( nparams == 0 ) {
                    indiceIPos = i+j;
                    indiceI = new ExpMatrizIndice( indiceIPos, ExpMatrizIndice.VETOR );                                               
                    nparams++;
                } else if ( nparams == 1 ) {
                    indiceJPos = i+j;
                    indiceJ = new ExpMatrizIndice( indiceJPos, ExpMatrizIndice.VETOR );
                    nparams++;
                }
            } else {              
                InterResult result = manager.getNumExpInter().interpreta( base, base, aplic, codigo, i+j, i2 );
                if ( result.getJ() == 0 )
                    return new MatrizIndicesInterResult( result );
                                
                j += result.getJ();
                
                if ( nparams == 0 ) {
                    indiceIPos = i+j;
                    indiceI = new ExpMatrizIndice( (Exp)result.getInstrucaoOuExp(), indiceIPos, ExpMatrizIndice.NORMAL );
                    nparams++;
                } else if ( nparams == 1 ) {
                    indiceJPos = i+j;
                    indiceJ = new ExpMatrizIndice( (Exp)result.getInstrucaoOuExp(), indiceJPos, ExpMatrizIndice.NORMAL );
                    nparams++;
                }
            }                

            j += contUtil.contaEsps( codigo, i+j );

            ch = codigo.getSEGCH( i+j );
            if ( ch == ',' || ch == ')' ) {
                j++;
                if ( ch == ')' )                        
                    return new MatrizIndicesInterResult( indiceI, indiceJ, nparams, indiceIPos, indiceJPos, j );
            } else {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_PARENTESES_OU_VIRGULA_ESPERADO );
                return new MatrizIndicesInterResult( erro );
            }
        }
        
        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_PARENTESES_ESPERADO );
        return new MatrizIndicesInterResult( erro );
    }
    
}
