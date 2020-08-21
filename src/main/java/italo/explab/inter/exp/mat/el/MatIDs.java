package italo.explab.inter.exp.mat.el;

import italo.explab.arvore.exp.matriz.indice.ExpMatIDs;
import italo.explab.msg.Erro;

public class MatIDs {
    
    private Erro erro;
    private ExpMatIDs expMatIDs;
    private int j;
    
    public MatIDs( ExpMatIDs expMatIDs, int j ) {
        this.expMatIDs = expMatIDs;
        this.j = j;
    }
        
    public MatIDs( Erro erro ) {
        this.erro = erro;
    }

    public Erro getErro() {
        return erro;
    }

    public void setErro(Erro erro) {
        this.erro = erro;
    }

    public ExpMatIDs getExpMatIDs() {
        return expMatIDs;
    }

    public void setExpMatIDs(ExpMatIDs expMatIDs) {
        this.expMatIDs = expMatIDs;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }
    
}
