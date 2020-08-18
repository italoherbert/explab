package italo.explab.inter.exp.mat;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.matriz.vetN1IncN2.VetN1IncN2;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.msg.CodigoErro;
import italo.explab.util.ContadorUtil;

public class VetN1IncN2Inter extends Inter {
    
    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {               
        ContadorUtil contUtil = aplic.getContUtil();
                
        int j = 0;
        j += contUtil.contaEsps( codigo, i+j );
                                        
        if ( codigo.getSEGCH( i+j ) != '[' )
            return new InterResult();
        
        InterManager manager = aplic.getInterManager();
        
        VetN1IncN2 vet = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoVetN1IncN2( no.getI(), no, codigo );

        j++;
        j += contUtil.contaEsps( codigo, i+j );
        
        InterResult result1 = manager.getNumExpInter().interpreta( vet, base, aplic, codigo, i+j, i2 );
        if ( result1.getJ() == 0 )
            return result1;
        
        j += result1.getJ();                
        j += contUtil.contaEsps( codigo, i+j );
            
        if ( codigo.getSEGCH( i+j ) != ':' )
            return new InterResult();        
                
        j++;
        j += contUtil.contaEsps( codigo, i+j );
                        
        InterResult result2 = manager.getNumExpInter().interpreta( vet, base, aplic, codigo, i+j, i2 );
        if ( result2.getJ() == 0 )
            return result2;
                            
        j += result2.getJ();
        j += contUtil.contaEsps( codigo, i+j );
            
        if ( codigo.getSEGCH( i+j ) != ':' ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.DOIS_PONTOS_ESPERADO );
            return new InterResult( erro );
        }
                
        j++;
        j += contUtil.contaEsps( codigo, i+j );
                                
        InterResult result3 = manager.getNumExpInter().interpreta( vet, base, aplic, codigo, i+j, i2 );
        if ( result3.getJ() == 0 )
            return result3;
                        
        j += result3.getJ();
        j += contUtil.contaEsps( codigo, i+j );
        
        if ( codigo.getSEGCH( i+j ) != ']' ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FECHA_COLCHETES_ESPERADO );
            return new InterResult( erro );
        }
        
        j++;
                   
        vet.setN1( (Exp)result1.getInstrucaoOuExp() );
        vet.setInc( (Exp)result2.getInstrucaoOuExp() );
        vet.setN2( (Exp)result3.getInstrucaoOuExp() );
                
        return new InterResult( vet, j );
    }
    
}
