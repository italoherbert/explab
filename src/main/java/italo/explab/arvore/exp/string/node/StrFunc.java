package italo.explab.arvore.exp.string.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.funcoumat.FuncOuMatELExp;
import italo.explab.arvore.instrucao.Instrucao;

public class StrFunc extends StringExp {
    
    private FuncOuMatELExp funcExp;

    @Override
    public Instrucao novo( ExecNo parent ) {
        StrFunc sfunc = new StrFunc(); 
        super.carrega( sfunc, parent );
        
        sfunc.setFuncExp((FuncOuMatELExp)funcExp.novo( sfunc ) );
        
        return sfunc;
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
