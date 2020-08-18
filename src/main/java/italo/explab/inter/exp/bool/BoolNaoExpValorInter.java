package italo.explab.inter.exp.bool;

import italo.explab.InterAplic;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.bool.node.BoolNaoExpValor;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;

public class BoolNaoExpValorInter extends Inter {

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        InterManager manager = aplic.getInterManager();
        
        InterResult ir = manager.getNaoBoolExpValorInter().interpreta( no, base, aplic, codigo, i, i2 );
        if ( ir.getErro() != null )
            return ir;

        if ( ir.getJ() > 0 ) {
            BoolNaoExpValor bnExpValor = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoBoolNaoExpValor( i, no, codigo );
            bnExpValor.setExp( (Exp)ir.getInstrucaoOuExp() );
            
            return new InterResult( bnExpValor, ir.getJ() );
        }
        
        return new InterResult();
    }
    
}
