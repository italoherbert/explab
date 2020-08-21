package italo.explab.arvore.exp.num.exec;

import italo.explab.ErroMSGs;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.num.node.NumIncDec;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.var.Var;

public class NumIncDecExec implements Exec {

    @Override
    public ExecResult exec(ExecNo no, Executor executor) {
        NumIncDec exp = (NumIncDec)no;
        
        Codigo codigo = no.getCodigo();
        
        ExecResult er = executor.exec( exp.getIncDec() );
        if ( er.isErroOuEx() )
            return er;
                        
        if ( er.getVar() == null ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, exp.getIncDec().getI(), ErroMSGs.VALOR_NAO_NULO_ESPERADO );
            return new ExecResult( erro );
        }
        
        if ( er.getVar().getTipo() != Var.REAL ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, exp.getIncDec().getI(), ErroMSGs.VALOR_REAL_ESPERADO );
            return new ExecResult( erro );
        }
        
        return er;
    }

}
