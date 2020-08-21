package italo.explab.arvore.exp.num.exec;

import italo.explab.ErroMSGs;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.num.node.NumMat;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;

public class NumMatExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {        
        NumMat exp = (NumMat)no;
        
        Codigo codigo = no.getCodigo();
        
        ExecResult er = executor.exec( exp.getMatrizExp() );
        if ( er.isErroOuEx() )
            return er;
                        
        if ( er.getVar() == null ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, exp.getMatrizExp().getI(), ErroMSGs.VALOR_NAO_NULO_ESPERADO );
            return new ExecResult( erro );
        }
        
        if ( er.getVar().getTipoCompativel() != Var.TC_NUMERICO ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, exp.getMatrizExp().getI(), ErroMSGs.VALOR_NUMERICO_ESPERADO );
            return new ExecResult( erro );
        }
        
        if ( exp.isTransposta() ) {
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
