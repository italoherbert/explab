package italo.explab.arvore.busca.var;

import italo.explab.arvore.BlocoNo;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.Executor;
import italo.explab.recursos.RecursoManager;
import italo.explab.recursos.classe.Objeto;
import italo.explab.recursos.var.Variavel;
import italo.explab.arvore.ObjetoRecursoNo;
import italo.explab.arvore.exp.func.FuncExp;

public class EscopoVarBusca implements VarBusca {
            
    @Override
    public VarBuscaResult busca( ExecNo no, Executor executor, String nome ) {                                        
        ExecNo corrente = no;
        while( corrente != null ) {
            if ( corrente instanceof BlocoNo ) {
                RecursoManager recursos = ((BlocoNo)corrente).getBloco().getRecursos();
                Variavel v = recursos.getVarLista().buscaLocal( nome );                                    
                if ( v != null )
                    return new VarBuscaResult( v, recursos, null );
            }
                        
            if ( corrente instanceof ObjetoRecursoNo ) {
                Objeto obj = ((ObjetoRecursoNo)corrente).getRuntimeObjeto(); 
                if ( obj != null ) {                        
                    RecursoManager recursos = obj.getRecursos();
                    Variavel v = recursos.getVarLista().buscaLocal( nome );
                    if ( v != null )
                        return new VarBuscaResult( v, recursos, obj );

                    Objeto corr = obj.getContainerObjeto();                        
                    while ( corr != null ) {
                        recursos = corr.getRecursos();
                        v = recursos.getVarLista().buscaLocal( nome );
                        if ( v != null )
                            return new VarBuscaResult( v, recursos, corr );

                        corr = corr.getContainerObjeto();                    
                    } 

                    corr = obj.getSuperObjeto();                        
                    while ( corr != null ) {
                        recursos = corr.getRecursos();
                        v = recursos.getVarLista().buscaLocal( nome );
                        if ( v != null )
                            return new VarBuscaResult( v, recursos, corr );

                        corr = corr.getSuperObjeto();                    
                    } 
                }                 
            }     
            
            if ( corrente instanceof FuncExp ) {
                if ( corrente != no ) {
                    corrente = corrente.grupoRaiz();
                } else {
                    corrente = corrente.getParente();
                }
            } else {            
                corrente = corrente.getParente();
            }
        }
                
        return null;        
    }
        
    @Override
    public RecursoManager recursos( ExecNo no, Executor executor ) {
        BlocoNo bno = no.blocoNo();
        if ( bno == null )
            return null;
        
        return bno.getBloco().getRecursos();
    }
    
}
