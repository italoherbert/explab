package italo.explab.arvore.cmd.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.instrucao.Instrucao;

public class Ajuda extends Instrucao {
    
    private Exp[] termos;

    @Override
    public Instrucao novo( ExecNo parent ) {
        Ajuda ajuda = new Ajuda();
        super.carrega( ajuda, parent );
        
        Exp[] lista = new Exp[ termos.length ];
        for( int k = 0; k < termos.length; k++ )
            lista[k] = (Exp)termos[k].novo( ajuda );
        
        ajuda.setTermos( lista ); 
        
        return ajuda;
    }

    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        for( Exp termo : termos )
           termo.setBaseParamsParente( parent );
    }
    
    public Exp[] getTermos() {
        return termos;
    }

    public void setTermos(Exp[] termos) {
        this.termos = termos;
    }
    
}
