package italo.explab.arvore.exp.num.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.incdec.IncDec;
import italo.explab.arvore.instrucao.Instrucao;

public class NumIncDec extends NumericaExp {

    private IncDec incdec;
    
    @Override
    public Instrucao novo( ExecNo parent ) {
        NumIncDec numIncDec = new NumIncDec();
        super.carrega(numIncDec, parent );
        
        numIncDec.setIncDec((IncDec)incdec.novo( numIncDec ) ); 
        
        return numIncDec;
    }

    @Override
    public void setBaseParamsParente(ExecNo parent) {
        incdec.setBaseParamsParente( parent ); 
    }

    public IncDec getIncDec() {
        return incdec;
    }

    public void setIncDec(IncDec incdec) {
        this.incdec = incdec;
    }
    
}
