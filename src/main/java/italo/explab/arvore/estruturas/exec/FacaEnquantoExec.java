package italo.explab.arvore.estruturas.exec;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.estruturas.node.FacaEnquanto;
import italo.explab.var.BooleanVar;

public class FacaEnquantoExec extends LoopExec {
        
    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        FacaEnquanto enquanto = (FacaEnquanto)no;
                                
        boolean condicao;
        do {
            ExecResult r1 = executor.exec( enquanto.getBloco() );
            if ( r1.isErroOuEx() )
                return r1;
            
            if ( r1.isFimLoop() ) {
                condicao = false;
            } else {                                
                r1 = super.condicaoValor( enquanto.getCondicao(), executor );
                if ( r1.isErroOuEx() )
                    return r1;
        
                condicao = ((BooleanVar)r1.getVar()).getValor(); 
            }
        } while ( condicao );
        
        return new ExecResult();
    }
    
}
    
