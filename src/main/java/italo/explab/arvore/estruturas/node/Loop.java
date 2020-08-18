package italo.explab.arvore.estruturas.node;

import italo.explab.arvore.AbstractExecNo;
import italo.explab.arvore.BlocoNo;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.bloco.Bloco;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.instrucao.Instrucao;

public abstract class Loop extends Instrucao implements BlocoNo {

    private Exp condicao;
    private Bloco bloco;

    @Override
    protected void carrega( AbstractExecNo novo, ExecNo parent ) {
        super.carrega( novo, parent );
        
        Loop loop = (Loop)novo;
        loop.setCondicao( (Exp)condicao.novo( novo ) );
        loop.setBloco( (Bloco)bloco.novo( novo ) );         
    }

    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        condicao.setBaseParamsParente( parent );
        bloco.setBaseParamsParente( this );
    }        
    
    public Exp getCondicao() {
        return condicao;
    }

    public void setCondicao(Exp condicao) {
        this.condicao = condicao;
    }

    @Override
    public Bloco getBloco() {
        return bloco;
    }

    @Override
    public void setBloco(Bloco bloco) {
        this.bloco = bloco;
    }
    
}
