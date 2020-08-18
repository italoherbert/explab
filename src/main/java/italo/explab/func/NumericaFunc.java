package italo.explab.func;

import italo.explab.MetodoParam;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.func.numerica.NFunc;
import italo.explab.func.numerica.NFuncFactory;
import italo.explab.func.numerica.NFuncResult;
import italo.explab.var.num.NumericaVar;
import italo.explab.var.Var;

public class NumericaFunc extends AbstractFuncAdapter {
                  
    private final NFunc nfunc;
    private final NFuncFactory factory;
    
    public NumericaFunc( NFuncFactory factory, NFunc nfunc ) {
        this.factory = factory;
        this.nfunc = nfunc;        
    }
        
    @Override
    public FuncResult exec( Codigo codigo, MetodoParam[] params, int i ) {                                       
        CodigoErro erro = null;
        for( int k = 0; erro == null && k < params.length; k++ )
            if ( params[ k ].getVar().getTipoCompativel() != Var.TC_NUMERICO )
                erro = new CodigoErro( this.getClass(), codigo, params[k].getPos(), "func.param.numerico.esperado" );                    
        
        if ( erro == null ) {            
            NumericaVar[] pars = new NumericaVar[ params.length ];
            for( int k = 0; k < pars.length; k++ )
                pars[k] = (NumericaVar)params[k].getVar();

            NFuncResult result = nfunc.exec( pars );
            if ( result.getErro() == null )
                return new FuncResult( result.getValor(), this );        
                        
            erro = result.getErro().criaErro( codigo, params, i );                
            return new FuncResult( erro );                                    
        } 
        return new FuncResult( erro );
    }

    @Override
    public Func nova() {
        NFunc nova = factory.criaNova( nfunc );
        return new NumericaFunc( factory, nova );
    }

    @Override
    public boolean verifica( String nome, int quantParametros ) {
        if ( quantParametros != this.getQuantParametros() && 
                ( this.getQuantParametros() != -1 && quantParametros != -1 ) ) {
            return false;
        }
        return ( nfunc.getNome().equalsIgnoreCase( nome ) );            
    }
    
    @Override
    public String getNome() {
        return nfunc.getNome();
    }

    @Override
    public int getQuantParametros() {
        return nfunc.getQuantParams();
    }
    
}
