package italo.explab.arvore.exp.matriz.vetN1IncN2;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.instrucao.Instrucao;

public class VetN1IncN2 extends Exp {
    
    private Exp n1;
    private Exp inc;
    private Exp n2;

    @Override
    public Instrucao novo( ExecNo parent ) {
        VetN1IncN2 vet = new VetN1IncN2();
        super.carrega( vet, parent );
        
        vet.setN1( (Exp)n1.novo( vet ) );
        vet.setN2( (Exp)n2.novo( vet ) );
        vet.setInc( (Exp)inc.novo( vet ) ); 
        
        return vet;
    }
    
    @Override
    public void setBaseParamsParente( ExecNo parent ) {       
        n1.setBaseParamsParente( parent );
        n2.setBaseParamsParente( parent );
        inc.setBaseParamsParente( parent ); 
    }

    public Exp getN1() {
        return n1;
    }

    public void setN1(Exp n1) {
        this.n1 = n1;
    }

    public Exp getInc() {
        return inc;
    }

    public void setInc(Exp inc) {
        this.inc = inc;
    }

    public Exp getN2() {
        return n2;
    }

    public void setN2(Exp n2) {
        this.n2 = n2;
    }
    
}
