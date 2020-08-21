package italo.explab.arvore.exp.num.exec;

import italo.explab.ErroMSGs;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.num.node.NumVetN1IncN2;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;

public class NumVetN1IncN2Exec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        NumVetN1IncN2 vet = (NumVetN1IncN2)no;
        
        Codigo codigo = no.getCodigo();
        
        ExecResult er = executor.exec( vet.getVetN1IncN2() );
        if ( er.isErroOuEx() )
            return er;
                        
        if ( er.getVar() == null ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, vet.getVetN1IncN2().getI(), ErroMSGs.VALOR_NAO_NULO_ESPERADO );
            return new ExecResult( erro );
        }
        
        if ( er.getVar().getTipo() != Var.MATRIZ ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, vet.getVetN1IncN2().getI(), ErroMSGs.VALOR_TIPO_MATRIZ_ESPERADO );
            return new ExecResult( erro );
        }
        
        if ( vet.isTransposta() ) {
            if ( er.getVar().getTipo() == Var.MATRIZ ) {
                MatrizVar matvar = (MatrizVar)er.getVar();
                matvar = executor.getAplic().getFuncManager().getTranspostaFunc().transposta( matvar );
                                  
                return new ExecResult( matvar );
            } else {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.VAR_TIPO_MATRIZ_ESPERADO );
                return new ExecResult( erro );
            }            
        }
        return er;
    }
    
}
