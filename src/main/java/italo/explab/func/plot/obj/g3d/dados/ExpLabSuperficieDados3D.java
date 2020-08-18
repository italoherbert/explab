package italo.explab.func.plot.obj.g3d.dados;

public class ExpLabSuperficieDados3D extends ExpLabDados3D {
        
    private double[][] matrizZ;
    private boolean somenteMalha = false;
    
    public double[][] getMatrizZ() {
        return matrizZ;
    }

    public void setMatrizZ(double[][] matrizZ) {
        this.matrizZ = matrizZ;
    }

    public boolean isSomenteMalha() {
        return somenteMalha;
    }

    public void setSomenteMalha(boolean somenteMalha) {
        this.somenteMalha = somenteMalha;
    }
    
}
