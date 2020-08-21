package italo.explab.arvore.cmd.exec;

import italo.explab.ErroMSGs;
import italo.explab.arvore.ExObj;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.cmd.node.Lance;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.var.ObjetoVar;
import italo.explab.var.Var;

public class LanceExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        Lance lance = (Lance)no;
        
        Codigo codigo = no.getCodigo();
        
        ExecResult er = executor.exec( lance.getExp() );
        if ( er.isErroOuEx() )
            return er;        
        
        if ( er.getVar() == null ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.VALOR_NAO_NULO_ESPERADO );
            return new ExecResult( erro );
        }
        
        if ( er.getVar().getTipo() != Var.OBJETO_REF ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.VALOR_OBJETO_ESPERADO );
            return new ExecResult( erro );
        }
                
        String classeException = executor.getAplic().getConfig().getClasseException();
                
        ObjetoVar obj = (ObjetoVar)er.getVar();                
        if ( !obj.getObjeto().instanciaDe( classeException ) ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, lance.getExp().getI(), ErroMSGs.CLASSE_DE_EXCECAO_ESPERADA );
            return new ExecResult( erro );
        }               
                        
        ExecResult result = new ExecResult();
        result.setExObj( new ExObj( obj, lance ) );
        return result;
    }
    
}
