package italo.explab.recursos;

import italo.explab.arvore.exp.obj.NovoObjeto;
import italo.explab.construtor.Construtor;
import italo.explab.recursos.classe.Classe;
import italo.explab.recursos.construtor.ConstrutorLista;

public class ObjetoRecursoManager extends RecursoManager {
    
    private ConstrutorLista construtorLista = new ConstrutorLista();   
    private Classe classe;
    private NovoObjeto objref;
    
    @Override
    public ObjetoRecursoManager novo() {
        ObjetoRecursoManager copia = new ObjetoRecursoManager();
        this.carrega( copia );
        return copia;
    }
    
    public void carrega( ObjetoRecursoManager copia ) {
        super.carrega( copia ); 
        for( Construtor c : construtorLista.getConstrutores() )
            copia.getConstrutorLista().addConstrutor( c.novo() ); 
    }
    
    public ConstrutorLista getConstrutorLista() {
        return construtorLista;
    }

    public void setConstrutorLista( ConstrutorLista construtorLista ) {
        this.construtorLista = construtorLista;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public NovoObjeto getObjetoRef() {
        return objref;
    }

    public void setObjetoRef(NovoObjeto objref) {
        this.objref = objref;
    }

}
