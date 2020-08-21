package italo.explab.arvore.cmd.exec;

import italo.explab.ErroMSGs;
import italo.explab.arvore.BlocoNo;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.cmd.node.Use;
import italo.explab.arvore.cmd.node.use.UseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.var.StringVar;

public class UseExec implements Exec {

    @Override
    public ExecResult exec(ExecNo no, Executor executor) {
        Use use = (Use)no;
        
        Codigo codigo = no.getCodigo();
        
        ExecResult er = executor.exec( use.getUseArqNomeExp() );
        if ( er.isErroOuEx() )
            return er;
        
        String useArqNome = ((StringVar)er.getVar()).getValor();
        
        BlocoNo bno = no.blocoNo();
        if ( bno == null ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.ERRO_BLOCO_NO_ESPERADO );
            return new ExecResult( erro );
        }
        
        UseResult result = use.getUseListener().interpretaClasses( bno, bno, executor.getAplic(), codigo, useArqNome, use.getUseArqNomeExp().getI() );
        if ( result.getErro() != null )
            return new ExecResult( result.getErro() );                        
                
        return new ExecResult();
    }
    
}
