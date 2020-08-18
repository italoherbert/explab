package italo.explab.inter.params;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.arvore.ExecNo;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.inter.params.result.ChamadaParamInterResult;
import italo.explab.inter.params.result.ChamadaParamsInterResult;
import italo.explab.msg.CodigoErro;
import italo.explab.util.ContadorUtil;
import java.util.ArrayList;
import java.util.List;

public class ChamadaParamsInter extends Inter {   

    @Override
    public ChamadaParamsInterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        List<ChamadaParamInterResult> params = new ArrayList();
        
        InterManager manager = aplic.getInterManager();
        ContadorUtil contUtil = aplic.getContUtil();
                    
        int j = 1;
                                                    
        while ( i+j < i2 ) {
            j += contUtil.contaEsps( codigo, i+j );

            char ch = codigo.getSEGCH( i+j );
            if ( ch == ')' )
                return new ChamadaParamsInterResult( params, j+1 );                                                

            int valorJ = j;

            InterResult result = manager.getValorInter().interpreta( base, base, aplic, codigo, i+j, i2 );                                                                                
            if ( result.getJ() == 0 ) { 
                j += contUtil.contaEsps( codigo, i+j );
                if ( codigo.getSEGCH( i+j ) == ')' ) {                    
                    return new ChamadaParamsInterResult( params, j+1 );        
                } else {
                    return new ChamadaParamsInterResult( result );
                }
            }
            j += result.getJ();
                
            params.add( new ChamadaParamInterResult( result, i, valorJ ) );

            j += contUtil.contaEsps( codigo, i+j );                                                                                    

            ch = codigo.getSEGCH( i+j );
            if ( ch == ',' || ch == ')' ) {
                if ( ch == ')' )
                    return new ChamadaParamsInterResult( params, j+1 );                                            

                j++;                
            } else {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_PARENTESES_OU_VIRGULA_ESPERADO );
                return new ChamadaParamsInterResult( erro );                    
            }         
        }
        
        CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, "parentese.ou.virgula.esperado" );
        return new ChamadaParamsInterResult( erro );        
    }
            
}
