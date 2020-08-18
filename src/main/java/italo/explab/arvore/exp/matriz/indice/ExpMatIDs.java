package italo.explab.arvore.exp.matriz.indice;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecUtil;

public class ExpMatIDs {

    private ExpMatrizIndice[] indices;
    private ExpMatIDs prox = null;

    public ExpMatIDs(ExpMatrizIndice[] indices) {
        this.indices = indices;
    }

    public ExpMatIDs( ExpMatrizIndice[] indices, ExpMatIDs prox ) {
        this.indices = indices;
        this.prox = prox;
    }

    public ExpMatIDs novo( ExecNo parent ) {
        ExpMatrizIndice[] vet = new ExpMatrizIndice[ indices.length ];
        for( int k = 0; k < vet.length; k++ )
            vet[ k ] = indices[ k ].novo( parent );
        
        ExpMatIDs ids = new ExpMatIDs( vet );
        
        if ( prox != null )
            ids.setProx( prox.novo( parent ) );
        
        return ids;
    }
    
    public void setBaseParamsParente( ExecNo parent ) {
        for( int k = 0; k < indices.length; k++ )
            if( indices[ k ].getTipo() == ExpMatrizIndice.NORMAL )                     
                indices[ k ].getExp().setBaseParamsParente( parent ); 
    }
    
    public ExpMatrizIndice[] getIndices() {
        return indices;
    }

    public void setIndices(ExpMatrizIndice[] indices) {
        this.indices = indices;
    }

    public ExpMatIDs getProx() {
        return prox;
    }

    public void setProx(ExpMatIDs prox) {
        this.prox = prox;
    }
    
}
