package italo.explab.func;

import italo.explab.MetodoParam;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.func.FuncExp;
import italo.explab.codigo.Codigo;

public abstract class AbstractFuncAdapter extends AbstractFunc {
    
    public abstract FuncResult exec( Codigo codigo, MetodoParam[] params, int i );
    
    @Override
    public FuncResult exec( FuncExp fno, Executor executor, Codigo codigo, MetodoParam[] params ) {
        return this.exec( codigo, params, fno.getI() );
    }
    
}
