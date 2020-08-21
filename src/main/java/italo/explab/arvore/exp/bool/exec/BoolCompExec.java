package italo.explab.arvore.exp.bool.exec;

import italo.explab.ErroMSGs;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.bool.node.BoolComp;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.recursos.classe.Objeto;
import italo.explab.var.BooleanVar;
import italo.explab.var.ObjetoVar;
import italo.explab.var.StringVar;
import italo.explab.var.Var;
import italo.explab.var.num.RealVar;

public class BoolCompExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        BoolComp bc = (BoolComp)no;
                
        Codigo codigo = no.getCodigo();
        
        ExecResult r1 = executor.exec( bc.getExp1() );
        ExecResult r2 = executor.exec( bc.getExp2() );
        
        if ( r1.isErroOuEx() )
            return r1;        
        if ( r2.isErroOuEx() )
            return r2;
        
        Var exp1 = r1.getVar();
        Var exp2 = r2.getVar();
        
        if ( exp1 == null ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, bc.getExp1().getI(), ErroMSGs.VALOR_NAO_NULO_ESPERADO );
            return new ExecResult( erro );
        }
        
        if ( exp2 == null ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, bc.getExp2().getI(), ErroMSGs.VALOR_NAO_NULO_ESPERADO );
            return new ExecResult( erro );
        }
        
        if ( bc.isNotExp1() ) {
            if ( exp1.getTipo() != Var.BOOLEAN ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, bc.getExp1().getI(), ErroMSGs.VALOR_BOOLEANO_ESPERADO );
                return new ExecResult( erro );
            }
            exp1 = new BooleanVar( !((BooleanVar)exp1).getValor() );
        }
        
        if ( bc.isNotExp2() ) {
            if ( exp2.getTipo() != Var.BOOLEAN ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, bc.getExp2().getI(), ErroMSGs.VALOR_BOOLEANO_ESPERADO );
                return new ExecResult( erro );
            }
            exp2 = new BooleanVar( !((BooleanVar)exp2).getValor() );
        }
        
        boolean valor = true;
                        
        switch( bc.getOperador() ) {
            case BoolComp.IGUAL:
                valor = exp1.iguais( exp2 );
                break;
            case BoolComp.DIFERENTE:
                valor = !exp1.iguais( exp2 );
                break;
            case BoolComp.MAIOR:                
            case BoolComp.MAIOR_OU_IGUAL:
            case BoolComp.MENOR:
            case BoolComp.MENOR_OU_IGUAL:
                if ( exp1.getTipo() != Var.REAL ) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, bc.getExp1().getI(), ErroMSGs.VALOR_REAL_ESPERADO );
                    return new ExecResult( erro );
                }

                if ( exp2.getTipo() != Var.REAL ) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, bc.getExp2().getI(), ErroMSGs.VALOR_REAL_ESPERADO );
                    return new ExecResult( erro );
                }
                
                double v1 = ((RealVar)exp1).getValor();
                double v2 = ((RealVar)exp2).getValor();
                
                switch( bc.getOperador() ) {            
                    case BoolComp.MAIOR:
                        valor = v1 > v2;
                        break;
                    case BoolComp.MAIOR_OU_IGUAL:
                        valor = v1 >= v2;
                        break;
                    case BoolComp.MENOR:
                        valor = v1 < v2;
                        break;
                    case BoolComp.MENOR_OU_IGUAL:                        
                        valor = v1 <= v2;
                        break;
                }
                break;
            case BoolComp.INSTANCIADE:
                if ( exp1.getTipo() != Var.OBJETO_REF ) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, bc.getExp1().getI(), ErroMSGs.VAR_TIPO_OBJETO_ESPERADO );
                    return new ExecResult( erro );
                }
                
                if ( exp2.getTipo() != Var.STRING ) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, bc.getExp2().getI(), ErroMSGs.VALOR_STRING_ESPERADO );
                    return new ExecResult( erro );
                }
                
                Objeto obj = ((ObjetoVar)exp1).getObjeto();
                String classeNome = ((StringVar)exp2).getValor();
                
                valor = obj.instanciaDe( classeNome );                
                break;
            default:
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, bc.getI(), ErroMSGs.OPERADOR_COMP_ESPERADO );
                return new ExecResult( erro );                
        }
        
        if ( bc.isNot() )
            valor = !valor;
                
        return new ExecResult( new BooleanVar( valor ) );
    }
    
}
