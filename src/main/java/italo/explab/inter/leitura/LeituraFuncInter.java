package italo.explab.inter.leitura;

import italo.explab.ErroMSGs;
import italo.explab.InterAplic;
import italo.explab.codigo.Codigo;
import italo.explab.func.UsuarioFunc;
import italo.explab.msg.CodigoErro;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterTO;
import italo.explab.inter.leitura.result.LeituraFuncInterResult;
import italo.explab.inter.leitura.result.LeituraParamsECorpoFuncInterResult;
import italo.explab.util.ContadorUtil;

public class LeituraFuncInter extends Inter {
        
    @Override
    public LeituraFuncInterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {                               
        ContadorUtil contUtil = aplic.getContUtil();
                                
        int cont = contUtil.contaTextoValor( codigo, i, PalavrasReservadas.FUNC );
        if ( cont == 0 ) 
            return new LeituraFuncInterResult();        
        
        InterManager manager = aplic.getInterManager();        
        
        int j = cont;
        j += contUtil.contaEsps( codigo, i+j );
                
        int nomePos = i+j;                

        cont = contUtil.contaVarNomeTam( codigo, i+j );
        if ( cont == 0 ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.FUNC_NOME_ESPERADO );
            return new LeituraFuncInterResult( erro );
        }
        
        String nome = codigo.getCodigo().substring( i+j, i+j+cont );
        
        PalavrasReservadas prs = aplic.getPalavrasReservadas();
                                    
        boolean ehPR = prs.verificaSePalavraReservada( nome );
        if ( ehPR ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, nomePos, ErroMSGs.EXISTE_COMO_PALAVRA_RESERVADA , nome );
            return new LeituraFuncInterResult( erro );
        }
        
        j += cont;
        j += contUtil.contaEsps( codigo, i+j );
        
        LeituraParamsECorpoFuncInterResult result = 
                (LeituraParamsECorpoFuncInterResult)manager.getLeituraParamsECorpoInter().interpreta( no, base, aplic, codigo, i+j, i2 );        
        
        if ( result.getJ() == 0 ) {
            if ( result.getErro() == null )
                return new LeituraFuncInterResult( result );
            
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j, ErroMSGs.PARAM_DEF_INVALIDA );
            return new LeituraFuncInterResult( erro );
        }
        
        j += result.getJ();
                        
        String[] params = result.getParams();
        String[] exceptionClasses = result.getExceptionClasses();
        Instrucao[] instrucoes = result.getInstrucoes();
        
        UsuarioFunc func = new UsuarioFunc( nome, params, exceptionClasses, instrucoes );
        return new LeituraFuncInterResult( func, j );
    }
        
}
