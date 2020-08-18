package italo.explab.arvore.exp.bool.exec;

import italo.explab.ErroMSGs;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.bool.node.BoolNaoExpValor;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.var.BooleanVar;
import italo.explab.var.Var;

public class BoolNaoExpValorExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {        
        BoolNaoExpValor exp = (BoolNaoExpValor)no;
        
        Codigo codigo = no.getCodigo();
        
        ExecResult er = executor.exec( exp.getExp() );
        if ( er.isErroOuEx() )
            return er;
        
        if ( er.getVar().getTipo() != Var.BOOLEAN ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.VALOR_BOOLEANO_ESPERADO );
            return new ExecResult( erro );
        }
        
        BooleanVar bv = (BooleanVar)er.getVar();
        if ( exp.isNot() )
            bv = new BooleanVar( !bv.getValor() );
        
        return new ExecResult( bv ); 
    }
    
}
