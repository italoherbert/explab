package italo.explab.matriz.indice;

public class MatrizIndice {        
        
    private int indice;
    private int pos;

    public MatrizIndice( int indice, int pos ) {
        this.indice = indice;
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }    
    
}
