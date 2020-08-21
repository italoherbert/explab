package italo.explab.inter.exp.mat;

import italo.explab.InterAplic;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.matriz.valor.MatrizValor;
import italo.explab.arvore.exp.matriz.valor.NumMatrizValor;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;


public class RealMatInter extends AbstractMatInter {

    @Override
    protected Inter getELValorInter(InterManager manager) {
        return manager.getNumExpInter();
    }

    @Override
    protected MatrizValor novoMatrizValor( InterAplic aplic, Codigo codigo, int i, ExecNo parente, Exp[][] matriz ) {
        NumMatrizValor mv = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoNumMatrizValor( i, parente, codigo );
        mv.setMatriz( matriz );
        return mv;
    }
    
}
