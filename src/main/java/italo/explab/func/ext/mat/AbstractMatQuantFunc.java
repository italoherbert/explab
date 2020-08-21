package italo.explab.func.ext.mat;

import italo.explab.func.AbstractFuncAdapter;
import italo.explab.ErroMSGs;
import italo.explab.MetodoParam;
import italo.explab.codigo.Codigo;
import italo.explab.func.FuncResult;
import italo.explab.msg.CodigoErro;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;
import italo.explab.var.num.NumeroRealVar;

public abstract class AbstractMatQuantFunc extends AbstractFuncAdapter {
    
    private final int QUANT_PARAMS = 1;
    
    public abstract int quant( MatrizVar mat );
    
    @Override
    public FuncResult exec( Codigo codigo, MetodoParam[] params, int i ) {
        if ( params.length != 1 ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i, ErroMSGs.PARAMS_QUANT_INVALIDA, "1" );
            return new FuncResult( erro );
        }
        
        Var var = params[0].getVar();        
        if ( var.getTipo() != Var.MATRIZ ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i, ErroMSGs.VAR_TIPO_MATRIZ_ESPERADO );
            return new FuncResult( erro );
        }
        
        MatrizVar mat = (MatrizVar)var;
        double valor = this.quant( mat );
        
        return new FuncResult( new NumeroRealVar( valor ), this );
    }
        
    @Override
    public int getQuantParametros() {
        return QUANT_PARAMS;
    }
    
    
}
