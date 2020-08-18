package italo.explab.arvore.exp.incdec;

import italo.explab.ErroMSGs;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.VarOuFuncExecResult;
import italo.explab.arvore.exp.num.exec.NumVariavelExec;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.var.Var;
import italo.explab.var.num.RealVar;
import italo.explab.var_alterador.VarAlteradorException;

public class IncDecExec extends NumVariavelExec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {                
        IncDec incdec = (IncDec)no;
        
        Codigo codigo = no.getCodigo();
                
        VarOuFuncExecResult er = (VarOuFuncExecResult)executor.exec( incdec.getVariavelExp() );
        if ( er.isErroOuEx() )
            return er;
                
        if ( er.getVar().getTipo() != Var.REAL ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.VALOR_REAL_ESPERADO );
            return new ExecResult( erro );
        }
        
        RealVar n = (RealVar)er.getVar().nova();        
        if ( incdec.isAnt() == incdec.isExecSeAntIgual() ) {
            n.setValor( incdec.isInc() ? n.getValor() + 1 : n.getValor() - 1 );                         
            try { 
                er.getAlterador().alteraVar( n.nova() );
            } catch ( VarAlteradorException ex ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.VAR_TENTATIVA_DE_ALTERAR_CONSTANTE, er.getVarNome() );
                return new ExecResult( erro );
            }            
        }
        
        return new ExecResult( n );
    }
    
}
