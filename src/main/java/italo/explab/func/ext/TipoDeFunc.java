package italo.explab.func.ext;

import italo.explab.func.AbstractFuncAdapter;
import italo.explab.ErroMSGs;
import italo.explab.MetodoParam;
import italo.explab.codigo.Codigo;
import italo.explab.func.Func;
import italo.explab.func.FuncFactory;
import italo.explab.func.FuncResult;
import italo.explab.msg.CodigoErro;
import italo.explab.var.NullVar;
import italo.explab.var.StringVar;
import italo.explab.var.Var;

public class TipoDeFunc extends AbstractFuncAdapter {

    private final String NOME = FuncFactory.TIPODE;
    private final int QUANT_PARAMS = 1;
    
    @Override
    public FuncResult exec( Codigo codigo, MetodoParam[] params, int i ) {
        if ( params.length != QUANT_PARAMS )
            return new FuncResult( new CodigoErro( this.getClass(), codigo, i, ErroMSGs.PARAMS_QUANT_INVALIDA, "1" ) );
                
        Var var = params[0].getVar();        
        
        String tipo = (var == null ? new NullVar().getStringTipo() : var.getStringTipo() );
        
        return new FuncResult( new StringVar( tipo ), this );
    }
    
    @Override
    public Func nova() {
        return new TipoDeFunc();
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
