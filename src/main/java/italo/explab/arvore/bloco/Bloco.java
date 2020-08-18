package italo.explab.arvore.bloco;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.recursos.RecursoManager;

public class Bloco extends Instrucao implements BlocoRecursos {
    
    private Instrucao[] instrucoes; 
    private RecursoManager recursos;
    
    private BlocoRecursos brecs;
    
    @Override
    public Instrucao novo( ExecNo parent ) {
        Bloco bloco = new Bloco();
        super.carrega( bloco, parent );
                        
        if ( instrucoes != null ) {
            Instrucao[] lista = new Instrucao[ instrucoes.length ];
            for( int k = 0; k < lista.length; k++ )
                lista[k] = instrucoes[k].novo( parent );            
            
            bloco.setInstrucoes( lista );
        }
        
        bloco.setRecursos( recursos.novo() );
        bloco.setBlocoRecursos( brecs ); 
                
        return bloco;
    }

    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        if ( instrucoes != null )
            for( Instrucao inst : instrucoes )
                inst.setBaseParamsParente( parent ); 
    }

    public Instrucao[] getInstrucoes() {
        return instrucoes;
    }

    public void setInstrucoes(Instrucao[] instrucoes) {
        this.instrucoes = instrucoes;
    }

    public RecursoManager getRecursos() {
        if ( brecs != null )
            return brecs.getRecursos();
        return recursos;
    }

    public void setRecursos(RecursoManager recursos) {
        this.recursos = recursos;
    }

    public BlocoRecursos getBlocoRecursos() {
        return brecs;
    }

    public void setBlocoRecursos(BlocoRecursos brecs) {
        this.brecs = brecs;
    }
 
}
