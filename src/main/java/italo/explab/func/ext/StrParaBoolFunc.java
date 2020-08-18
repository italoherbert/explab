package italo.explab.func.ext;

import italo.explab.func.AbstractFuncAdapter;
import italo.explab.ErroMSGs;
import italo.explab.MetodoParam;
import italo.explab.codigo.Codigo;
import italo.explab.func.Func;
import italo.explab.func.FuncFactory;
import italo.explab.func.FuncResult;
import italo.explab.msg.CodigoErro;
import italo.explab.PalavrasReservadas;
import italo.explab.var.StringVar;
import italo.explab.var.Var;
import italo.explab.var.BooleanVar;

public class StrParaBoolFunc extends AbstractFuncAdapter {

    private final String NOME = FuncFactory.STRPARABOOL;
    private final int QUANT_PARAMS = 1;
    
    @Override
    public FuncResult exec( Codigo codigo, MetodoParam[] params, int i ) {                 
        if( params.length != QUANT_PARAMS ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i, ErroMSGs.PARAMS_QUANT_INVALIDA, String.valueOf( QUANT_PARAMS ) );
            return new FuncResult( erro );
        }
        
        Var p1 = params[0].getVar();
        
        if ( p1.getTipo() != Var.STRING ) {
            int pos = params[0].getPos();
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, pos, ErroMSGs.PARAM_TIPO_STRING_ESPERADO );
            return new FuncResult( erro );
        }
        
        String str = ((StringVar)p1).getValor();
        if ( str.equalsIgnoreCase( PalavrasReservadas.VERDADE ) ) {
            return new FuncResult( new BooleanVar( true ), this );
        } else if ( str.equalsIgnoreCase( PalavrasReservadas.FALSO ) ) { 
            return new FuncResult( new BooleanVar( false ), this ); 
        } else {
            int pos = params[0].getPos();
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, pos, ErroMSGs.PARAM_TIPO_STRING_FORMATO_BOOLEAN_ESPERADO );
            return new FuncResult( erro );
        }
    }
    
    @Override
    public Func nova() {
        return new StrParaBoolFunc();
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
