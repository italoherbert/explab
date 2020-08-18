package italo.explab.arvore.estruturas.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.instrucao.Instrucao;

public class Enquanto extends Loop {

    @Override
    public Instrucao novo( ExecNo parent ) {
        Enquanto enquanto = new Enquanto();
        super.carrega( enquanto, parent );
        
        return enquanto;
    }
                
}
