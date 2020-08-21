package italo.explab.inter.exp.mat;

import italo.explab.InterAplic;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.matriz.valor.GenericaMatrizValor;
import italo.explab.arvore.exp.matriz.valor.MatrizValor;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;


public class MatInter extends AbstractMatInter {

    @Override
    protected Inter getELValorInter( InterManager manager ) {
        return manager.getValorInter();
    }

    @Override
    protected MatrizValor novoMatrizValor( InterAplic aplic, Codigo codigo, int i, ExecNo parente, Exp[][] matriz) {
        GenericaMatrizValor mv = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoMatrizValor( i, parente, codigo );
        mv.setMatriz( matriz );
        return mv;
    }
                                
}

