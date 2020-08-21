package italo.explab.func;

import italo.explab.MetodoParam;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.func.FuncExp;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.codigo.Codigo;
import italo.explab.func.ext.FExecFunc;
import italo.explab.func.numerica.DivNFunc;
import italo.explab.func.numerica.ModNFunc;
import italo.explab.func.numerica.MultNFunc;
import italo.explab.func.numerica.NaoEscalarMultNFunc;
import italo.explab.func.numerica.PotNFunc;
import italo.explab.func.numerica.SomaNFunc;
import italo.explab.func.numerica.SubNFunc;
import italo.explab.func.ext.mat.TranspostaFunc;

public class FuncManager {
    
    private final SomaNFunc somaFunc = new SomaNFunc();
    private final SubNFunc subFunc = new SubNFunc();
    private final MultNFunc multFunc = new MultNFunc();
    private final DivNFunc divFunc = new DivNFunc();
    private final ModNFunc modFunc = new ModNFunc();
    private final PotNFunc potFunc = new PotNFunc();
        
    private final NaoEscalarMultNFunc naoEscalarMultFunc = new NaoEscalarMultNFunc();
    
    private final TranspostaFunc transpostaFunc = new TranspostaFunc();
    
    private final FExecFunc fexecFunc = new FExecFunc();
    
    public FuncResult exec( Func func, FuncExp fno, Executor executor, Codigo codigo, MetodoParam[] params ) {
        return func.exec( fno, executor, codigo, params );
    }
    
    public Instrucao[] carregaInstrucoes( ExecNo no, UsuarioFunc func ) {
        Instrucao[] insts = func.getInstrucoes();
        Instrucao[] instrucoes = new Instrucao[ insts.length ];
        for( int k = 0; k < instrucoes.length; k++ ) {
            instrucoes[ k ] = insts[ k ].novo( no );                                
            instrucoes[ k ].setBaseParamsParente( no ); 
        }
        
        return instrucoes;
    }
    
    public SomaNFunc getSomaFunc() {
        return somaFunc;
    }

    public SubNFunc getSubFunc() {
        return subFunc;
    }

    public MultNFunc getMultFunc() {
        return multFunc;
    }

    public DivNFunc getDivFunc() {
        return divFunc;
    }

    public ModNFunc getModFunc() {
        return modFunc;
    }

    public PotNFunc getPotFunc() {
        return potFunc;
    }

    public NaoEscalarMultNFunc getNaoEscalarMultFunc() {
        return naoEscalarMultFunc;
    }

    public TranspostaFunc getTranspostaFunc() {
        return transpostaFunc;
    }

    public FExecFunc getFExecFunc() {
        return fexecFunc;
    }
    
}
