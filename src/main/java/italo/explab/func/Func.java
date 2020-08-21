package italo.explab.func;

import italo.explab.MetodoParam;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.func.FuncExp;
import italo.explab.codigo.Codigo;

public interface Func {        
            
    public Func nova();
    
    public FuncResult exec( FuncExp fno, Executor executor, Codigo codigo, MetodoParam[] params );    
    
    public boolean verifica( String nome, int quantParametros );
    
    public String getNome();
    
    public int getQuantParametros();
                                   
}
