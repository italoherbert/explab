package italo.explab.arvore.cmd.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.string.node.StringExp;
import italo.explab.arvore.instrucao.Instrucao;

public class CD extends Instrucao {

    private StringExp caminhoExp;

    @Override
    public Instrucao novo( ExecNo parent ) {
        CD cd = new CD();
        super.carrega( cd, parent );
        
        cd.setCaminhoExp( (StringExp)caminhoExp.novo( cd ) );
        
        return cd;
    }        

    @Override
    public void setBaseParamsParente(ExecNo parent) {
        caminhoExp.setBaseParamsParente( parent ); 
    }
    
    public StringExp getCaminhoExp() {
        return caminhoExp;
    }

    public void setCaminhoExp(StringExp caminhoExp) {
        this.caminhoExp = caminhoExp;
    }
    
}
