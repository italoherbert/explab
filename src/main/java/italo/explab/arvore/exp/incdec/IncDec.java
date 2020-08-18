package italo.explab.arvore.exp.incdec;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.num.node.NumericaExp;
import italo.explab.arvore.exp.variavel.VariavelExp;
import italo.explab.arvore.instrucao.Instrucao;

public class IncDec extends NumericaExp {
        
    private VariavelExp variavelExp;
    private boolean inc = true;
    private boolean ant = false;
    private boolean execSeAntIgual = true;
    
    @Override
    public Instrucao novo( ExecNo parent ) {
        IncDec incdec = new IncDec();
        super.carrega( incdec, parent );
        
        incdec.setVariavelExp( (VariavelExp)variavelExp.novo( incdec ) );
        incdec.setInc( inc );
        incdec.setAnt( ant ); 
        incdec.setExecSeAntIgual( execSeAntIgual ); 
        
        return incdec;
    }
    
    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        variavelExp.setBaseParamsParente( parent ); 
    }
            
    public VariavelExp getVariavelExp() {
        return variavelExp;
    }

    public void setVariavelExp(VariavelExp variavelExp) {
        this.variavelExp = variavelExp;
    }

    public boolean isInc() {
        return inc;
    }

    public void setInc(boolean inc) {
        this.inc = inc;
    }

    public boolean isAnt() {
        return ant;
    }

    public void setAnt(boolean ant) {
        this.ant = ant;
    }

    public boolean isExecSeAntIgual() {
        return execSeAntIgual;
    }

    public void setExecSeAntIgual(boolean execSeAntIgual) {
        this.execSeAntIgual = execSeAntIgual;
    }

}
