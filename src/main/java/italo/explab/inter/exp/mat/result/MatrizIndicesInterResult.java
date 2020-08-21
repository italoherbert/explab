package italo.explab.inter.exp.mat.result;

import italo.explab.msg.Erro;
import italo.explab.AnaliseOuInterResult;
import italo.explab.arvore.exp.matriz.indice.ExpMatrizIndice;
import italo.explab.inter.InterResult;

public class MatrizIndicesInterResult extends InterResult {        
    
    private ExpMatrizIndice expParamIndiceI;
    private ExpMatrizIndice expParamIndiceJ;
    private int indiceIPos;
    private int indiceJPos;
    private int nparams;
    
    public MatrizIndicesInterResult( 
            ExpMatrizIndice expParamIndiceI, ExpMatrizIndice expParamIndiceJ, int nparams, 
            int indiceIPos, int indiceJPos, int j ) {       
        super( j );
        this.expParamIndiceI = expParamIndiceI;
        this.expParamIndiceJ = expParamIndiceJ;        
        this.nparams = nparams;
        this.indiceIPos = indiceIPos;
        this.indiceJPos = indiceJPos;
    }

    public MatrizIndicesInterResult() {}
    
    public MatrizIndicesInterResult( Erro erro ) {
        super( erro );
    }

    public MatrizIndicesInterResult( AnaliseOuInterResult result ) {
        super( result );
    }
    
    public ExpMatrizIndice[] paramsIDIJ() {
        if ( nparams == 1 ) {
            return new ExpMatrizIndice[] { expParamIndiceI };
        } else if ( nparams == 2 ) {
            return new ExpMatrizIndice[] { expParamIndiceI, expParamIndiceJ };
        }
        return new ExpMatrizIndice[] {};
    }

    public ExpMatrizIndice getExpParamIndiceI() {
        return expParamIndiceI;
    }

    public ExpMatrizIndice getExpParamIndiceJ() {
        return expParamIndiceJ;
    }
    
    public int getQuantParams() {
        return nparams;
    }

    public int getIndiceIPos() {
        return indiceIPos;
    }

    public int getIndiceJPos() {
        return indiceJPos;
    }
        
}
