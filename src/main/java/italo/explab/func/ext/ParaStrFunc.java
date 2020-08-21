package italo.explab.func.ext;

import italo.explab.ErroMSGs;
import italo.explab.MetodoParam;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.func.FuncExp;
import italo.explab.codigo.Codigo;
import italo.explab.func.AbstractFunc;
import italo.explab.func.Func;
import italo.explab.func.FuncFactory;
import italo.explab.func.FuncResult;
import italo.explab.msg.InterImpr;
import italo.explab.msg.CodigoErro;
import italo.explab.var.StringVar;
import italo.explab.var.Var;


public class ParaStrFunc extends AbstractFunc {

    private final String NOME = FuncFactory.PARASTR;
    private final int QUANT_PARAMS = 1;

    @Override
    public FuncResult exec(FuncExp fno, Executor executor, Codigo codigo, MetodoParam[] params) {                        
        if( params.length != QUANT_PARAMS ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, fno.getI(), ErroMSGs.PARAMS_QUANT_INVALIDA, String.valueOf( QUANT_PARAMS ) );
            return new FuncResult( erro );
        }
        
        InterImpr impr = executor.getAplic().getImpr();
        
        Var p1 = params[0].getVar();        
        String str = impr.formataVar( p1 );
        
        return new FuncResult( new StringVar( str ), this );        
    }
    
    @Override
    public Func nova() {
        return new ParaStrFunc();
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

