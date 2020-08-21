package italo.explab.func.string;

import italo.explab.func.AbstractFuncAdapter;
import italo.explab.ErroMSGs;
import italo.explab.MetodoParam;
import italo.explab.codigo.Codigo;
import italo.explab.func.Func;
import italo.explab.func.FuncResult;
import italo.explab.msg.CodigoErro;
import italo.explab.var.StringVar;
import italo.explab.var.Var;
import italo.explab.var.num.NumeroRealVar;

public class IndiceDEStringFunc extends AbstractFuncAdapter {

    private final String NOME = StringFuncFactory.INDICEDE;
    private final int QUANT_PARAMS = 2;
    
    @Override
    public FuncResult exec( Codigo codigo, MetodoParam[] params, int i ) {
        if( params.length != QUANT_PARAMS ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i, ErroMSGs.PARAMS_QUANT_INVALIDA, String.valueOf( QUANT_PARAMS ) );
            return new FuncResult( erro );
        }
        
        Var p1 = params[0].getVar();
        Var p2 = params[1].getVar();
        
        if ( p1.getTipo() != Var.STRING ) {
            int pos = params[0].getPos();
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, pos, ErroMSGs.PARAM_TIPO_STRING_ESPERADO );
            return new FuncResult( erro );
        }
        
        if ( p2.getTipo() != Var.STRING ) {
            int pos = params[1].getPos();
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, pos, ErroMSGs.PARAM_TIPO_STRING_ESPERADO );
            return new FuncResult( erro );
        }
        
        String str1 = ((StringVar)p1).getValor();
        String str2 = ((StringVar)p2).getValor();
        
        int indice = str1.indexOf( str2 );
        
        return new FuncResult( new NumeroRealVar( indice ), this );
    }
    

    @Override
    public Func nova() {
        return new IndiceDEStringFunc();
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
