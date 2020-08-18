package italo.explab.arvore.exp.funcoumat;

import italo.explab.ErroMSGs;
import italo.explab.MetodoParam;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.VarOuFuncExecResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.arvore.busca.var.VarBusca;
import italo.explab.arvore.busca.var.VarBuscaResult;
import italo.explab.arvore.exp.ExpRecurso;
import italo.explab.arvore.exp.OOChamada;
import italo.explab.arvore.exp.func.FuncExp;
import italo.explab.arvore.exp.matriz.MatrizExecResult;
import italo.explab.arvore.exp.matriz.MatrizExp;
import italo.explab.arvore.exp.matriz.indice.ExpMatIDs;
import italo.explab.arvore.exp.matriz.indice.ExpMatrizIndice;
import italo.explab.var.Var;
import italo.explab.var.ObjetoVar;
import italo.explab.var.mat.MatrizVar;
import italo.explab.var_alterador.VarAlterador;

public class ChamadaFuncOuMatELExec implements Exec {
    
    @Override
    public VarOuFuncExecResult exec( ExecNo no, Executor executor ) {        
        FuncOuMatELExp fcexp = (FuncOuMatELExp)no;

        String fnome = fcexp.getNome();
        Exp[] fparams = fcexp.getParams();
        int acesso = fcexp.getAcesso();
        OOChamada ooChamador = fcexp.getOOChamador();
                
        Codigo codigo = no.getCodigo();
                                
        VarBusca vb;
        if ( fcexp.getRuntimeFuncBusca() != null ) {
            vb = fcexp.getRuntimeVarBusca();
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
        
        MetodoParam[] mparams = new MetodoParam[ fparams.length ];        
        for( int i = 0; i < fparams.length; i++ ) {
            Exp param = (Exp)fparams[ i ];
            ExecResult er = executor.exec( param );
            if ( er.isErroOuEx() )
                return new VarOuFuncExecResult( er );
            
            mparams[ i ] = new MetodoParam( er.getVar(), param.getI() );            
        }        
                        
        Var retornada = null;                
        VarAlterador alterador = null;
        boolean ehmat = false;
        
        VarBuscaResult vbresult = vb.busca( fcexp, executor, fnome );
        if ( vbresult != null ) {
            Var var = vbresult.getVariavel().getVar();
            if ( var.getTipo() == Var.MATRIZ ) {
                ExpMatrizIndice[] indicesExps = new ExpMatrizIndice[ mparams.length ];
                for( int i = 0; i < indicesExps.length; i++ )                   
                    indicesExps[ i ] = new ExpMatrizIndice( fparams[ i ], fparams[ i ].getI(), ExpMatrizIndice.NORMAL );                
                
                ExpMatIDs ids = new ExpMatIDs( indicesExps );
                
                MatrizExp matexp = executor.getExecManager().getExecNoFactory().getExpFactory().novoMatrizExp( no.getI(), fcexp, codigo );
                matexp.setNome( fnome );
                matexp.setExpMatIDs( ids );
                matexp.setBaseParamsParente( fcexp ); 
                matexp.setTransposta( fcexp.isTransposta() ); 
                matexp.setRuntimeObjeto( vbresult.getRuntimeObjeto() );
                                
                MatrizExecResult er = (MatrizExecResult)executor.exec( matexp );                
                if ( er.isErroOuEx() )
                    return new VarOuFuncExecResult( er );                                                                 
                
                retornada = er.getVar();
                alterador = er.getAlterador();
                ehmat = true;
            }
        }
                                
        if ( !ehmat ) {
            FuncExp fexp = executor.getExecManager().getExecNoFactory().getExpFactory().novoFuncExp( no.getI(), no, codigo );
            fexp.setNome( fnome );
            fexp.setParams( mparams ); 
            fexp.setAcesso( acesso );            
            fexp.setTransposta( fcexp.isTransposta() );                               
                    
            ExecResult er = executor.exec( fexp );
            if ( er.isErroOuEx() )
                return new VarOuFuncExecResult( er );                                                
            
            retornada = er.getVar();
        }
        
        if ( retornada != null && fcexp.getExpMatIDs() != null ) {
            if ( retornada.getTipo() != Var.MATRIZ ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.VAR_TIPO_MATRIZ_ESPERADO );
                return new VarOuFuncExecResult( erro );
            }
            
            MatrizVar matvar = (MatrizVar)retornada;
            ExpMatIDs prox = fcexp.getExpMatIDs();
            MatrizExecResult er = executor.getExecManager().getExpExecManager().getMatrizExpExec().matriz( fcexp, prox, executor, matvar );
            if ( er.isErroOuEx() )
                return new VarOuFuncExecResult( er );
            
            retornada = er.getVar();            
        }
                
        if ( ooChamador != null ) {            
            if ( retornada == null ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.ERRO_RETORNO_NAO_NULO_ESPERADO );
                return new VarOuFuncExecResult( erro );
            }
            
            if ( retornada.getTipo() != Var.OBJETO_REF ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.ERRO_RETORNO_TIPO_OBJETO_ESPERADO );
                return new VarOuFuncExecResult( erro );
            }                                   
                        
            ExpRecurso chamada = ooChamador.getChamada();            
            chamada.runtimeConfigObjBusca( ((ObjetoVar)retornada).getObjeto(), executor.getBuscaManager() );
                        
            ExecResult er = executor.exec( (Exp)chamada );            
            if ( er.isErroOuEx() )
                return new VarOuFuncExecResult( er );            

            return (VarOuFuncExecResult)er;
        }
        
        if ( ehmat ) 
            return new VarOuFuncExecResult( retornada, fnome, alterador );
        
        return new VarOuFuncExecResult( retornada, true );
    }
    
}
