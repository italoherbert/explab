package italo.explab.arvore.exp.num.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.matriz.MatrizExp;
import italo.explab.arvore.instrucao.Instrucao;

public class NumMat extends NumericaExp {
        
    private MatrizExp matrizExp;

    @Override
    public Instrucao novo( ExecNo parent ) {
        NumMat numMatExp = new NumMat();
        super.carrega( numMatExp, parent );
        
        numMatExp.setMatrizExp( (MatrizExp)matrizExp.novo( numMatExp ) );
        
        return numMatExp;
    }
    
    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        matrizExp.setBaseParamsParente( parent ); 
    }

    public MatrizExp getMatrizExp() {
        return matrizExp;
    }

    public void setMatrizExp(MatrizExp matrizExp) {
        this.matrizExp = matrizExp;
    }
    
    
}
