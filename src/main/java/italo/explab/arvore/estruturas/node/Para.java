package italo.explab.arvore.estruturas.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.bloco.BlocoRecursos;
import italo.explab.arvore.grupo.Grupo;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.recursos.RecursoManager;

public class Para extends Loop implements BlocoRecursos {

    private Grupo iniInstsGrupo;
    private Grupo aposFimITInstsGrupo;

    @Override
    public RecursoManager getRecursos() {
        return super.getBloco().getRecursos();
    }

    @Override
    public Instrucao novo( ExecNo parent ) {
        Para para = new Para();
        super.carrega( para, parent );
                 
        para.setIniInstsGrupo( (Grupo)iniInstsGrupo.novo( para ) );
        para.setAposFimITInstsGrupo( (Grupo)aposFimITInstsGrupo.novo( para ) );
        
        para.getIniInstsGrupo().getBloco().setBlocoRecursos( para );
        para.getAposFimITInstsGrupo().getBloco().setBlocoRecursos( para ); 
                
        return para;
    }

    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        super.setBaseParamsParente( this );
        
        iniInstsGrupo.setBaseParamsParente( this );
        aposFimITInstsGrupo.setBaseParamsParente( this ); 
    }

    public Grupo getIniInstsGrupo() {
        return iniInstsGrupo;
    }

    public void setIniInstsGrupo(Grupo iniInstsGrupo) {
        this.iniInstsGrupo = iniInstsGrupo;
    }

    public Grupo getAposFimITInstsGrupo() {
        return aposFimITInstsGrupo;
    }

    public void setAposFimITInstsGrupo(Grupo aposFimITInstsGrupo) {
        this.aposFimITInstsGrupo = aposFimITInstsGrupo;
    }

 
}
