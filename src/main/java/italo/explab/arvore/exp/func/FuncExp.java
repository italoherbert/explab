package italo.explab.arvore.exp.func;

import italo.explab.MetodoParam;
import italo.explab.arvore.BlocoNo;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ObjetoRecursoNo;
import italo.explab.arvore.bloco.Bloco;
import italo.explab.arvore.busca.ArvBuscaManager;
import italo.explab.arvore.busca.func.FuncBusca;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.ExpRecurso;
import static italo.explab.arvore.exp.ExpRecurso.NORMAL;
import italo.explab.arvore.exp.OOChamada;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.recursos.classe.Objeto;
import italo.explab.var.Var;

public class FuncExp extends Exp implements ExpRecurso, BlocoNo, ObjetoRecursoNo {
    
    private String nome;
    private MetodoParam[] params;
    private int acesso = NORMAL;
    private Var retornada;     
    private boolean transposta;    
        
    private Bloco bloco;
    private OOChamada ooChamador;    
    private OOChamada ooChamada;
               
    private FuncBusca runtimeFuncBusca; 
    private Objeto runtimeObjeto;
    
    @Override
    public Instrucao novo( ExecNo parent ) {
        FuncExp fexp = new FuncExp();
        super.carrega( fexp, parent );
        
        if ( ooChamador != null )
            ooChamador.setObjetoChamador( fexp );
        
        if ( ooChamada != null )
            ooChamada.setChamada( fexp ); 
        
        fexp.setNome( nome );                       
        fexp.setParams( params );
        fexp.setAcesso( acesso );
        fexp.setTransposta( transposta ); 
                        
        fexp.setBloco( (Bloco)bloco.novo( fexp ) );
        fexp.setOOChamador( ooChamador ); 
        fexp.setOOChamada( ooChamada ); 
        
        if ( retornada != null )
            fexp.setRetornada( retornada.nova() );
                
        return fexp;
    }

    @Override
    public void setBaseParamsParente( ExecNo parent ) {                                                       
        if ( ooChamador != null )
            ((Instrucao)ooChamador.getChamada()).setBaseParamsParente( parent ); 
    }
        
    @Override
    public void runtimeConfigObjBusca( Objeto obj, ArvBuscaManager abmanager ) {
        this.runtimeObjeto = obj;
        this.runtimeFuncBusca = abmanager.getEsteOuSuperFuncBusca();
    }
    
    @Override
    public Bloco getBloco() {
        return bloco;
    }

    @Override
    public void setBloco(Bloco bloco) {
        this.bloco = bloco;
    }
        
    public Var getRetornada() {
        return retornada;
    }

    public void setRetornada(Var retornada) {
        this.retornada = retornada;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public MetodoParam[] getParams() {
        return params;
    }

    public void setParams(MetodoParam[] params) {
        this.params = params;
    }

    public int getAcesso() {
        return acesso;
    }

    public void setAcesso(int acesso) {
        this.acesso = acesso;
    }

    @Override
    public Objeto getRuntimeObjeto() {
        return runtimeObjeto;
    }

    public void setRuntimeObjeto(Objeto objeto) {
        this.runtimeObjeto = objeto;
    }

    public FuncBusca getRuntimeFuncBusca() {
        return runtimeFuncBusca;
    }

    public void setRuntimeFuncBusca(FuncBusca funcBusca) {
        this.runtimeFuncBusca = funcBusca;
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

    public boolean isTransposta() {
        return transposta;
    }

    public void setTransposta(boolean transposta) {
        this.transposta = transposta;
    }
        
}
