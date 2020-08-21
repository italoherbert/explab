package italo.explab.arvore.exp.obj;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.ExpRecurso;
import italo.explab.arvore.busca.ArvBuscaManager;
import italo.explab.arvore.ObjetoRecursoNo;
import italo.explab.arvore.exp.OOChamada;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.recursos.classe.Objeto;

public class NovoObjeto extends Exp implements ExpRecurso, ObjetoRecursoNo {
    
    private String classeNome;
    private Exp[] construtorParams;
    private SuperObjeto superObjeto = null;
    
    private OOChamada ooChamador;
    private OOChamada ooChamada;
    
    private Objeto runtimeObjeto;

    @Override
    public Instrucao novo( ExecNo parent ) {
        NovoObjeto nobj = new NovoObjeto();
        super.carrega( nobj, parent );
              
        if ( ooChamador != null )
            ooChamador.setObjetoChamador( nobj );
        
        if ( ooChamada != null )
            ooChamada.setChamada( nobj ); 
        
        nobj.setClasseNome( classeNome );
        nobj.setOOChamador( ooChamador );
        nobj.setOOChamada( ooChamada ); 
        nobj.setSuperObjeto( superObjeto ); 
        
        Exp[] params = new Exp[ construtorParams.length ];
        for( int k = 0; k < params.length; k++ )
            params[ k ] = (Exp)construtorParams[ k ].novo( nobj ); 
        
        nobj.setConstrutorParams( params ); 
                
        return nobj;
    }
    
    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        for( Exp param : construtorParams )
            param.setParente( parent );
                        
        if ( ooChamador != null )
            ((Instrucao)ooChamador.getChamada()).setBaseParamsParente( parent ); 
    }

    @Override
    public void runtimeConfigObjBusca(Objeto obj, ArvBuscaManager abmanager) {
        runtimeObjeto = obj;
    }
    
    public OOChamada getOOChamador() {
        return ooChamador;
    }
    
    @Override
    public void setOOChamador(OOChamada expRec) {
        this.ooChamador = expRec;
    }

    public OOChamada getOOChamada() {
        return ooChamada;
    }

    @Override
    public void setOOChamada(OOChamada ooChamada) {
        this.ooChamada = ooChamada;
    }
        
    public String getClasseNome() {
        return classeNome;
    }

    public void setClasseNome(String classeNome) {
        this.classeNome = classeNome;
    }

    public Exp[] getConstrutorParams() {
        return construtorParams;
    }

    public void setConstrutorParams(Exp[] construtorParams) {
        this.construtorParams = construtorParams;
    }

    public SuperObjeto getSuperObjeto() {
        return superObjeto;
    }

    public void setSuperObjeto(SuperObjeto superObjeto) {
        this.superObjeto = superObjeto;
    }

    @Override
    public Objeto getRuntimeObjeto() {        
        return runtimeObjeto;
    }

    public void setRuntimeObjeto(Objeto objeto) {
        this.runtimeObjeto = objeto;
    }

}
