package italo.explab.arvore.exp.matriz.valor;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;

public interface MatrizValor extends ExecNo {
    
    public Exp[][] getMatriz();
    
    public boolean isTransposta();
    
    public Exp getInstancia();
    
}
