package italo.explab.func.plot.obj.g2d;

import italo.explab.func.plot.obj.ExpLabLinhaConfig;
import italo.explab.func.plot.obj.ExpLabPontoConfig;

public class ExpLabDados2D implements ExpLabGrafico2D {
    
    private double[] dadosX;
    private double[] dadosY;
    
    private int[][] arestas = null;
    
    private String legenda = null;
    
    private ExpLabLinhaConfig linhaConfig;
    private ExpLabPontoConfig pontoConfig;

    public String getLegenda() {
        return legenda;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }

    public double[] getDadosX() {
        return dadosX;
    }

    public void setDadosX(double[] dadosX) {
        this.dadosX = dadosX;
    }

    public double[] getDadosY() {
        return dadosY;
    }

    public void setDadosY(double[] dadosY) {
        this.dadosY = dadosY;
    }

    public int[][] getArestas() {
        return arestas;
    }

    public void setArestas(int[][] arestas) {
        this.arestas = arestas;
    }

    public ExpLabLinhaConfig getLinhaConfig() {
        return linhaConfig;
    }

    public void setLinhaConfig(ExpLabLinhaConfig linhaConfig) {
        this.linhaConfig = linhaConfig;
    }

    public ExpLabPontoConfig getPontoConfig() {
        return pontoConfig;
    }

    public void setPontoConfig(ExpLabPontoConfig pontoConfig) {
        this.pontoConfig = pontoConfig;
    }

    @Override
    public int getTipo() {
        return DADOS;
    }
    
}
