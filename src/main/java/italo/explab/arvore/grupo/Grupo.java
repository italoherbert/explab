package italo.explab.arvore.grupo;

import italo.explab.arvore.AbstractExecNo;
import italo.explab.arvore.BlocoNo;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.bloco.Bloco;
import italo.explab.arvore.instrucao.Instrucao;

public class Grupo extends Instrucao implements BlocoNo {

    private Bloco bloco;

    @Override
    protected void carrega( AbstractExecNo novoNo, ExecNo parent ) {
        super.carrega( novoNo, parent ); 
        ((Grupo)novoNo).setBloco( (Bloco)bloco.novo( novoNo ) );         
    }

    @Override
    public Instrucao novo( ExecNo parent ) {
        Grupo grupo = new Grupo();
        this.carrega( grupo, parent ); 
                
        return grupo;
    }

    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        bloco.setBaseParamsParente( this ); 
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
