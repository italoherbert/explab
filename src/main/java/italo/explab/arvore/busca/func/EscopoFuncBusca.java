package italo.explab.arvore.busca.func;

import italo.explab.arvore.BlocoNo;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.Executor;
import italo.explab.arvore.ObjetoRecursoNo;
import italo.explab.func.Func;
import italo.explab.recursos.RecursoManager;
import italo.explab.recursos.classe.Objeto;

public class EscopoFuncBusca implements FuncBusca {

    @Override
    public FuncBuscaResult busca( ExecNo no, Executor executor, String nome, int quantParametros ) {        
        ExecNo corrente = no;
        while( corrente != null ) {
            if ( corrente instanceof BlocoNo ) {
                RecursoManager recursos = ((BlocoNo)corrente).getBloco().getRecursos();
                Func f = recursos.getFuncLista().buscaLocal( nome, quantParametros );
                if ( f != null ) {
                    Objeto obj = null;
                    if ( corrente instanceof ObjetoRecursoNo )
                        obj = ((ObjetoRecursoNo)corrente).getRuntimeObjeto();                    

                    return new FuncBuscaResult( f, obj );
                }
            }
             
            if ( corrente instanceof ObjetoRecursoNo ) {
                Objeto obj = ((ObjetoRecursoNo)corrente).getRuntimeObjeto();
                if ( obj != null ) {
                    RecursoManager recursos = obj.getRecursos();
                    Func f = recursos.getFuncLista().buscaLocal( nome, quantParametros );
                        if ( f != null )
                            return new FuncBuscaResult( f, obj );

                    Objeto corr = obj.getContainerObjeto();                        
                    while ( corr != null ) {
                        recursos = corr.getRecursos();
                        f = recursos.getFuncLista().buscaLocal( nome, quantParametros );
                        if ( f != null )
                            return new FuncBuscaResult( f, corr );

                        corr = corr.getContainerObjeto();                    
                    } 

                    corr = obj.getSuperObjeto();                        
                    while ( corr != null ) {
                        recursos = corr.getRecursos();
                        f = recursos.getFuncLista().buscaLocal( nome, quantParametros );
                        if ( f != null )
                            return new FuncBuscaResult( f, corr );

                        corr = corr.getSuperObjeto();                    
                    } 
                }                
            }       
                            
            corrente = corrente.getParente();                        
        }
        
        return null; 
    }           
    
}
