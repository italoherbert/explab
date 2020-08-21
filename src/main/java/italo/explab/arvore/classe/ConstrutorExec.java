package italo.explab.arvore.classe;

import italo.explab.ErroMSGs;
import italo.explab.MetodoParam;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.busca.construtor.ConstrutorBuscaResult;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.VarOuFuncExecResult;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.codigo.Codigo;
import italo.explab.construtor.Construtor;
import italo.explab.construtor.ConstrutorResult;
import italo.explab.msg.CodigoErro;

public class ConstrutorExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        ClasseConstrutor cc = (ClasseConstrutor)no;
        Exp[] cparams = cc.getParams();
        
        Codigo codigo = no.getCodigo();
                
        MetodoParam[] mparams = new MetodoParam[ cparams.length ];        
        for( int i = 0; i < cparams.length; i++ ) {
            ExecResult er = executor.exec( cparams[ i ] );
            if ( er.getErro() != null )
                return new VarOuFuncExecResult( er.getErro() );
            
            mparams[ i ] = new MetodoParam( er.getVar(), cparams[ i ].getI() );
        }
                        
        ConstrutorBuscaResult cbresult = cc.getConstrutorBusca().busca( cc, executor, cparams.length );
        
        if ( cbresult == null ) {
            if ( cparams.length != 0 ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, cc.getI(), ErroMSGs.CONSTRUTOR_NAO_ENCONTRADO, String.valueOf( cparams.length ) );
                return new ExecResult( erro );
            }
        } else {            
            Construtor c = cbresult.getConstrutor();
            
            Instrucao[] insts = c.getInstrucoes();
            Instrucao[] instrucoes = new Instrucao[ insts.length ];
            for( int k = 0; k < instrucoes.length; k++ ) {
                instrucoes[ k ] = insts[ k ].novo( cc );
                instrucoes[ k ].setBaseParamsParente( cc ); 
            }

            cc.getBloco().setInstrucoes( instrucoes ); 
            
            cc.setRuntimeObjeto( cbresult.getRuntimeObjeto() ); 

            ConstrutorResult result = c.exec( cc, executor, codigo, mparams );
            if ( result.getErro() != null )
                return new ExecResult( result.getErro() );                                 
            
            if ( result.getExObj() != null )
                return new VarOuFuncExecResult( result.getExObj() );            
        }
        return new ExecResult();
    }
    
}
