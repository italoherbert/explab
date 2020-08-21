package italo.explab.arvore.estruturas.exec;

import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.estruturas.node.CompareCaso;
import italo.explab.arvore.estruturas.node.CompareCasoGrupo;

public class CompareCasoExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        CompareCaso compare = (CompareCaso)no;
         
        ExecResult r1 = executor.exec( compare.getExp() );
        if ( r1.isErroOuEx() )
            return r1;
        
        CompareCasoGrupo[] grupos = compare.getCasoGrupos();
        if ( grupos != null ) {
            boolean achou = false; 
            for( CompareCasoGrupo grupo : grupos ) {
                ExecResult r2 = executor.exec( grupo.getValor() );
                if ( r2.isErroOuEx() )
                    return r2;
                
                if ( achou || r2.getVar().iguais( r1.getVar() ) ) { 
                    achou = true;
                    ExecResult r3 = executor.exec( grupo );                    
                    if ( r3.getErro() != null || r3.isFinalizarBloco() )
                        return r3;                                                     
                }
            }
        }
        
        if ( compare.getPadraoGrupo() != null ) {
            ExecResult r2 = executor.exec( compare.getPadraoGrupo() );
            if ( r2.getErro() != null || r2.isFinalizarBloco() )
                return r2;         
        }
                
        return new ExecResult();
    }
            
}
