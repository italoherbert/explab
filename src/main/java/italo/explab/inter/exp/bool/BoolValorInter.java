package italo.explab.inter.exp.bool;

import italo.explab.InterAplic;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.bool.node.BoolValor;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.util.ContadorUtil;
import italo.explab.var.BooleanVar;

public class BoolValorInter extends Inter {

    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        ContadorUtil contUtil = aplic.getContUtil();
                
        int cont = contUtil.contaTextoValor( codigo, i, PalavrasReservadas.VERDADE );
        if ( cont > 0 ) {
            BoolValor bv = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoBoolValor( i, no, codigo );
            bv.setValor( new BooleanVar( true ) );
            return new InterResult( bv, cont );
        } else {
            cont = contUtil.contaTextoValor( codigo, i, PalavrasReservadas.FALSO );
            if ( cont > 0 ) {
                BoolValor bv = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoBoolValor( i, no, codigo );
                bv.setValor( new BooleanVar( false ) );
                return new InterResult( bv, cont );
            } else {
                return new InterResult();
            }
        }
    }
    
}
