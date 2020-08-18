package italo.explab.inter.cmd;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.PalavrasReservadas;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.cmd.node.Lance;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.msg.CodigoErro;
import italo.explab.util.ContadorUtil;

public class LanceCMDInter extends Inter {

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2) {
        InterManager manager = aplic.getInterManager();
        ContadorUtil contUtil = aplic.getContUtil();
        AnalisadorSintaticoManager asManager = aplic.getAnalisadorSintaticoManager();        
                
        AnaliseResult aresult = asManager.getLanceAnalisador().analisa( codigo, i );
        if ( aresult.getJ() == 0 )
            return new InterResult( aresult );
        
        int j = 0;      
               
        int cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.LANCE );
        if ( cont == 0 )
            return new InterResult();
                        
        Lance lance = aplic.getExecutor().getExecManager().getExecNoFactory().getCMDFactory().novoLance( i, no, codigo );

        j += cont;
        j += contUtil.contaEsps( codigo, i+j );
                        
                
        InterResult result = manager.getValorInter().interpreta( lance, no, aplic, codigo, i+j, i2 );
        if ( result.getJ() == 0 ) {
            if ( result.getErro() != null )
                return result;        
            
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.VALOR_ESPERADO );
            return new InterResult( erro );
        }                
              
        lance.setExp( (Exp)result.getInstrucaoOuExp() ); 
        
        return new InterResult( lance, j );        
    }
    
}
