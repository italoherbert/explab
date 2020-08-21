package italo.explab.arvore.estruturas.exec;

import italo.explab.ErroMSGs;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.Exp;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.var.Var;

public abstract class LoopExec implements Exec {
    
    protected ExecResult condicaoValor( Exp condicao, Executor executor ) {
        Codigo codigo = condicao.getCodigo();
        
        ExecResult er = executor.exec( condicao );
        if ( er.isErroOuEx() )
            return er;
                
        if ( er.getVar() == null ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, condicao.getI(), ErroMSGs.VALOR_NAO_NULO_ESPERADO );
            return new ExecResult( erro );
        }
        
        if ( er.getVar().getTipo()!= Var.BOOLEAN ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, condicao.getI(), ErroMSGs.VALOR_BOOLEANO_ESPERADO );
            return new ExecResult( erro );
        }
        
        return er;                
    }
    
}
