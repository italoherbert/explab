package italo.explab.arvore.cmd.node.leia;

import italo.explab.InterAplic;
import italo.explab.arvore.ExecNo;
import italo.explab.codigo.Codigo;

public interface LeiaListener {
    
    public LeiaResult execLeitura( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, boolean tipoString );
                
}
