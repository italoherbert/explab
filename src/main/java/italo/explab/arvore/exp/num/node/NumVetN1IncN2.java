package italo.explab.arvore.exp.num.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.matriz.vetN1IncN2.VetN1IncN2;
import italo.explab.arvore.instrucao.Instrucao;

public class NumVetN1IncN2 extends NumericaExp {

    private VetN1IncN2 vetN1IncN2;
    
    @Override
    public Instrucao novo(ExecNo parent) {
        NumVetN1IncN2 exp = new NumVetN1IncN2();
        super.carrega( exp, parent );
        
        exp.setVetN1IncN2( (VetN1IncN2)vetN1IncN2.novo( exp ) );
        
        return exp;
    }

    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        vetN1IncN2.setBaseParamsParente( parent );
    }    

    public VetN1IncN2 getVetN1IncN2() {
        return vetN1IncN2;
    }

    public void setVetN1IncN2(VetN1IncN2 vetN1IncN2) {
        this.vetN1IncN2 = vetN1IncN2;
    }
    
}
