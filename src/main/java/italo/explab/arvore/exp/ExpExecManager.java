package italo.explab.arvore.exp;

import italo.explab.arvore.exp.bool.exec.BoolCompExec;
import italo.explab.arvore.exp.bool.exec.BoolExpExec;
import italo.explab.arvore.exp.bool.exec.BoolFuncExec;
import italo.explab.arvore.exp.bool.exec.BoolValorExec;
import italo.explab.arvore.exp.bool.exec.BoolNaoExpValorExec;
import italo.explab.arvore.exp.funcoumat.ChamadaFuncOuMatELExec;
import italo.explab.arvore.exp.func.FuncExpExec;
import italo.explab.arvore.exp.matriz.MatrizExpExec;
import italo.explab.arvore.exp.matriz.valor.MatrizValorExec;
import italo.explab.arvore.exp.matriz.vetN1IncN2.VetN1IncN2Exec;
import italo.explab.arvore.exp.num.exec.NumExpExec;
import italo.explab.arvore.exp.num.exec.NumFuncExec;
import italo.explab.arvore.exp.num.exec.NumMatExec;
import italo.explab.arvore.exp.num.exec.NumRealValorExec;
import italo.explab.arvore.exp.num.exec.NumAtribExec;
import italo.explab.arvore.exp.num.exec.NumIncDecExec;
import italo.explab.arvore.exp.num.exec.NumVariavelExec;
import italo.explab.arvore.exp.num.exec.NumVetN1IncN2Exec;
import italo.explab.arvore.exp.obj.NovoObjetoExec;
import italo.explab.arvore.exp.obj.SuperObjetoExec;
import italo.explab.arvore.exp.string.exec.StrExpExec;
import italo.explab.arvore.exp.string.exec.StrValorExec;
import italo.explab.arvore.exp.valornull.NullValorExec;
import italo.explab.arvore.exp.variavel.VariavelExpExec;
import italo.explab.arvore.exp.variavel.func.FuncVarExpExec;

public class ExpExecManager {
    
    private final VariavelExpExec variavelExpExec = new VariavelExpExec();
    private final ChamadaFuncOuMatELExec chamadaFuncOuMatELExec = new ChamadaFuncOuMatELExec();
    private final MatrizExpExec matrizExpExec = new MatrizExpExec();
    private final FuncExpExec funcExpExec = new FuncExpExec();
    
    private final BoolCompExec boolCompExec = new BoolCompExec();
    private final BoolValorExec boolValorExec = new BoolValorExec();
    private final BoolNaoExpValorExec boolVariavelExec = new BoolNaoExpValorExec();
    private final BoolFuncExec boolFuncExec = new BoolFuncExec();
    private final BoolExpExec boolExpExec = new BoolExpExec();

    private final NumVetN1IncN2Exec numVetN1IncN2Exec = new NumVetN1IncN2Exec();
    private final NumMatExec numMatExec = new NumMatExec();
    private final NumRealValorExec numRealValorExec = new NumRealValorExec();
    private final NumVariavelExec numVariavelExec = new NumVariavelExec();
    private final NumFuncExec numFuncExec = new NumFuncExec();
    private final NumExpExec numExpExec = new NumExpExec();    
    private final NumAtribExec numAtribExec = new NumAtribExec();
    private final NumIncDecExec numIncDecExec = new NumIncDecExec();
    
    private final StrValorExec strValorExec = new StrValorExec();
    private final StrExpExec strExpExec = new StrExpExec();
    
    private final NovoObjetoExec novoObjetoExec = new NovoObjetoExec();
    private final SuperObjetoExec superObjetoExec = new SuperObjetoExec();
    
    private final MatrizValorExec matrizValorExec = new MatrizValorExec();
    private final VetN1IncN2Exec vetN1IncN2Exec = new VetN1IncN2Exec();
    
    private final FuncVarExpExec funcVarExpExec = new FuncVarExpExec();    
    
    private final NullValorExec nullValorExec = new NullValorExec();
        
    public VariavelExpExec getVariavelExpExec() {
        return variavelExpExec;
    }

    public ChamadaFuncOuMatELExec getChamadaFuncOuMatELExec() {
        return chamadaFuncOuMatELExec;
    }

    public MatrizExpExec getMatrizExpExec() {
        return matrizExpExec;
    }

    public FuncExpExec getFuncExpExec() {
        return funcExpExec;
    }
    

    public BoolCompExec getBoolCompExec() {
        return boolCompExec;
    }

    public BoolValorExec getBoolValorExec() {
        return boolValorExec;
    }

    public BoolNaoExpValorExec getBoolVariavelExec() {
        return boolVariavelExec;
    }
    
    public BoolFuncExec getBoolFuncExec() {
        return boolFuncExec;
    }
    
    public BoolExpExec getBoolExpExec() {
        return boolExpExec;
    }
        
    public NumRealValorExec getNumRealValorExec() {
        return numRealValorExec;
    }

    public NumAtribExec getNumAtribExec() {
        return numAtribExec;
    }

    public NumIncDecExec getNumIncDecExec() {
        return numIncDecExec;
    }

    public NumMatExec getNumMatExec() {
        return numMatExec;
    }

    public NumVetN1IncN2Exec getNumVetN1IncN2Exec() {
        return numVetN1IncN2Exec;
    }
    
    public NumVariavelExec getNumVariavelExec() {
        return numVariavelExec;
    }
            
    public NumFuncExec getNumFuncExec() {
        return numFuncExec;
    }

    public NumExpExec getNumExpExec() {
        return numExpExec;
    }       

    public StrValorExec getStrValorExec() {
        return strValorExec;
    }

    public StrExpExec getStrExpExec() {
        return strExpExec;
    }

    public NovoObjetoExec getNovoObjetoExec() {
        return novoObjetoExec;
    }

    public MatrizValorExec getMatrizValorExec() {
        return matrizValorExec;
    }

    public VetN1IncN2Exec getVetN1IncN2Exec() {
        return vetN1IncN2Exec;
    }

    public NullValorExec getNullValorExec() {
        return nullValorExec;
    }

    public FuncVarExpExec getFuncVarExpExec() {
        return funcVarExpExec;
    }
    
    public SuperObjetoExec getSuperObjetoExec() {
        return superObjetoExec;
    }
           
}
