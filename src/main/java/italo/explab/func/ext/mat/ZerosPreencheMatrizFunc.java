package italo.explab.func.ext.mat;

import italo.explab.func.AbstractFuncAdapter;
import italo.explab.ErroMSGs;
import italo.explab.MetodoParam;
import italo.explab.codigo.Codigo;
import italo.explab.func.Func;
import italo.explab.func.FuncFactory;
import italo.explab.func.FuncResult;
import italo.explab.msg.CodigoErro;
import italo.explab.var.num.NumeroRealVar;

public class ZerosPreencheMatrizFunc extends AbstractFuncAdapter {

    private final String NOME = FuncFactory.ZEROS;
    private final int QUANT_PARAMS = -1;
    
    private final PreencheMatrizFunc preencheMatrizFunc = new PreencheMatrizFunc();
    
    @Override
    public FuncResult exec( Codigo codigo, MetodoParam[] params, int i ) {
        if ( params.length < 1 || params.length > 2 ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i, ErroMSGs.PARAMS_QUANT_INVALIDA, "1 ou 2" );
            return new FuncResult( erro );
        }
        
        MetodoParam valorMP = new MetodoParam( new NumeroRealVar( 0 ) );
        
        MetodoParam[] params2;
        if ( params.length == 1 ) {
            params2 = new MetodoParam[] { params[0], valorMP };
        } else {
            params2 = new MetodoParam[] { params[0], params[1], valorMP };
        }
        
        return preencheMatrizFunc.exec( codigo, params2, i );
    }

    @Override
    public Func nova() {
        return new ZerosPreencheMatrizFunc();
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
