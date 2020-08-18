package italo.explab.recursos.func;

import italo.explab.func.FuncFactory;
import italo.explab.func.NumericaFunc;
import italo.explab.func.numerica.NFunc;
import italo.explab.func.numerica.NFuncFactory;
import italo.explab.func.string.StringFuncFactory;

public class GlobalFuncLista extends FuncLista {
    
    private final FuncFactory funcFactory = new FuncFactory();
    
    public GlobalFuncLista() {        
        NFuncFactory numericaFuncFactory = funcFactory.getNumFuncFactory();
        StringFuncFactory stringFuncFactory = funcFactory.getStringFuncFactory();
        
        for( String nome : NFuncFactory.FUNCS ) {
            NFunc nf = numericaFuncFactory.criaNova( nome );
            super.funcs.add( new NumericaFunc( numericaFuncFactory, nf ) );       
        }
        
        for( String nome : StringFuncFactory.FUNCS )
            super.funcs.add( stringFuncFactory.criaNova( nome ) );               
        
        for( String nome : FuncFactory.FUNCS )
            super.funcs.add( funcFactory.criaNova( nome ) );
    }        
    
}
