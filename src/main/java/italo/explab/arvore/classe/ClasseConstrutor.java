package italo.explab.arvore.classe;

import italo.explab.arvore.BlocoNo;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ObjetoRecursoNo;
import italo.explab.arvore.bloco.Bloco;
import italo.explab.arvore.busca.construtor.ConstrutorBusca;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.recursos.classe.Objeto;

public class ClasseConstrutor extends Instrucao implements BlocoNo, ObjetoRecursoNo {
            
    private Exp[] params; 
    private Bloco bloco;
    private ConstrutorBusca construtorBusca;
    
    private Objeto runtimeObjeto;
    
    @Override
    public Instrucao novo( ExecNo parent ) {
        ClasseConstrutor cc = new ClasseConstrutor();
        super.carrega( cc, parent );
        
        cc.setBloco( (Bloco)bloco.novo( cc ) );
        cc.setConstrutorBusca( construtorBusca );
        
        Exp[] parametros = new Exp[ params.length ];
        for( int k = 0; k < parametros.length; k++ )
            parametros[k] = (Exp)params[k].novo( cc );                    
        
        cc.setParams( parametros );
        
        return cc;
    }

    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        if ( params != null )
            for( Exp param : params )
                param.setParente( parent );         
    } 
                     
    @Override
    public Bloco getBloco() {
        return bloco;
    }

    @Override
    public void setBloco(Bloco bloco) {
        this.bloco = bloco;
    }

    public Exp[] getParams() {
        return params;
    }

    public void setParams(Exp[] params) {
        this.params = params;
    }

    public ConstrutorBusca getConstrutorBusca() {
        return construtorBusca;
    }

    public void setConstrutorBusca(ConstrutorBusca construtorBusca) {
        this.construtorBusca = construtorBusca;
    }

    @Override
    public Objeto getRuntimeObjeto() {
        return runtimeObjeto;
    }

    public void setRuntimeObjeto(Objeto obj) {
        this.runtimeObjeto = obj;
    }

}
