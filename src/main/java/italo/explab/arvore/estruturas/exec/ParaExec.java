package italo.explab.arvore.estruturas.exec;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.estruturas.node.Para;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.var.BooleanVar;

public class ParaExec extends LoopExec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        Para para = (Para)no;
                
        ExecResult r1 = executor.exec( para.getIniInstsGrupo() );
        if ( r1.isErroOuEx() )
            return r1;                    
          
        ExecResult r2 = super.condicaoValor( para.getCondicao(), executor );
        if ( r2.isErroOuEx() )
            return r2;
        
        boolean condicao = ((BooleanVar)r2.getVar()).getValor();
        while ( condicao ) {                                                
            ExecResult r3 = executor.exec( para.getBloco() );
            if ( r3.isErroOuEx() )
                return r3;                        
            
            if ( r3.isFimLoop() ) {
                condicao = false;
            } else {
                ExecResult r4 = executor.exec( para.getAposFimITInstsGrupo() );
                if ( r4.isErroOuEx() )
                    return r4;                
                
                r2 = super.condicaoValor( para.getCondicao(), executor );
                if ( r2.isErroOuEx() )
                    return r2;
        
                condicao = ((BooleanVar)r2.getVar()).getValor(); 
            }
        }
        
        return new ExecResult();
    }        
    
}
