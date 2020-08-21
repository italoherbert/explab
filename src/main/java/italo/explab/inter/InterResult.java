package italo.explab.inter;

import italo.explab.AnaliseOuInterResult;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.msg.Erro;

public class InterResult implements AnaliseOuInterResult {
    
    public final static int RESULT_SEM_J = Integer.MAX_VALUE;
    
    protected int j = 0;
    protected Erro erro = null;
    
    protected Instrucao instrucao = null;

    public InterResult() {}
    
    public InterResult( int j ) {
        this.j = j;
    }
    
    public InterResult( Instrucao instrucao, int j ) {
        this.instrucao = instrucao;
        this.j = j;
    }
    
    public InterResult( Erro erro ) {
        this.erro = erro;
    }
    
    public InterResult( AnaliseOuInterResult result ) {
        this.j = result.getJ();
        this.erro = result.getErro();
    }

    public int getJ() {
        return j;
    }

    public Erro getErro() {
        return erro;
    }

    public Instrucao getInstrucaoOuExp() {
        return instrucao;
    }
    
    
}
