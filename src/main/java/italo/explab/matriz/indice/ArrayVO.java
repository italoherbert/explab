package italo.explab.matriz.indice;

public class ArrayVO {
    
    public final static int VETOR = -1;
    public final static int ELEMENTO = -2;
    
    private final int i;
    private final int j;

    public ArrayVO( int i, int j ) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
    
}
