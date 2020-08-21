package italo.explab.arvore.grupo;

import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;

public class GrupoExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        Grupo grupo = (Grupo)no;        
        return executor.exec( grupo.getBloco() );
    }
            
}
