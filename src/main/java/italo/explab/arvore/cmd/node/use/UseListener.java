package italo.explab.arvore.cmd.node.use;

import italo.explab.InterAplic;
import italo.explab.arvore.ExecNo;
import italo.explab.codigo.Codigo;

public interface UseListener {
    
    public UseResult interpretaClasses( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, String useArqNome, int arqNomeI );    
    
}
