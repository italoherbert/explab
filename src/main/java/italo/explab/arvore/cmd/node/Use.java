package italo.explab.arvore.cmd.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.string.node.StringExp;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.arvore.cmd.node.use.UseListener;

public class Use extends Instrucao {
    
    private StringExp useArqNomeExp;
    private UseListener listener;

    @Override
    public Instrucao novo( ExecNo parent ) {
        Use use = new Use();
        super.carrega( use, parent );
        
        use.setUseArqNomeExp( (StringExp)useArqNomeExp.novo( use ) );
        use.setUseListener( listener ); 
        
        return use;
    }

    @Override
    public void setBaseParamsParente(ExecNo parent) {
        useArqNomeExp.setBaseParamsParente( parent );
    }
    
    public StringExp getUseArqNomeExp() {
        return useArqNomeExp;
    }

    public void setUseArqNomeExp(StringExp useArqNomeExp) {
        this.useArqNomeExp = useArqNomeExp;
    }    

    public UseListener getUseListener() {
        return listener;
    }

    public void setUseListener(UseListener listener) {
        this.listener = listener;
    }
    
}
