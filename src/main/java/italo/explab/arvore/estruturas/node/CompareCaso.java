package italo.explab.arvore.estruturas.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.grupo.Grupo;
import italo.explab.arvore.instrucao.Instrucao;

public class CompareCaso extends Instrucao {
    
    private Exp exp;
    private CompareCasoGrupo[] casoGrupos;
    private Grupo padraoGrupo; 

    @Override
    public Instrucao novo( ExecNo parent ) {
        CompareCaso cc = new CompareCaso();
        super.carrega( cc, parent );
        
        cc.setExp( (Exp)exp.novo( cc ) );
        
        CompareCasoGrupo[] grupos = new CompareCasoGrupo[ casoGrupos.length ];
        for( int k = 0; k < grupos.length; k++ )
            grupos[ k ] = (CompareCasoGrupo)casoGrupos[ k ].novo( cc );
        
        cc.setCasoGrupos( grupos );
        cc.setPadraoGrupo( (Grupo)padraoGrupo.novo( cc ) );
        
        return cc;
    }

    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        exp.setBaseParamsParente( parent ); 
        for( CompareCasoGrupo ccg : casoGrupos )
            ccg.setBaseParamsParente( parent );
        
        padraoGrupo.setBaseParamsParente( parent ); 
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    public CompareCasoGrupo[] getCasoGrupos() {
        return casoGrupos;
    }

    public void setCasoGrupos(CompareCasoGrupo[] blocos) {
        this.casoGrupos = blocos;
    }

    public Grupo getPadraoGrupo() {
        return padraoGrupo;
    }

    public void setPadraoGrupo(Grupo padraoGrupo) {
        this.padraoGrupo = padraoGrupo;
    }
            
}
