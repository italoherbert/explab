package italo.explab.func.ext;

import italo.explab.func.AbstractFuncAdapter;
import italo.explab.ErroMSGs;
import italo.explab.func.FuncFactory;
import italo.explab.var.StringVar;

import italo.explab.MetodoParam;
import italo.explab.codigo.Codigo;
import italo.explab.func.Func;
import italo.explab.func.FuncResult;
import italo.explab.msg.CodigoErro;
import italo.explab.var.Var;

public class SetArqCharsetFunc extends AbstractFuncAdapter {

    private final String NOME = FuncFactory.SETCHARSET;
    private final int QUANT_PARAMS = 1;
    
    @Override
    public FuncResult exec( Codigo codigo, MetodoParam[] params, int i ) {                 
        if ( params.length != QUANT_PARAMS )
            return new FuncResult( new CodigoErro( this.getClass(), codigo, ErroMSGs.PARAMS_QUANT_INVALIDA, "1" ) );
        
        Var p = params[0].getVar();
        
        if ( p.getTipo() != Var.STRING ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i, ErroMSGs.PARAM_TIPO_STRING_ESPERADO );
            return new FuncResult( erro );
        }
        
        System.setProperty( "file.encoding", ((StringVar)p).getValor() );
        String charset = System.getProperty( "file.encoding" ); 
        
        return new FuncResult( new StringVar( charset ), this );
    }
    
    @Override
    public Func nova() {
        return new SetArqCharsetFunc();
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
