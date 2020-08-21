package italo.explab.inter.cmd;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.valornull.NullValor;
import italo.explab.arvore.cmd.node.Retorne;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.util.ContadorUtil;

public class RetorneCMDInter extends Inter {

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        ContadorUtil contUtil = aplic.getContUtil();                                
        
        int cont = contUtil.contaTextoValor( codigo, i, PalavrasReservadas.RETORNE );
        if ( cont == 0 )
            return new InterResult();
        
        InterManager manager = aplic.getInterManager();                 
                
        Retorne retorne = aplic.getExecutor().getExecManager().getExecNoFactory().getCMDFactory().novoRetorne( i, no, codigo );

        int j = cont;
        j += contUtil.contaEsps( codigo, i+j );
                
        char ch = codigo.getSEGCH( i+j );
        if ( ch == ';' ) {  
            NullValor nv = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoNullValor( i+j, retorne, codigo );                        
            retorne.setRetorno( nv );            
            
            return new InterResult( retorne, j );
        }
                
        InterResult result = manager.getValorInter().interpreta( retorne, no, aplic, codigo, i+j, i2 );
        if ( result.getJ() == 0 ) {
            if ( result.getErro() != null )
                return result;        
            
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.VALOR_ESPERADO );
            return new InterResult( erro );
        }
        
        j += result.getJ();
        j += contUtil.contaEsps( codigo, i+j );
        
        ch = codigo.getSEGCH( i+j );
        if ( ch != ';' ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.PONTO_E_VIRGULA_ESPERADO );
            return new InterResult( erro );
        }
        
        retorne.setRetorno( (Exp)result.getInstrucaoOuExp() );
                         
        return new InterResult( retorne, j );
    }
                
}
