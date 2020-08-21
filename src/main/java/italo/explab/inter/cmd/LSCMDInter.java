package italo.explab.inter.cmd;

import italo.explab.InterAplic;
import italo.explab.codigo.Codigo;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.cmd.node.LS;
import italo.explab.inter.Inter;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.util.ContadorUtil;

public class LSCMDInter extends Inter {

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {                        
        ContadorUtil contUtil = aplic.getContUtil();
        int cont = contUtil.contaTextoValor( codigo, i, PalavrasReservadas.LS );
        if ( cont == 0 )
            return new InterResult();
        
        int j = cont;
                
        int jj = contUtil.contaEsps( codigo, i+j );        
        if ( jj == 0 && i+j < i2 )
            return new InterResult();
                        
        LS ls = aplic.getExecutor().getExecManager().getExecNoFactory().getCMDFactory().novoLS( i, no, codigo );
        
        return new InterResult( ls, j );
    }
    
}
