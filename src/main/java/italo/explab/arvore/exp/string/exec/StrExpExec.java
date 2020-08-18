package italo.explab.arvore.exp.string.exec;

import italo.explab.ErroMSGs;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.string.node.StrExp;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.var.StringVar;

public class StrExpExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        StrExp exp = (StrExp)no;
        
        Codigo codigo = no.getCodigo();
        
        ExecResult r1 = executor.exec( exp.getExp1() );
        if ( r1.isErroOuEx() )
            return r1;
        
        if ( r1.getVar() == null ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, exp.getExp1().getI(), ErroMSGs.VALOR_NAO_NULO_ESPERADO );
            return new ExecResult( erro );
        }

        ExecResult r2 = executor.exec( exp.getExp2() );
        if ( r2.isErroOuEx() )
            return r2;
        
        if ( r2.getVar() == null ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, exp.getExp2().getI(), ErroMSGs.VALOR_NAO_NULO_ESPERADO );
            return new ExecResult( erro );
        }
                
        String s1 = executor.getAplic().getImpr().formataVar( r1.getVar() );
        String s2 = executor.getAplic().getImpr().formataVar( r2.getVar() );
        
        return new ExecResult( new StringVar( s1+s2 ) );
    }
    
}

