package italo.explab.inter.exp.obj;

import italo.explab.InterAplic;
import italo.explab.PalavrasReservadas;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.classe.EsteOuSuperConstrutor;
import italo.explab.arvore.exp.Exp;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.inter.params.result.ChamadaParamInterResult;
import italo.explab.inter.params.result.ChamadaParamsInterResult;
import italo.explab.util.ContadorUtil;

public class EsteChamadaConstrutorInter extends Inter {

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        AnalisadorSintaticoManager asManager = aplic.getAnalisadorSintaticoManager();
        AnaliseResult aresult = asManager.getEsteChamadaConstrutorAnalisador().analisa( codigo, i );
        if ( aresult.getJ() == 0 )
            return new InterResult( aresult );
        
        InterManager manager = aplic.getInterManager();
        ContadorUtil contUtil = aplic.getContUtil();

        EsteOuSuperConstrutor chamadaConstrutorInst = aplic.getExecutor().getExecManager().getExecNoFactory().novoEsteConstrutor( i, no, codigo );
                        
        int j = 0;
        j += contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.ESTE );
        j += contUtil.contaEsps( codigo, i+j );   
                        
        ChamadaParamsInterResult chamadaParamsResult = 
                (ChamadaParamsInterResult)manager.getChamadaParamsInter().interpreta( chamadaConstrutorInst, base, aplic, codigo, i+j, i2 );
        
        if ( chamadaParamsResult.getJ() == 0 )
            return chamadaParamsResult;        
        
        j += chamadaParamsResult.getJ();
                
        int size = chamadaParamsResult.getParams().size();  
        Exp[] construtorParams = new Exp[ size ];        
        for( int k = 0; k < size; k++ ) {
            ChamadaParamInterResult varParamResult = chamadaParamsResult.getParams().get( k );                        
            construtorParams[ k ] = (Exp)varParamResult.getValorResult().getInstrucaoOuExp();            
        }
        
        chamadaConstrutorInst.setParams( construtorParams ); 
        
        return new InterResult( chamadaConstrutorInst, j );
    }
    
    
}
