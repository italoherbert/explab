package italo.explab.construtor;

import italo.explab.MetodoParam;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.classe.ClasseConstrutor;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.codigo.Codigo;
import italo.explab.msg.FuncOuConstrutor;
import italo.explab.msg.LanceErro;
import italo.explab.recursos.RecursoManager;
import italo.explab.util.TenteCaptureUtil;
import italo.explab.var.Var;

public class Construtor implements FuncOuConstrutor {
        
    private final String[] parametros;    
    private final String[] exceptionClasses;
    private final Instrucao[] instrucoes;
    
    public Construtor( String[] parametros, String[] exceptionClasses, Instrucao[] instrucoes ) {
        this.parametros = parametros;
        this.exceptionClasses = exceptionClasses;
        this.instrucoes = instrucoes;
    }
        
    public Construtor novo() {
        return new Construtor( parametros, exceptionClasses, instrucoes );                
    }
    
    public ConstrutorResult exec( ClasseConstrutor cno, Executor executor, Codigo codigo, MetodoParam[] params ) {                                           
        TenteCaptureUtil tenteCapUtil = executor.getAplic().getTenteCaptureUtil();
                                                
        RecursoManager recursos = cno.getBloco().getRecursos();                        
        
        for( int k = 0; k < params.length; k++ ) {            
            String n = parametros[ k ];
            Var var = params[ k ].getVar();
        
            recursos.getVarLista().addVar( n, var );            
        }                        
                        
        ExecResult result = executor.exec( cno.getBloco() );
        if ( result.getErro() != null )
            return new ConstrutorResult( result.getErro() );        
        
        if ( result.getExObj() != null ) {
            LanceErro exerro = tenteCapUtil.excecaoCapturadaOuTratada( result.getExObj(), this, codigo, cno );            
            if ( exerro != null )
                return new ConstrutorResult( exerro );            
            
            return new ConstrutorResult( result.getExObj() );
        }
                
        return new ConstrutorResult( this );
    }

    @Override
    public String getNome() {
        return PalavrasReservadas.CONSTRUTOR;
    }

    @Override
    public int getQuantParametros() {
        return parametros.length;
    }
            
    @Override
    public String[] getExceptionClasses() {
        return exceptionClasses;
    }

    public String[] getParametros() {
        return parametros;
    }

    public Instrucao[] getInstrucoes() {
        return instrucoes;
    }

}
