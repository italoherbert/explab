package italo.explab.arvore.cmd.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.cmd.node.leia.LeiaListener;
import italo.explab.arvore.exp.bool.node.BoolValor;
import italo.explab.arvore.exp.string.node.StrValor;
import italo.explab.arvore.instrucao.Instrucao;

public class Leia extends Instrucao {

    private StrValor varnomeExp;
    private BoolValor ehTipoStringExp;
    private LeiaListener listener;

    @Override
    public Instrucao novo( ExecNo parent ) {
        Leia leia = new Leia();
        super.carrega( leia, parent );
        
        leia.setVarNomeExp( (StrValor)varnomeExp.novo( leia ) );
        leia.setEhTipoStringExp( (BoolValor)ehTipoStringExp.novo( leia ) );
        leia.setLeiaListener( listener );
        
        return leia;
    }
    
    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        varnomeExp.setBaseParamsParente( parent );
        ehTipoStringExp.setBaseParamsParente( parent );
    }
    
    public StrValor getVarNomeExp() {
        return varnomeExp;
    }

    public void setVarNomeExp(StrValor varnomeExp) {
        this.varnomeExp = varnomeExp;
    }

    public BoolValor getEhTipoStringExp() {
        return ehTipoStringExp;
    }

    public void setEhTipoStringExp(BoolValor ehTipoStringExp) {
        this.ehTipoStringExp = ehTipoStringExp;
    }

    public LeiaListener getLeiaListener() {
        return listener;
    }

    public void setLeiaListener(LeiaListener listener) {
        this.listener = listener;
    }
    
}
