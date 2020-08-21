package italo.explab.arvore.estruturas.exec;

import italo.explab.ErroMSGs;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.estruturas.node.Se;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.var.BooleanVar;
import italo.explab.var.Var;

public class SeExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        Se se = (Se)no;
        
        Codigo codigo = no.getCodigo();
        
        ExecResult result = executor.exec( se.getCondicao() );
        if ( result.isErroOuEx() )
            return result;
        
        if ( result.getVar() == null ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, se.getI(), ErroMSGs.VALOR_NAO_NULO_ESPERADO );
            return new ExecResult( erro );
        }
        
        if ( result.getVar().getTipo() != Var.BOOLEAN ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, se.getI(), ErroMSGs.VALOR_BOOLEANO_ESPERADO );
            return new ExecResult( erro );
        }
        
        BooleanVar bv = (BooleanVar)result.getVar();
        if ( bv.getValor() ) {
            result = executor.exec( se.getEntaoGrupo() );            
            if ( result.getErro() != null || result.isFinalizarBloco() )
                return result;
        } else {
            result = executor.exec( se.getSenaoGrupo() );
            if ( result.getErro() != null || result.isFinalizarBloco() )
                return result;
        }
        
        return new ExecResult();
    }
    
}
