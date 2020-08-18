package italo.explab.arvore.exp;

import italo.explab.arvore.ExecManager;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.bool.node.BoolComp;
import italo.explab.arvore.exp.bool.node.BoolExp;
import italo.explab.arvore.exp.bool.node.BoolFunc;
import italo.explab.arvore.exp.bool.node.BoolValor;
import italo.explab.arvore.exp.bool.node.BoolNaoExpValor;
import italo.explab.arvore.exp.func.FuncExp;
import italo.explab.arvore.exp.funcoumat.FuncOuMatELExp;
import italo.explab.arvore.exp.matriz.MatrizExp;
import italo.explab.arvore.exp.matriz.valor.GenericaMatrizValor;
import italo.explab.arvore.exp.matriz.valor.NumMatrizValor;
import italo.explab.arvore.exp.matriz.vetN1IncN2.VetN1IncN2;
import italo.explab.arvore.exp.num.node.NumExp;
import italo.explab.arvore.exp.num.node.NumFunc;
import italo.explab.arvore.exp.num.node.NumMat;
import italo.explab.arvore.exp.num.node.NumRealValor;
import italo.explab.arvore.exp.num.node.NumAtrib;
import italo.explab.arvore.exp.num.node.NumIncDec;
import italo.explab.arvore.exp.num.node.NumVariavel;
import italo.explab.arvore.exp.num.node.NumVetN1IncN2;
import italo.explab.arvore.exp.obj.NovoObjeto;
import italo.explab.arvore.exp.obj.SuperObjeto;
import italo.explab.arvore.exp.string.node.StrExp;
import italo.explab.arvore.exp.string.node.StrFunc;
import italo.explab.arvore.exp.string.node.StrValor;
import italo.explab.arvore.exp.string.node.StrVariavel;
import italo.explab.arvore.exp.valornull.NullValor;
import italo.explab.arvore.exp.variavel.VariavelExp;
import italo.explab.arvore.exp.variavel.func.FuncVarExp;
import italo.explab.codigo.Codigo;

public class ExpFactory {

    private final ExpExecManager  expExecManager;
    private final ExecManager execManager;

    public ExpFactory( ExecManager manager ) {
        this.execManager = manager;
        this. expExecManager = manager.getExpExecManager();
    }
    
    public NovoObjeto novoNovoObjeto( int i, ExecNo parente, Codigo codigo ) {
        NovoObjeto no = new NovoObjeto();
        no.setI( i );
        no.setParente( parente );
        no.setCodigo( codigo ); 
        no.setExec( expExecManager.getNovoObjetoExec() ); 
        return no;
    }
    
    public SuperObjeto novoSuperObjeto( int i, ExecNo parente, Codigo codigo ) {
        SuperObjeto no = new SuperObjeto();
        no.setI( i );
        no.setParente( parente );
        no.setCodigo( codigo ); 
        no.setExec( expExecManager.getSuperObjetoExec() ); 
        return no;
    }
    
    public VariavelExp novoVariavelExp( int i, ExecNo parente, Codigo codigo ) {
        VariavelExp exp = new VariavelExp();
        exp.setI( i );
        exp.setParente( parente );
        exp.setCodigo( codigo ); 
        exp.setExec( expExecManager.getVariavelExpExec() );
        return exp;
    }
    
    public FuncOuMatELExp novoFuncOuMatELExp( int i, ExecNo parente, Codigo codigo ) {
        FuncOuMatELExp exp = new FuncOuMatELExp();
        exp.setI( i );
        exp.setParente( parente );
        exp.setCodigo( codigo ); 
        exp.setExec(  expExecManager.getChamadaFuncOuMatELExec() ); 
        exp.setBloco( execManager.getExecNoFactory().novoBloco( i, exp, codigo ) );
        return exp;
    }
    
    public MatrizExp novoMatrizExp( int i, ExecNo parente, Codigo codigo ) {
        MatrizExp exp = new MatrizExp();
        exp.setI( i );
        exp.setParente( parente );
        exp.setCodigo( codigo ); 
        exp.setExec( expExecManager.getMatrizExpExec() );
        return exp;
    }
    
    public FuncExp novoFuncExp( int i, ExecNo parente, Codigo codigo ) {
        FuncExp exp = new FuncExp();
        exp.setI( i );
        exp.setParente( parente );
        exp.setCodigo( codigo ); 
        exp.setBloco( execManager.getExecNoFactory().novoBloco( i, exp, codigo ) );
        exp.setExec( expExecManager.getFuncExpExec());
        return exp;
    }
    
    public BoolNaoExpValor novoBoolNaoExpValor( int i, ExecNo parente, Codigo codigo ) {
        BoolNaoExpValor exp = new BoolNaoExpValor();         
        exp.setI( i );
        exp.setParente( parente );
        exp.setCodigo( codigo ); 
        exp.setExec( expExecManager.getBoolVariavelExec() );
        return exp;
    }
    
    public BoolFunc novoBoolFunc( int i, ExecNo parente, Codigo codigo ) {
        BoolFunc boolFunc = new BoolFunc();    
        boolFunc.setI( i );
        boolFunc.setParente( parente );
        boolFunc.setCodigo( codigo );     
        
        FuncOuMatELExp funcExp = this.novoFuncOuMatELExp( i, boolFunc, codigo );
        
        boolFunc.setFuncExp( funcExp ); 
        boolFunc.setExec( expExecManager.getBoolFuncExec() );
        return boolFunc;
    }
    
    public BoolValor novoBoolValor( int i, ExecNo parente, Codigo codigo ) {
        BoolValor bv = new BoolValor();
        bv.setI( i );
        bv.setParente( parente );
        bv.setCodigo( codigo ); 
        bv.setExec( expExecManager.getBoolValorExec() );
        return bv;
    }
    
    public BoolExp novoBoolExp( int i, ExecNo parente, Codigo codigo ) {
        BoolExp exp = new BoolExp();
        exp.setI( i );
        exp.setParente( parente );
        exp.setCodigo( codigo ); 
        exp.setExec( expExecManager.getBoolExpExec() ); 
        return exp;
    }
    
    public BoolComp novoBoolComp( int i, ExecNo parente, Codigo codigo ) {
        BoolComp comp = new BoolComp();
        comp.setI( i );
        comp.setParente( parente );
        comp.setCodigo( codigo ); 
        comp.setExec( expExecManager.getBoolCompExec() ); 
        return comp;
    }
    
    public NumRealValor novoNumRealValor( int i, ExecNo parente, Codigo codigo ) {
        NumRealValor nv = new NumRealValor();
        nv.setI( i );
        nv.setParente( parente );
        nv.setCodigo( codigo ); 
        nv.setExec( expExecManager.getNumRealValorExec() );
        return nv;
    }
    
    public NumAtrib novoNumAtrib( int i, ExecNo parente, Codigo codigo ) {
        NumAtrib na = new NumAtrib();
        na.setI( i );
        na.setParente( parente );
        na.setCodigo( codigo ); 
        na.setExec( expExecManager.getNumAtribExec() );
        return na;
    }
    
    public NumIncDec novoNumIncDec( int i, ExecNo parente, Codigo codigo ) {
        NumIncDec nid = new NumIncDec();
        nid.setI( i );
        nid.setParente( parente );
        nid.setCodigo( codigo ); 
        nid.setExec( expExecManager.getNumIncDecExec() );
        return nid;
    }
    
    public NumMat novoNumMat( int i, ExecNo parente, Codigo codigo ) {
        NumMat nmexp = new NumMat();
        nmexp.setI( i );
        nmexp.setParente( parente );
        nmexp.setCodigo( codigo ); 
        nmexp.setExec( expExecManager.getNumMatExec() );
        return nmexp;
    }
    
    public NumVetN1IncN2 novoNumVetN1IncN2( int i, ExecNo parente, Codigo codigo ) {
        NumVetN1IncN2 vet = new NumVetN1IncN2();
        vet.setI( i );
        vet.setParente( parente );
        vet.setCodigo( codigo ); 
        vet.setExec( expExecManager.getNumVetN1IncN2Exec() );
        return vet;
    }
    
    public NumVariavel novoNumVariavel( int i, ExecNo parente, Codigo codigo ) {
        NumVariavel exp = new NumVariavel();
        exp.setI( i );
        exp.setParente( parente );
        exp.setCodigo( codigo ); 
        
        VariavelExp varexp = this.novoVariavelExp( i, exp, codigo );
        varexp.setExec( expExecManager.getVariavelExpExec() ); 

        exp.setVariavelExp( varexp );        
        exp.setExec( expExecManager.getNumVariavelExec() ); 
        return exp;
    }
    
    public NumFunc novoNumFunc( int i, ExecNo parente, Codigo codigo ) {
        NumFunc numFunc = new NumFunc();
        numFunc.setI( i );
        numFunc.setParente( parente );
        numFunc.setCodigo( codigo ); 
        
        FuncOuMatELExp funcExp = this.novoFuncOuMatELExp( i, numFunc, codigo );
        
        numFunc.setFuncExp( funcExp );
        numFunc.setExec( expExecManager.getNumFuncExec() ); 
        return numFunc;
    }
    
    public NumExp novoNumExp( int i, ExecNo parente, Codigo codigo ) {
        NumExp exp = new NumExp();
        exp.setI( i );
        exp.setParente( parente );
        exp.setCodigo( codigo ); 
        exp.setExec( expExecManager.getNumExpExec() ); 
        return exp;
    }
    
    public StrValor novoStrValor( int i, ExecNo parente, Codigo codigo ) {
        StrValor sv = new StrValor();
        sv.setI( i );
        sv.setParente( parente );
        sv.setCodigo( codigo ); 
        sv.setExec( expExecManager.getStrValorExec() ); 
        return sv;
    }
    
    public StrVariavel novoStrVariavel( int i, ExecNo parente, Codigo codigo ) {
        StrVariavel exp = new StrVariavel();
        exp.setI( i );
        exp.setParente( parente );
        exp.setCodigo( codigo ); 
        
        VariavelExp varexp = this.novoVariavelExp( i, exp, codigo );
        varexp.setExec( expExecManager.getVariavelExpExec() ); 

        exp.setVariavelExp( varexp );        
        exp.setExec( expExecManager.getVariavelExpExec() ); 
        return exp;
    }
    
    public StrFunc novoStrFunc( int i, ExecNo parente, Codigo codigo ) {
        StrFunc strFunc = new StrFunc();
        strFunc.setI( i );
        strFunc.setParente( parente );
        strFunc.setCodigo( codigo ); 
        
        FuncOuMatELExp funcExp = this.novoFuncOuMatELExp( i, strFunc, codigo );
        
        strFunc.setFuncExp( funcExp ); 
        strFunc.setExec( expExecManager.getChamadaFuncOuMatELExec() ); 
        return strFunc;
    }
    
    public StrExp novoStrExp( int i, ExecNo parente, Codigo codigo ) {
        StrExp exp = new StrExp();
        exp.setI( i );
        exp.setParente( parente );
        exp.setCodigo( codigo ); 
        exp.setExec( expExecManager.getStrExpExec() ); 
        return exp;
    }
    
    public GenericaMatrizValor novoMatrizValor( int i, ExecNo parente, Codigo codigo ) {
        GenericaMatrizValor mv = new GenericaMatrizValor();
        mv.setI( i );
        mv.setParente( parente );
        mv.setCodigo( codigo ); 
        mv.setExec( expExecManager.getMatrizValorExec() ); 
        return mv;
    }
    
    public NumMatrizValor novoNumMatrizValor( int i, ExecNo parente, Codigo codigo ) {
        NumMatrizValor nmv = new NumMatrizValor();
        nmv.setI( i );
        nmv.setParente( parente );
        nmv.setCodigo( codigo ); 
        nmv.setExec( expExecManager.getMatrizValorExec() ); 
        return nmv;
    }
    
    public VetN1IncN2 novoVetN1IncN2( int i, ExecNo parente, Codigo codigo ) {
        VetN1IncN2 vet = new VetN1IncN2();
        vet.setI( i );
        vet.setParente( parente );
        vet.setCodigo( codigo ); 
        vet.setExec( expExecManager.getVetN1IncN2Exec() );
        return vet;
    }
    
    public NullValor novoNullValor( int i, ExecNo parente, Codigo codigo ) {
        NullValor nv = new NullValor();
        nv.setI( i );
        nv.setParente( parente );
        nv.setCodigo( codigo ); 
        nv.setExec( expExecManager.getNullValorExec() );
        return nv;
    }
    
    public FuncVarExp novoFuncVarExp( int i, ExecNo parente, Codigo codigo ) {
        FuncVarExp exp = new FuncVarExp();
        exp.setI( i );
        exp.setParente( parente );
        exp.setCodigo( codigo );
        exp.setExec( expExecManager.getFuncVarExpExec() );
        return exp;
    }
            
}
