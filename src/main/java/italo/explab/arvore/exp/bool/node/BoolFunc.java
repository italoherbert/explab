package italo.explab.arvore.exp.bool.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.funcoumat.FuncOuMatELExp;
import italo.explab.arvore.instrucao.Instrucao;

public class BoolFunc extends BooleanExp {
    
    private FuncOuMatELExp funcExp;

    @Override
    public Instrucao novo( ExecNo parent ) {
        BoolFunc bf = new BoolFunc();
        super.carrega( bf, parent );
        
        bf.setFuncExp( (FuncOuMatELExp)funcExp.novo( bf ) ); 
        
        return bf;
    }

    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        funcExp.setBaseParamsParente( parent ); 
    }
    
    public FuncOuMatELExp getFuncExp() {
        return funcExp;
    }
    
    public void setFuncExp( FuncOuMatELExp funcExp ) {
        this.funcExp = funcExp;
    }
    
}
