package italo.explab.inter.exp.obj;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.BlocoNo;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.obj.NovoObjeto;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.inter.params.result.ChamadaParamInterResult;
import italo.explab.inter.params.result.ChamadaParamsInterResult;
import italo.explab.msg.CodigoErro;
import italo.explab.util.ContadorUtil;

public class NovoObjetoInter extends Inter {

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        ContadorUtil contUtil = aplic.getContUtil();
                
        int cont = contUtil.contaTextoValor( codigo, i, PalavrasReservadas.NOVO );        
        if ( cont == 0 )
            return new InterResult();                                         
                    
        InterManager manager = aplic.getInterManager();
                                                     
        NovoObjeto novoObjeto = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoNovoObjeto( i, no, codigo );

        int j = cont;
        j += contUtil.contaEsps( codigo, i+j );
                 
        cont = contUtil.contaClasseOuPacoteNomeTam( codigo, i+j );
                                
        String classeNome = codigo.getCodigo().substring( i+j, i+j+cont );                                                           
        
        j += cont;
                                
        BlocoNo bno = no.blocoNo();
        if ( bno == null ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.ERRO_BLOCO_NO_ESPERADO );
            return new InterResult( erro );
        }                                              
                  
        ChamadaParamsInterResult chamadaParamsResult = 
                (ChamadaParamsInterResult)manager.getChamadaParamsInter().interpreta( novoObjeto, base, aplic, codigo, i+j, i2 );
        
        if ( chamadaParamsResult.getJ() == 0 ) {            
            if ( chamadaParamsResult.getErro() != null )
                return chamadaParamsResult;
            
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.CLASSE_OBJ_CHAMADA_CONSTRUTOR_ERRO );
            return new InterResult( erro );
        }
        
        j += chamadaParamsResult.getJ();     
                
        int size = chamadaParamsResult.getParams().size();  
        Exp[] construtorParams = new Exp[ size ];        
        for( int k = 0; k < size; k++ ) {
            ChamadaParamInterResult varParamResult = chamadaParamsResult.getParams().get( k );                                                
            construtorParams[ k ] = (Exp)varParamResult.getValorResult().getInstrucaoOuExp();            
        }
                
        novoObjeto.setClasseNome( classeNome );
        novoObjeto.setConstrutorParams( construtorParams );                 
                                
        return new InterResult( novoObjeto, j );
    }    
    
}
