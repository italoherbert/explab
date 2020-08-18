package italo.explab.arvore.exp.bool.exec;

import italo.explab.ErroMSGs;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.bool.node.BoolExp;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.var.BooleanVar;
import italo.explab.var.Var;

public class BoolExpExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        BoolExp boolExp = (BoolExp)no;
        
        Codigo codigo = no.getCodigo();
        
        ExecResult er = executor.exec( boolExp.getExp1() );
        if ( er.isErroOuEx() )
            return er;
        
        if ( er.getVar() == null ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, boolExp.getExp1().getI(), ErroMSGs.VALOR_NAO_NULO_ESPERADO );
            return new ExecResult( erro );
        }
        
        if ( er.getVar().getTipo() != Var.BOOLEAN ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, boolExp.getExp1().getI(), ErroMSGs.VALOR_BOOLEANO_ESPERADO );
            return new ExecResult( erro );
        }
        
        boolean v1 = ((BooleanVar)er.getVar()).getValor();
        if ( boolExp.isNotExp1() )
            v1 = !v1;
        
        if ( boolExp.getOperador() == BoolExp.VALOR_UNICO )
            return new ExecResult( new BooleanVar( v1 ) );
        
        boolean result = v1;        
        switch( boolExp.getOperador() ) {
            case BoolExp.AND:                
            case BoolExp.OR:
            case BoolExp.XOR:
                ExecResult er2 = executor.exec( boolExp.getExp2() );
                if ( er2.isErroOuEx() )
                    return er2;

                if ( er2.getVar() == null ) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, boolExp.getExp2().getI(), ErroMSGs.VALOR_NAO_NULO_ESPERADO );
                    return new ExecResult( erro );
                }

                if ( er2.getVar().getTipo() != Var.BOOLEAN ) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, boolExp.getExp2().getI(), ErroMSGs.VALOR_BOOLEANO_ESPERADO );
                    return new ExecResult( erro );
                }
                
                boolean v2 = ((BooleanVar)er2.getVar()).getValor();
                switch( boolExp.getOperador() ) {
                    case BoolExp.AND:
                        result = v1 && v2;
                        break;
                    case BoolExp.OR:
                        result = v1 || v2;
                        break;
                    case BoolExp.XOR:
                        result = v1 ^ v2;
                        break;
                }
                break;            
            case BoolExp.VALOR_UNICO:
                result = v1;
                break;
        }
        
        if ( boolExp.isNot() )
            result = !result;
        
        return new ExecResult( new BooleanVar( result ) );
    }
    
}
