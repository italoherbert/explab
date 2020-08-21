package italo.explab.arvore.exp.matriz;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ObjetoRecursoNo;
import italo.explab.arvore.busca.ArvBuscaManager;
import italo.explab.arvore.busca.var.VarBusca;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.ExpRecurso;
import italo.explab.arvore.exp.OOChamada;
import italo.explab.arvore.exp.matriz.indice.ExpMatIDs;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.recursos.classe.Objeto;

public class MatrizExp extends Exp implements ExpRecurso, ObjetoRecursoNo {

    private String nome;
    private ExpMatIDs indices;
    private int acesso;
    private boolean transposta;
    
    private OOChamada ooChamador;    
    private OOChamada ooChamada;
               
    private VarBusca runtimeVarBusca; 
    private Objeto runtimeObjeto;

    @Override
    public Instrucao novo( ExecNo parent ) {
        MatrizExp matexp = new MatrizExp();
        super.carrega( matexp, parent );
                
        if ( ooChamador != null )
            ooChamador.setObjetoChamador( matexp );
        
        if ( ooChamada != null )
            ooChamada.setChamada( matexp );
        
        matexp.setNome( nome );     
        matexp.setAcesso( acesso );
        matexp.setTransposta( transposta ); 
        matexp.setExpMatIDs( indices.novo( matexp ) );        
        matexp.setOOChamador( ooChamador );        
        matexp.setOOChamada( ooChamada );
        
        return matexp;
    }

    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        indices.setBaseParamsParente( parent ); 
    }

    @Override
    public void runtimeConfigObjBusca( Objeto obj, ArvBuscaManager abmanager ) {
        this.runtimeObjeto = obj;
        this.runtimeVarBusca = abmanager.getEsteVarBusca();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public ExpMatIDs getExpMatIDs() {
        return indices;
    }
    
    public void setExpMatIDs( ExpMatIDs indices ) {
        this.indices = indices;
    }

    public int getAcesso() {
        return acesso;
    }

    public void setAcesso(int acesso) {
        this.acesso = acesso;
    }

    public OOChamada getOOChamador() {
        return ooChamador;
    }

    @Override
    public void setOOChamador(OOChamada ooChamador) {
        this.ooChamador = ooChamador;
    }

    public OOChamada getOOChamada() {
        return ooChamada;
    }

    @Override
    public void setOOChamada(OOChamada ooChamada) {
        this.ooChamada = ooChamada;
    }

    public VarBusca getRuntimeVarBusca() {
        return runtimeVarBusca;
    }

    public void setRuntimeVarBusca(VarBusca runtimeVarBusca) {
        this.runtimeVarBusca = runtimeVarBusca;
    }

    @Override
    public Objeto getRuntimeObjeto() {
        return runtimeObjeto;
    }

    public void setRuntimeObjeto(Objeto objeto) {
        this.runtimeObjeto = objeto;
    }

    public boolean isTransposta() {
        return transposta;
    }

    public void setTransposta(boolean transposta) {
        this.transposta = transposta;
    }
    
}
