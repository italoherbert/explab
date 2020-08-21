package italo.explab.arvore.exp.variavel;

import italo.explab.ErroMSGs;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.ExpRecurso;
import italo.explab.arvore.exp.VarOuFuncExecResult;
import italo.explab.arvore.busca.var.VarBusca;
import italo.explab.arvore.busca.var.VarBuscaResult;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.OOChamada;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.recursos.RecursoManager;
import italo.explab.recursos.classe.Objeto;
import italo.explab.var.ObjetoVar;
import italo.explab.var.Var;
import italo.explab.var_alterador.SimplesAlterador;
import italo.explab.var_alterador.VarAlterador;

public class VariavelExpExec implements Exec {
    
    @Override
    public VarOuFuncExecResult exec( ExecNo no, Executor executor ) {
        VariavelExp expVar = (VariavelExp)no;        
        
        Codigo codigo = no.getCodigo();
        
        String varnome = expVar.getNome();
        int acesso = expVar.getAcesso();
        OOChamada ooChamador = expVar.getOOChamador();
                
        VarBusca vb;
        if ( expVar.getRuntimeVarBusca() != null ) {
            vb = expVar.getRuntimeVarBusca();
        } else {
            switch ( acesso ) {
                case ExpRecurso.ESTE:
                    vb = executor.getBuscaManager().getEsteVarBusca();                    
                    break;
                case ExpRecurso.SUPER:
                    vb = executor.getBuscaManager().getSuperVarBusca();
                    break;
                default:
                    vb = executor.getBuscaManager().getEscopoVarBusca();                    
                    break;
            }
        }
        
        if ( acesso == ExpRecurso.ESTE && varnome == null ) {
            if ( no.getParente() == null ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.ERRO_OBJ_NO_ESPERADO );
                return new VarOuFuncExecResult( erro );
            }
            
            Objeto obj = no.getParente().objeto( executor );
            if ( obj == null ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.ERRO_OBJ_NO_ESPERADO );
                return new VarOuFuncExecResult( erro );
            }
                            
            return new VarOuFuncExecResult( obj.getObjVar(), false );        
        }
                        
        VarBuscaResult vbresult = vb.busca( expVar, executor, varnome );                                                   
        if ( vbresult == null ) { 
            if ( ooChamador == null && expVar.isParenteAtrib() ) {         
                RecursoManager recursos = vb.recursos( expVar, executor ); 
                                
                if ( recursos != null ) {                     
                    VarAlterador alterador = new SimplesAlterador( varnome, recursos );
                    return new VarOuFuncExecResult( null, varnome, alterador );                                                                    
                }
                                
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.VAR_INVALIDA );
                return new VarOuFuncExecResult( erro );
            } else {                
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.VAR_INVALIDA );
                return new VarOuFuncExecResult( erro );
            }
        } else {
            expVar.setRuntimeObjeto( vbresult.getRuntimeObjeto() );
            
            Var var = vbresult.getVariavel().getVar();
            if ( ooChamador == null ) {
                RecursoManager recursos = vbresult.getRecursos();                                                                
                                  
                VarAlterador alterador = new SimplesAlterador( varnome, recursos );       
                return new VarOuFuncExecResult( var, varnome, alterador );                
            } else {                
                if ( var == null ) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.VALOR_NAO_NULO_ESPERADO );
                    return new VarOuFuncExecResult( erro );
                }
                
                if ( var.getTipo() != Var.OBJETO_REF ) {
                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.VALOR_OBJETO_ESPERADO );
                    return new VarOuFuncExecResult( erro );
                }
                
                if ( ((ObjetoVar)var).getObjeto() != null ) {                                                                        
                    ExpRecurso chamada = ooChamador.getChamada();
                    chamada.runtimeConfigObjBusca( ((ObjetoVar)var).getObjeto(), executor.getBuscaManager() );

                    ExecResult er = executor.exec( (Exp)chamada );
                    if ( er.isErroOuEx() )
                        return new VarOuFuncExecResult( er );                                    

                    if ( er instanceof VarOuFuncExecResult )
                        return (VarOuFuncExecResult)er;
                                        
                    return new VarOuFuncExecResult( er.getVar(), false );                                                                    
                }
                
                return new VarOuFuncExecResult( var, false );                                                                    
            }
        }
    }
    
}
