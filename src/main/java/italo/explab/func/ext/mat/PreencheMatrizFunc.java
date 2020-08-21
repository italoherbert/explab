package italo.explab.func.ext.mat;

import italo.explab.func.AbstractFuncAdapter;
import italo.explab.ErroMSGs;
import italo.explab.MetodoParam;
import italo.explab.codigo.Codigo;
import italo.explab.func.Func;
import italo.explab.func.FuncFactory;
import italo.explab.func.FuncResult;
import italo.explab.msg.CodigoErro;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;
import italo.explab.var.mat.NumericaMatrizVar;
import italo.explab.var.num.RealVar;

public class PreencheMatrizFunc extends AbstractFuncAdapter {
    
    private final String NOME = FuncFactory.PREENCHE;
    private final int QUANT_PARAMS = -1;

    @Override
    public FuncResult exec( Codigo codigo, MetodoParam[] params, int i ) {
        if ( params.length < 2 || params.length > 3 ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, i, ErroMSGs.PARAMS_QUANT_INVALIDA, "2 ou 3" );
            return new FuncResult( erro );
        }
        
        int nl = 1;
        int nc;
        Var valor;
        
        Var p1 = params[0].getVar();                
        if ( p1.getTipo() != Var.REAL ) {
            int pos = params[0].getPos();
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, pos, ErroMSGs.PARAM_TIPO_REAL_ESPERADO );
            
            return new FuncResult( erro );
        }
        
        Var p2 = params[1].getVar();                
        if ( p2.getTipo() != Var.REAL ) {
            int pos = params[0].getPos();
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, pos, ErroMSGs.PARAM_TIPO_REAL_ESPERADO );
            
            return new FuncResult( erro );
        }

        if ( params.length == 3 ) {
            nl = (int)((RealVar)p1).getValor();
            nc = (int)((RealVar)p2).getValor();
            
            valor = params[2].getVar();                                        
        } else {
            nc = (int)((RealVar)p1).getValor();
            valor = params[1].getVar();
        }
                        
        MatrizVar mat = new MatrizVar( nl, nc );        
        for( int k = 0; k < nl; k++ )
            for( int k2 = 0; k2 < nc; k2++ )
                mat.setElemento( k, k2, valor.nova() );                                   
        
        return new FuncResult( new NumericaMatrizVar( mat ), this ); 
    }

    @Override
    public Func nova() {
        return new PreencheMatrizFunc();
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
