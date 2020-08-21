package italo.explab.func;

import italo.explab.MetodoParam;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.func.FuncExp;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.codigo.Codigo;
import italo.explab.var.Var;
import italo.explab.recursos.RecursoManager;
import italo.explab.msg.FuncOuConstrutor;
import italo.explab.msg.LanceErro;
import italo.explab.recursos.classe.Classe;
import italo.explab.util.TenteCaptureUtil;

public class UsuarioFunc extends AbstractFunc implements FuncOuConstrutor {
        
    private final String nome;
    private final int quantParams;
    
    private final String[] parametros;
    private final String[] exceptionClasses;

    private Classe classe;
    
    private final Instrucao[] instrucoes;
        
    public UsuarioFunc( String nome, String[] params, String[] exceptionClasses, Instrucao[] instrucoes ) {
        this.nome = nome;
        this.parametros = params;        
        this.exceptionClasses = exceptionClasses;        
        this.instrucoes = instrucoes;
        quantParams = params.length;        
    }    

    @Override
    public Func nova() {
        UsuarioFunc uf = new UsuarioFunc( nome, parametros, exceptionClasses, instrucoes );
        uf.setClasse( classe );
        return uf;
    }
    
    @Override
    public FuncResult exec( FuncExp fno, Executor executor, Codigo codigo, MetodoParam[] params ) {                            
        TenteCaptureUtil tenteCapUtil = executor.getAplic().getTenteCaptureUtil();
                                                
        RecursoManager recursos = fno.getBloco().getRecursos();
        
        for( int k = 0; k < params.length; k++ ) {            
            String n = parametros[ k ];
            Var var = params[ k ].getVar();
                           
            recursos.getVarLista().addVar( n, var ); 
        }                   
                
        ExecResult result = executor.exec( fno.getBloco() ); 
        if ( result.getErro() != null )
            return new FuncResult( result.getErro() );                                                                        
        
        if ( result.getExObj() != null ) {
            LanceErro exerro = tenteCapUtil.excecaoCapturadaOuTratada( result.getExObj(), this, codigo, fno );            
            if ( exerro != null )
                return new FuncResult( exerro );                        
            
            return new FuncResult( result.getExObj() );
        }
                    
        return new FuncResult( result.getRetornada(), this );
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }
        
    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public int getQuantParametros() {
        return quantParams;
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
