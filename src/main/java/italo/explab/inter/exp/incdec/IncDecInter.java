package italo.explab.inter.exp.incdec;

import italo.explab.InterAplic;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.variavel.VariavelExp;
import italo.explab.arvore.exp.incdec.IncDec;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;

public class IncDecInter extends Inter {

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        InterManager manager = aplic.getInterManager();
        
        int j = 0;
        
        IncDec incDec = aplic.getExecutor().getExecManager().getExecNoFactory().novoIncDec( i, no, codigo );
        
        char ch1 = codigo.getSEGCH( i+j );
        char ch2 = codigo.getSEGCH( i+j+1 );
        
        boolean achou = false;
        
        if ( ch1 == '+' && ch2 == '+' ) {
            incDec.setInc( true ); 
            incDec.setAnt( true );
            
            achou = true;
            j += 2;
        } else if ( ch1 == '-' && ch2 == '-' ) {
            incDec.setInc( false ); 
            incDec.setAnt( true );  
            
            achou = true;
            j += 2;
        }                
        
        InterResult ir = manager.getOOVarOuMatELOuFuncInter().interpreta( incDec, base, aplic, codigo, i+j, i2 );
        if ( ir.getJ() == 0 )
            return ir;
        
        j += ir.getJ();
        
        if ( !achou ) {        
            ch1 = codigo.getSEGCH( i+j );
            ch2 = codigo.getSEGCH( i+j+1 );

            if ( ch1 == '+' && ch2 == '+' ) {
                incDec.setInc( true ); 
                incDec.setAnt( false );

                j += 2;
                achou = true;
                aplic.getSessaoManager().addIncDec( incDec );
            } else if ( ch1 == '-' && ch2 == '-' ) {
                incDec.setInc( false ); 
                incDec.setAnt( false ); 
                
                j += 2;
                achou = true;
                aplic.getSessaoManager().addIncDec( incDec );
            }
        }
        
        if ( achou ) {
            incDec.setVariavelExp( (VariavelExp)ir.getInstrucaoOuExp() ); 
            incDec.setExecSeAntIgual( true ); 
            return new InterResult( incDec, j );                    
        }
        
        return new InterResult();
    }
    
}
