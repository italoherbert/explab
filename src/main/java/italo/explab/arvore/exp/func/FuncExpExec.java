package italo.explab.arvore.exp.func;

import italo.explab.ErroMSGs;
import italo.explab.MetodoParam;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.Executor;
import italo.explab.arvore.busca.func.FuncBusca;
import italo.explab.arvore.busca.func.FuncBuscaResult;
import italo.explab.arvore.exp.ExpRecurso;
import italo.explab.arvore.exp.VarOuFuncExecResult;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.codigo.Codigo;
import italo.explab.func.Func;
import italo.explab.func.FuncResult;
import italo.explab.func.UsuarioFunc;
import italo.explab.msg.CodigoErro;
import italo.explab.var.Var;

public class FuncExpExec implements Exec {

    @Override
    public VarOuFuncExecResult exec(ExecNo no, Executor executor) {
        FuncExp fexp = (FuncExp)no;
        String nome = fexp.getNome();
        MetodoParam[] params = fexp.getParams();
        int acesso = fexp.getAcesso();
        
        Codigo codigo = no.getCodigo();
        
        FuncBusca fb;
        if ( fexp.getRuntimeFuncBusca() != null ) {
            fb = fexp.getRuntimeFuncBusca();
        } else {
            switch ( acesso ) {
                case ExpRecurso.ESTE:
                    fb = executor.getBuscaManager().getEsteFuncBusca();
                    break;
                case ExpRecurso.SUPER:
                    fb = executor.getBuscaManager().getSuperFuncBusca();
                    break;
                default:
                    fb = executor.getBuscaManager().getEscopoFuncBusca();                    
                    break;
            }
        }
                
        FuncBuscaResult fbresult = fb.busca( fexp, executor, nome, params.length );
        
        if ( fbresult == null ) {
            CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.FUNC_NAO_ENCONTRADA, nome );
            return new VarOuFuncExecResult( erro );
        }

        Func f = fbresult.getFunc();                
        if ( f instanceof UsuarioFunc ) {
            Instrucao[] instrucoes = executor.getAplic().getFuncManager().carregaInstrucoes( fexp, (UsuarioFunc)f );                                
            fexp.getBloco().setInstrucoes( instrucoes ); 
        }
        
        fexp.setRuntimeObjeto( fbresult.getRuntimeObjeto() ); 

        FuncResult result = f.exec( fexp, executor, codigo, params );        
        if ( result.getErro() != null ) 
            return new VarOuFuncExecResult( result.getErro() );                                    
        
        if ( result.getExObj() != null )
            return new VarOuFuncExecResult( result.getExObj() );                                                        
                        
        Var retornada = result.getRetornada();

        return new VarOuFuncExecResult( retornada, true );
    }
    
}
