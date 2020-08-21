package italo.explab.arvore.exp.variavel;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.busca.ArvBuscaManager;
import italo.explab.arvore.busca.var.VarBusca;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.ExpRecurso;
import italo.explab.arvore.ObjetoRecursoNo;
import italo.explab.arvore.exp.OOChamada;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.recursos.classe.Objeto;

public class VariavelExp extends Exp implements ExpRecurso, ObjetoRecursoNo {
    
    private String nome;        
    private int acesso = NORMAL;
    
    private OOChamada ooChamador;
    private OOChamada ooChamada;
    
    private VarBusca runtimeVarBusca;    
    private Objeto runtimeObjeto;

    @Override
    public Instrucao novo( ExecNo parent ) {
        VariavelExp varexp = new VariavelExp();
        super.carrega( varexp, parent );
               
        if ( ooChamador != null )
            ooChamador.setObjetoChamador( varexp );
        
        if ( ooChamada != null )
            ooChamada.setChamada( varexp ); 
        
        varexp.setNome( nome );
        varexp.setAcesso( acesso ); 
        varexp.setOOChamador( ooChamador );        
        varexp.setOOChamada( ooChamada );
                        
        return varexp;
    }
    
    @Override
    public void setBaseParamsParente( ExecNo parent ) {                                
        if ( ooChamador != null )
            ((Instrucao)ooChamador.getChamada()).setBaseParamsParente( parent ); 
    }

    @Override
    public void runtimeConfigObjBusca( Objeto obj, ArvBuscaManager abmanager) {
        this.runtimeObjeto = obj;
        this.runtimeVarBusca = abmanager.getEsteOuSuperVarBusca();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getAcesso() {
        return acesso;
    }

    public void setAcesso(int acesso) {
        this.acesso = acesso;
    }

    public VarBusca getRuntimeVarBusca() {
        return runtimeVarBusca;
    }

    public void setRuntimeVarBusca(VarBusca runtimeVarBusca) {
        this.runtimeVarBusca = runtimeVarBusca;
    }

    public OOChamada getOOChamador() {
        return ooChamador;
    }

    @Override
    public void setOOChamador(OOChamada ooChamador) {
        this.ooChamador = ooChamador;
    }

    public OOChamada getOOChamavel() {
        return ooChamada;
    }

    @Override
    public void setOOChamada(OOChamada ooChamavel) {
        this.ooChamada = ooChamavel;
    }

    @Override
    public Objeto getRuntimeObjeto() {
        return runtimeObjeto;
    }

    public void setRuntimeObjeto(Objeto runtimeObjeto) {
        this.runtimeObjeto = runtimeObjeto;
    }

}
