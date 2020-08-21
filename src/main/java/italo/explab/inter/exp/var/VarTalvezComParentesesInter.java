package italo.explab.inter.exp.var;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.arvore.ExecNo;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.msg.CodigoErro;
import italo.explab.util.ContadorUtil;

public class VarTalvezComParentesesInter extends Inter {

    @Override
    public InterResult interpreta(ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2) {
        AnalisadorSintaticoManager asManager = aplic.getAnalisadorSintaticoManager();
        
        AnaliseResult aresult = asManager.getVarTalvezComParentesesAnalisador().analisa( codigo, i );
        if ( aresult.getJ() == 0 )
            return new InterResult( aresult );
        
        InterManager manager = aplic.getInterManager();
        ContadorUtil contUtil = aplic.getContUtil();
        
        int j = 0;
        
        int k = 0;
        
        char ch = codigo.getSEGCH( i+j );        
        while( codigo.isCHValido( i+j ) && ch == '(' ) {
            k++;
            
            j++;
            j += contUtil.contaEsps( codigo, i+j );
            ch = codigo.getSEGCH( i+j );
        }
                
        if ( !codigo.isCHValido( i+j ) )
            return new InterResult();
                    
        InterResult result = manager.getOOVarOuMatELOuFuncInter().interpreta( no, base, aplic, codigo, i+j, i2 );
        if ( result.getJ() == 0 )
            return result;

        j += result.getJ();

        while( codigo.isCHValido( i+j ) && k > 0 ) {
            j += contUtil.contaEsps( codigo, i+j );

            if ( codigo.getSEGCH( i+j ) == ')' ) {
                k--;                    
                j++;                    
            } else {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_PARENTESES_ESPERADO );
                return new InterResult( erro );
            }
        } 

        if ( k > 0 ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_PARENTESES_ESPERADO );
            return new InterResult( erro );
        }
        
        return new InterResult( result.getInstrucaoOuExp(), j );    
    }
    
}
