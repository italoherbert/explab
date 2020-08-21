package italo.explab.arvore.estruturas.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.instrucao.Instrucao;

public class FacaEnquanto extends Loop {

    @Override
    public Instrucao novo( ExecNo parent ) {
        FacaEnquanto facaEnquanto = new FacaEnquanto();
        super.carrega( facaEnquanto, parent ); 
        
        return facaEnquanto;
    }
        
}
