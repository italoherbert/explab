package italo.explab.arvore.exp.num.exec;

import italo.explab.ErroMSGs;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.busca.var.VarBusca;
import italo.explab.arvore.busca.var.VarBuscaResult;
import italo.explab.arvore.exp.num.node.NumExp;
import italo.explab.codigo.Codigo;
import italo.explab.func.FuncManager;
import italo.explab.func.numerica.NFuncErro;
import italo.explab.func.numerica.NFuncResult;
import italo.explab.msg.CodigoErro;
import italo.explab.recursos.var.GlobalVarLista;
import italo.explab.var.BooleanVar;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;
import italo.explab.var.num.NumericaVar;

public class NumExpExec implements Exec {
     
    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        NumExp exp = (NumExp)no;

        Codigo codigo = no.getCodigo();
        
        FuncManager fmanager = executor.getAplic().getFuncManager();        
                
        ExecResult r1 = executor.exec( exp.getExp1() );
        if ( r1.isErroOuEx() )
            return r1;
        
        if ( r1.getVar() == null ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, exp.getExp1().getI(), ErroMSGs.VALOR_NAO_NULO_ESPERADO );
            return new ExecResult( erro );
        }
        
        if ( r1.getVar().getTipoCompativel() != Var.TC_NUMERICO ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, exp.getExp1().getI(), ErroMSGs.VALOR_NUMERICO_ESPERADO );
            return new ExecResult( erro );
        }
                                        
        ExecResult r2 = executor.exec( exp.getExp2() );
        if ( r2.isErroOuEx() )
            return r2;                
                        
        if ( r2.getVar() == null ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, exp.getExp2().getI(), ErroMSGs.VALOR_NAO_NULO_ESPERADO );
            return new ExecResult( erro );
        }
          
        if ( r2.getVar().getTipoCompativel() != Var.TC_NUMERICO ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, exp.getExp2().getI(), ErroMSGs.VALOR_NUMERICO_ESPERADO );
            return new ExecResult( erro );
        }
                        
        NFuncResult result = null;
        
        boolean divPorZeroLigado = false;
        
        VarBusca escopoVB = executor.getBuscaManager().getEscopoVarBusca();
        VarBuscaResult vbresult = escopoVB.busca( no, executor, GlobalVarLista.DIV_POR_ZERO );
        
        Var divPorZeroLigadoVar = vbresult.getVariavel().getVar();
        if ( divPorZeroLigadoVar != null )
            if ( divPorZeroLigadoVar.getTipo() == Var.BOOLEAN )
                divPorZeroLigado = ((BooleanVar)divPorZeroLigadoVar).getValor();                    
                
        NumericaVar op1 = (NumericaVar)r1.getVar();
        NumericaVar op2 = (NumericaVar)r2.getVar();
                
        switch( exp.getOperador() ) {            
            case NumExp.SOMA:
                result = fmanager.getSomaFunc().exec( op1, op2 );
                break;
            case NumExp.SUB:
                result = fmanager.getSubFunc().exec( op1, op2 );                
                break;
            case NumExp.MULT:
                result = fmanager.getMultFunc().exec( op1, op2 );                
                break;
            case NumExp.DIV:
                result = fmanager.getDivFunc().exec( op1, op2 );                
                if ( result.isDivisaoPorZero() && divPorZeroLigado ) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.DIV_POR_ZERO );                                     
                    return new ExecResult( erro );
                }
                break;
            case NumExp.MOD:
                result = fmanager.getModFunc().exec( op1, op2 );                
                break;
            case NumExp.POT:
                result = fmanager.getPotFunc().exec( op1, op2 );                
                break;
            case NumExp.NAO_ESC_MULT_MATS:
                result = fmanager.getNaoEscalarMultFunc().exec( op1, op2 );                
                break;
        }    
                        
        if ( result != null ) {
            NFuncErro nfe = result.getErro();
            if ( nfe != null ) {
                CodigoErro erro = new CodigoErro( nfe.getClass(), codigo, no.getI(), nfe.getChave(), nfe.getParams() );                        
                return new ExecResult( erro );
            }     
                        
            if ( exp.isTransposta() ) {
                if ( result.getValor().getTipo() == Var.MATRIZ ) {
                    MatrizVar matvar = (MatrizVar)result.getValor();
                    matvar = executor.getAplic().getFuncManager().getTranspostaFunc().transposta( matvar );

                    return new ExecResult( matvar );
                } else {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.VAR_TIPO_MATRIZ_ESPERADO );
                    return new ExecResult( erro );
                }            
            }            
            
            return new ExecResult( result.getValor() );
        }
                        
        return new ExecResult();
    }
    
}
