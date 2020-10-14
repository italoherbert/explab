package italo.explab.func.ext;

import italo.explab.ErroMSGs;
import italo.explab.MetodoParam;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.func.FuncExp;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.codigo.Codigo;
import italo.explab.func.AbstractFunc;
import italo.explab.func.Func;
import italo.explab.func.FuncFactory;
import italo.explab.func.FuncManager;
import italo.explab.func.FuncResult;
import italo.explab.func.UsuarioFunc;
import italo.explab.msg.CodigoErro;
import italo.explab.var.FuncVar;
import italo.explab.var.Var;

public class FExecFunc extends AbstractFunc {

    private final String NOME = FuncFactory.FEXEC;
    private final int QUANT_PARAMS = -1;    
             
    @Override
    public FuncResult exec( FuncExp fno, Executor executor, Codigo codigo, MetodoParam[] params ) {            
        FuncManager funcManager = executor.getAplic().getFuncManager();
        if ( params.length < 1 )
            return new FuncResult( new CodigoErro( this.getClass(), codigo, fno.getI(), ErroMSGs.PARAMS_QUANT_INVALIDA, "1" ) );
                
        Var var = params[0].getVar();
        int pos = params[0].getPos();
        
        if ( var.getTipo() == Var.FUNC ) {
            UsuarioFunc func = ((FuncVar)var).getUsuarioFunc();
            if ( func.verifica( func.getNome(), params.length-1 ) ) {
                MetodoParam[] pars = new MetodoParam[ params.length-1 ];
                for( int k = 0; k < pars.length; k++ )
                    pars[ k ] = params[ k+1 ];                
                                          
                FuncExp fexp = executor.getExecManager().getExecNoFactory().getExpFactory().novoFuncExp( fno.getI(), fno, fno.getCodigo() );
                
                Instrucao[] instrucoes = funcManager.carregaInstrucoes( fexp, func );                
                fexp.getBloco().setInstrucoes( instrucoes );
                                                
                return funcManager.exec( func, fexp, executor, codigo, pars );                
            } else {
                int quantParamsEsperado = func.getQuantParametros()+1;
                return new FuncResult( new CodigoErro( this.getClass(), codigo, ErroMSGs.PARAMS_QUANT_INVALIDA, String.valueOf( quantParamsEsperado ) ) );                 
            }
        } else {
            return new FuncResult( new CodigoErro( this.getClass(), codigo, pos, ErroMSGs.VAR_TIPO_FUNC_ESPERADO ) );
        }        
    }
            
    @Override
    public Func nova() {
        return new FExecFunc();
    }

    @Override
    public String getNome() {
        return NOME;
    }

    @Override
    public int getQuantParametros() {
        return QUANT_PARAMS;
    }
    
}
