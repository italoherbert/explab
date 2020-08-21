package italo.explab.arvore.bloco;

import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.incdec.IncDec;
import italo.explab.arvore.instrucao.Instrucao;

public class BlocoExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {        
        Bloco bloco = (Bloco)no;                
        
        Instrucao[] instrucoes = bloco.getInstrucoes();
        if ( instrucoes == null )
            return new ExecResult();
                                
        for( Instrucao inst : instrucoes ) {                                                                                 
            ExecResult er = executor.exec( inst );                                                                                                               
            if ( er.getErro() != null || er.isFinalizarBloco() ) 
                return er;                                                  
            
            if ( inst.getIncDecApos() != null )
                for( IncDec incdec : inst.getIncDecApos() )
                    executor.exec( incdec );
            
            if ( !inst.isFinalizadaComPontoEVirgula() )
                if ( er.getVar() != null )
                    executor.getAplic().getImpr().imp( er.getVar() );                 
        }        
        
        return new ExecResult();
    }
    
}
