package italo.explab.func.ext;

import italo.explab.func.AbstractFuncAdapter;
import italo.explab.MetodoParam;
import italo.explab.codigo.Codigo;
import italo.explab.func.Func;
import italo.explab.func.FuncFactory;
import italo.explab.func.FuncResult;
import italo.explab.var.StringVar;

public class GetArqCharsetFunc extends AbstractFuncAdapter {

    private final String NOME = FuncFactory.GETCHARSET;
    private final int QUANT_PARAMS = 0;
    
    @Override
    public FuncResult exec( Codigo codigo, MetodoParam[] params, int i ) {                 
        String charset = System.getProperty( "file.encoding" );       
        return new FuncResult( new StringVar( charset ), this );
    }
    
    @Override
    public Func nova() {
        return new GetArqCharsetFunc();
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
