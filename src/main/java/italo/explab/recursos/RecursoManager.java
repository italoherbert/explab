package italo.explab.recursos;

import italo.explab.func.Func;
import italo.explab.recursos.classe.Classe;
import italo.explab.recursos.classe.ClasseLista;
import italo.explab.recursos.func.FuncLista;
import italo.explab.recursos.var.VarLista;
import italo.explab.recursos.var.Variavel;

public class RecursoManager {
            
    protected ClasseLista classeLista = new ClasseLista();
    protected VarLista varLista = new VarLista();
    protected FuncLista funcLista = new FuncLista();
                
    public RecursoManager novo() {
        RecursoManager copia = new RecursoManager();
        this.carrega( copia ); 
        return copia;
    }
    
    public void carrega( RecursoManager copia ) {
        for( Variavel v : varLista.getVariaveis() )
            copia.getVarLista().addVar( v.nova() );
        for( Func f : funcLista.getFuncs() )
            copia.getFuncLista().criaOuAltera( f.nova() );
        for( Classe c : classeLista.getClasses() )
            copia.getClasseLista().criaOuAltera( c.nova() ); 
    }
                 
    public void addRecursos( RecursoManager rec ) {
        for( Variavel v : rec.getVarLista().getVariaveis() )
            varLista.addVar( v );                                           
        for( Func f : rec.getFuncLista().getFuncs() )
            funcLista.criaOuAltera( f ); 
        for( Classe c : rec.getClasseLista().getClasses() )
            classeLista.criaOuAltera( c );                             
    }
    
    public ClasseLista getClasseLista() {
        return classeLista;
    }

    public VarLista getVarLista() {
        return varLista;
    }

    public FuncLista getFuncLista() {
        return funcLista;
    }
    
    public void setClasseLista(ClasseLista classeLista) {
        this.classeLista = classeLista;
    }

    public void setVarLista(VarLista varLista) {
        this.varLista = varLista;
    }

    public void setFuncLista(FuncLista funcLista) {
        this.funcLista = funcLista;
    }
        
}
