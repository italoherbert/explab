package italo.explab.matriz;

public class MatVetorDIMException extends MatrizException {

    private final int nl1;
    private final int nc1;
    private final int nl2;
    private final int nc2;

    public MatVetorDIMException(int nl1, int nc1, int nl2, int nc2) {
        this.nl1 = nl1;
        this.nc1 = nc1;
        this.nl2 = nl2;
        this.nc2 = nc2;
    }

    public int getNL1() {
        return nl1;
    }

    public int getNC1() {
        return nc1;
    }

    public int getNL2() {
        return nl2;
    }

    public int getNC2() {
        return nc2;
    }
    
}
