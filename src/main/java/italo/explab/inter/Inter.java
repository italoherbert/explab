package italo.explab.inter;

import italo.explab.InterAplic;
import italo.explab.arvore.ExecNo;
import italo.explab.codigo.Codigo;

public abstract class Inter {
    
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, int i, int i2 ) {
        return this.interpreta( no, base, aplic, codigo, null, i, i2 );
    }
        
    public abstract InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 );
    
}
