package italo.explab.arvore.classe;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.busca.construtor.ConstrutorBusca;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.instrucao.Instrucao;

public class EsteOuSuperConstrutor extends Instrucao {

    private Exp[] params;
    private ConstrutorBusca construtorBusca;
    
    @Override
    public Instrucao novo( ExecNo parent ) {
        EsteOuSuperConstrutor cci = new EsteOuSuperConstrutor();
        super.carrega( cci, parent );
        
        cci.setConstrutorBusca( construtorBusca );
        
        Exp[] parametros = new Exp[ params.length ];
        for( int k = 0; k < params.length; k++ )
            parametros[k] = (Exp)params[k].novo( cci );
        
        cci.setParams( parametros );
        
        return cci;
    }

    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        for( Exp param : params )
            param.setParente( parent ); 
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
    
}
