package italo.explab.arvore.busca.func;

import italo.explab.arvore.BlocoNo;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.Executor;
import italo.explab.func.Func;

public class LocalFuncBusca implements FuncBusca {

    @Override
    public FuncBuscaResult busca( ExecNo no, Executor executor, String nome, int quantParametros ) {
        BlocoNo bno = no.blocoNo();
        if ( bno == null ) 
            return null;
        
        Func f = bno.getBloco().getRecursos().getFuncLista().buscaLocal( nome, quantParametros );
        return new FuncBuscaResult( f );
    }
    
}
