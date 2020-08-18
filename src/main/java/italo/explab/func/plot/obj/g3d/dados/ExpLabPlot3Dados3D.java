package italo.explab.func.plot.obj.g3d.dados;

public class ExpLabPlot3Dados3D extends ExpLabDados3D {
    
    private double[] vetorZ;
    private String legenda = null;
        
    public double[] getVetorZ() {
        return vetorZ;
    }

    public void setVetorZ(double[] vetorZ) {
        this.vetorZ = vetorZ;
    }

    public String getLegenda() {
        return legenda;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }

}
