package italo.explab.arvore.estruturas.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.grupo.Grupo;
import italo.explab.arvore.instrucao.Instrucao;

public class Se extends Instrucao {
    
    private Exp condicao;
    private Grupo entaoGrupo;
    private Grupo senaoGrupo;

    @Override
    public Instrucao novo( ExecNo parent ) {
        Se se = new Se();
        super.carrega( se, parent );
        
        se.setCondicao( (Exp)condicao.novo( se ) );
        se.setEntaoGrupo( (Grupo)entaoGrupo.novo( se ) );
        se.setSenaoGrupo( (Grupo)senaoGrupo.novo( se ) );
        
        return se;
    }

    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        condicao.setBaseParamsParente( parent ); 
        entaoGrupo.setBaseParamsParente( parent );
        senaoGrupo.setBaseParamsParente( parent ); 
    }
    
    public Exp getCondicao() {
        return condicao;
    }

    public void setCondicao(Exp condicao) {
        this.condicao = condicao;
    }

    public Grupo getEntaoGrupo() {
        return entaoGrupo;
    }

    public void setEntaoGrupo(Grupo entaoGrupo) {
        this.entaoGrupo = entaoGrupo;
    }

    public Grupo getSenaoGrupo() {
        return senaoGrupo;
    }

    public void setSenaoGrupo(Grupo senaoGrupo) {
        this.senaoGrupo = senaoGrupo;
    }
    
}
