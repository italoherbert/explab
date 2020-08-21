package italo.explab.arvore.exp.funcoumat;

import italo.explab.arvore.BlocoNo;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ObjetoRecursoNo;
import italo.explab.arvore.bloco.Bloco;
import italo.explab.arvore.busca.ArvBuscaManager;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.ExpRecurso;
import italo.explab.var.Var;
import italo.explab.arvore.busca.func.FuncBusca;
import italo.explab.arvore.busca.var.VarBusca;
import italo.explab.arvore.exp.OOChamada;
import italo.explab.arvore.exp.matriz.indice.ExpMatIDs;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.recursos.classe.Objeto;

public class FuncOuMatELExp extends Exp implements ExpRecurso, ObjetoRecursoNo, BlocoNo {

    private String nome;
    private Exp[] params;
    private int acesso = NORMAL;
    private Var retornada;     
    private ExpMatIDs expMatIDs;
    private boolean transposta;
       
    private Bloco bloco;
    private OOChamada ooChamador;    
    private OOChamada ooChamada;
                
    private FuncBusca runtimeFuncBusca;
    private VarBusca runtimeVarBusca; 
    private Objeto runtimeObjeto; 

    @Override
    public Instrucao novo( ExecNo parent ) {
        FuncOuMatELExp fexp = new FuncOuMatELExp();
        super.carrega( fexp, parent );
        
        if ( ooChamador != null )
            ooChamador.setObjetoChamador( fexp );
        
        if ( ooChamada != null )
            ooChamada.setChamada( fexp ); 
        
        fexp.setNome( nome );
        
        Exp[] parametros = new Exp[ params.length ];
        for( int k = 0; k < parametros.length; k++ )
            parametros[ k ] = (Exp)params[ k ].novo( fexp );        
        
        fexp.setParams( parametros );
        fexp.setAcesso( acesso );
                        
        fexp.setBloco( (Bloco)bloco.novo( fexp ) );
        fexp.setOOChamador( ooChamador ); 
        fexp.setOOChamada( ooChamada ); 
        
        if ( retornada != null )
            fexp.setRetornada( retornada.nova() );
                
        return fexp;
    }

    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        for( Exp param : params )
            param.setParente( parent );                
                                
        if ( ooChamador != null )
            ((Instrucao)ooChamador.getChamada()).setBaseParamsParente( parent ); 
    }

    @Override
    public void runtimeConfigObjBusca( Objeto obj, ArvBuscaManager abmanager ) {
        this.runtimeObjeto = obj;
        this.runtimeFuncBusca = abmanager.getEsteOuSuperFuncBusca();
        this.runtimeVarBusca = abmanager.getEsteOuSuperVarBusca();
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

    public Exp[] getParams() {
        return params;
    }

    public void setParams(Exp[] params) {
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

    public VarBusca getRuntimeVarBusca() {
        return runtimeVarBusca;
    }

    public void setRuntimeVarBusca(VarBusca varbusca) {
        this.runtimeVarBusca = varbusca;
    }

    public OOChamada getOOChamador() {
        return ooChamador;
    }

    public ExpMatIDs getExpMatIDs() {
        return expMatIDs;
    }

    public void setExpMatIDs(ExpMatIDs expMatIDs) {
        this.expMatIDs = expMatIDs;
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

