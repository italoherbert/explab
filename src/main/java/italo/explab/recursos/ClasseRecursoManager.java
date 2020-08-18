package italo.explab.recursos;

import italo.explab.construtor.Construtor;
import italo.explab.func.Func;
import italo.explab.recursos.classe.Classe;
import italo.explab.recursos.construtor.ConstrutorLista;
import italo.explab.recursos.var.Variavel;

public class ClasseRecursoManager extends RecursoManager {
        
    private final ConstrutorLista construtorLista = new ConstrutorLista();
    
    @Override
    public ClasseRecursoManager novo() {
        ClasseRecursoManager copia = new ClasseRecursoManager();
        this.carrega( copia );
        return copia;
    }
    
    public void carrega( ClasseRecursoManager copia ) {
        super.carrega( copia ); 
        for( Construtor c : construtorLista.getConstrutores() )
            copia.getConstrutorLista().addConstrutor( c.novo() ); 
    }
    
    public void carrega( ObjetoRecursoManager copia ) {              
        for( Variavel v : varLista.getVariaveis() )
            copia.getVarLista().addVar( v.nova() );
        for( Func f : funcLista.getFuncs() )
            copia.getFuncLista().criaOuAltera( f.nova() );
        for( Classe c : classeLista.getClasses() )
            copia.getClasseLista().criaOuAltera( c.nova() ); 
        for( Construtor c : construtorLista.getConstrutores() )
            copia.getConstrutorLista().addConstrutor( c.novo() );        
    }
        
    public ConstrutorLista getConstrutorLista() {
        return construtorLista;
    }
    
}
