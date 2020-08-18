package italo.explab.arvore.estruturas.exec;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.estruturas.node.Enquanto;
import italo.explab.var.BooleanVar;

public class EnquantoExec extends LoopExec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        Enquanto enquanto = (Enquanto)no;
                        
        ExecResult r1 = super.condicaoValor( enquanto.getCondicao(), executor );
        if ( r1.isErroOuEx() )
            return r1;
        
        boolean condicao = ((BooleanVar)r1.getVar()).getValor();
        while ( condicao ) {
            ExecResult r2 = executor.exec( enquanto.getBloco() );
            if ( r2.isErroOuEx() )
                return r2;
            
            if ( r2.isFimLoop() ) {
                condicao = false;
            } else {                                
                r1 = super.condicaoValor( enquanto.getCondicao(), executor );
                if ( r1.isErroOuEx() )
                    return r1;
        
                condicao = ((BooleanVar)r1.getVar()).getValor(); 
            }
        }
        
        return new ExecResult();
    }
        
}
