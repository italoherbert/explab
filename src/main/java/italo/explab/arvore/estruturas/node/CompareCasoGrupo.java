package italo.explab.arvore.estruturas.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.grupo.Grupo;
import italo.explab.arvore.instrucao.Instrucao;

public class CompareCasoGrupo extends Grupo {
    
    private Exp valor;

    @Override
    public Instrucao novo( ExecNo parent ) {
        CompareCasoGrupo grupo = new CompareCasoGrupo();
        super.carrega( grupo, parent );
        
        grupo.setValor( (Exp)valor.novo( grupo ) ); 
        
        return grupo;
    }
        
    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        super.setBaseParamsParente( this );        
        valor.setBaseParamsParente( parent ); 
    }

    public Exp getValor() {
        return valor;
    }

    public void setValor(Exp valor) {
        this.valor = valor;
    }

}
