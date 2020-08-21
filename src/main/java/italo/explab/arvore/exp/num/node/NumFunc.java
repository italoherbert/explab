package italo.explab.arvore.exp.num.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.funcoumat.FuncOuMatELExp;
import italo.explab.arvore.instrucao.Instrucao;

public class NumFunc extends NumericaExp {
    
    private FuncOuMatELExp funcExp;

    @Override
    public Instrucao novo( ExecNo parent ) {
        NumFunc nfunc = new NumFunc();
        super.carrega( nfunc, parent );
        
        nfunc.setFuncExp((FuncOuMatELExp)funcExp.novo( nfunc ) ); 
        
        return nfunc;
    }
    
    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        funcExp.setBaseParamsParente( parent ); 
    }
       
    public FuncOuMatELExp getFuncExp() {
        return funcExp;
    }

    public void setFuncExp(FuncOuMatELExp funcExp) {
        this.funcExp = funcExp;
    }
    
}
