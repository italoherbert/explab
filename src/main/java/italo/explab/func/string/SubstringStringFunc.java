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
import italo.explab.var.num.RealVar;

public class SubstringStringFunc extends AbstractFuncAdapter {

    private final String NOME = StringFuncFactory.SUBSTRING;
    private final int QUANT_PARAMS = -1;
        
    @Override
    public FuncResult exec( Codigo codigo, MetodoParam[] params, int i ) {
        if( params.length < 2 || params.length > 3 ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i, ErroMSGs.PARAMS_QUANT_INVALIDA, "2 ou 3" );
            return new FuncResult( erro );
        }
        
        Var p1 = params[0].getVar();
        Var p2 = params[1].getVar();
        
        if ( p1.getTipo() != Var.STRING ) {
            int pos = params[0].getPos();
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, pos, ErroMSGs.PARAM_TIPO_STRING_ESPERADO );
            return new FuncResult( erro );
        }
        
        if ( p2.getTipo() != Var.REAL ) {
            int pos = params[1].getPos();
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, pos, ErroMSGs.PARAM_TIPO_REAL_ESPERADO );
            return new FuncResult( erro );
        }
                        
        String str = ((StringVar)p1).getValor();
        int strI = (int) ((RealVar)p2).getValor();
        
        int strI2;
        if ( params.length > 2 ) {
            Var p3 = params[2].getVar();
            if ( p3.getTipo() != Var.REAL ) {
                int pos = params[2].getPos();
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, pos, ErroMSGs.PARAM_TIPO_REAL_ESPERADO );
                return new FuncResult( erro );
            }
            strI2 = (int) ((RealVar)p3).getValor();
        } else {
            strI2 = str.length();
        }
        
        int len = str.length();
        if ( strI < 0 || strI > len || strI2 < 0 || strI2 > len || strI > strI2 ) {                           
            int pos = params[1].getPos();
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, pos, ErroMSGs.STRING_SUBSTRING_INDICE_INVALIDO, String.valueOf(strI ), String.valueOf(strI2 ), String.valueOf( len ) );
            return new FuncResult( erro );
        }
        
        String result = str.substring(strI, strI2 );
        return new FuncResult( new StringVar( result ), this );
    }
    

    @Override
    public Func nova() {
        return new SubstringStringFunc();
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
