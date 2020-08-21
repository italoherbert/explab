package italo.explab.func.plot.obj.g3d.dados;

import italo.explab.func.plot.obj.ExpLabLinhaConfig;
import italo.explab.func.plot.obj.ExpLabPoligonoConfig;
import italo.explab.func.plot.obj.ExpLabPontoConfig;
import italo.explab.func.plot.obj.g3d.ExpLabGrafico3D;

public abstract class ExpLabDados3D implements ExpLabGrafico3D {
    
    private double[] vetorX;
    private double[] vetorY;
    
    private int[][] arestas = null;
    
    private ExpLabPontoConfig pontoConfig;
    private ExpLabLinhaConfig linhaConfig;
    private ExpLabPoligonoConfig poligonoConfig;
    
    private boolean ehsuperficie = true;
                
    public double[] getVetorX() {
        return vetorX;
    }

    public void setVetorX(double[] vetorX) {
        this.vetorX = vetorX;
    }

    public double[] getVetorY() {
        return vetorY;
    }

    public void setVetorY(double[] vetorY) {
        this.vetorY = vetorY;
    }

    public int[][] getArestas() {
        return arestas;
    }

    public void setArestas(int[][] arestas) {
        this.arestas = arestas;
    }

    public boolean isEhSuperficie() {
        return ehsuperficie;
    }

    public void setEhSuperficie(boolean ehsuperficie) {
        this.ehsuperficie = ehsuperficie;
    }

    public ExpLabPontoConfig getPontoConfig() {
        return pontoConfig;
    }

    public void setPontoConfig(ExpLabPontoConfig pontoConfig) {
        this.pontoConfig = pontoConfig;
    }

    public ExpLabLinhaConfig getLinhaConfig() {
        return linhaConfig;
    }

    public void setLinhaConfig(ExpLabLinhaConfig linhaConfig) {
        this.linhaConfig = linhaConfig;
    }

    public ExpLabPoligonoConfig getPoligonoConfig() {
        return poligonoConfig;
    }

    public void setPoligonoConfig(ExpLabPoligonoConfig poligonoConfig) {
        this.poligonoConfig = poligonoConfig;
    }

    @Override
    public int getTipo() {
        return DADOS;
    }

}
