package italo.explab.func.plot.obj.g2d;

import italo.explab.func.plot.obj.ExpLabLinhaConfig;
import italo.explab.func.plot.obj.ExpLabPoligonoConfig;
import italo.explab.func.plot.obj.ExpLabPontoConfig;
import italo.iplot.funcs.g2d.Func2D;
import italo.iplot.plot2d.planocartesiano.g2d.PCFuncParametrica2DOpers;

public class ExpLabPFunc2D implements ExpLabGrafico2D {
        
    private double minX = -1;
    private double minY = -1;
    private double maxX = 1;
    private double maxY = 1;
    
    private double t1;
    private double t2;
    private int npt = 20;
    
    private String legenda = null;
    
    private boolean limitarX = false;
    private boolean limitarY = false;
    
    private PCFuncParametrica2DOpers funcOpers = null;
    
    private ExpLabPontoConfig pontoConfig;
    private ExpLabLinhaConfig linhaConfig;
    private ExpLabPoligonoConfig poligonoConfig;
    
    private Func2D funcX = null;
    private Func2D funcY = null;

    public double getT1() {
        return t1;
    }

    public void setT1(double t1) {
        this.t1 = t1;
    }

    public double getT2() {
        return t2;
    }

    public void setT2(double t2) {
        this.t2 = t2;
    }

    public int getNPT() {
        return npt;
    }

    public void setNPT(int npt) {
        this.npt = npt;
    }

    public String getLegenda() {
        return legenda;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }

    public double getMinX() {
        return minX;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    public boolean isLimitarX() {
        return limitarX;
    }

    public void setLimitarX(boolean limitarX) {
        this.limitarX = limitarX;
    }

    public boolean isLimitarY() {
        return limitarY;
    }

    public void setLimitarY(boolean limitarY) {
        this.limitarY = limitarY;
    }

    public PCFuncParametrica2DOpers getFuncOpers() {
        return funcOpers;
    }

    public void setFuncOpers(PCFuncParametrica2DOpers funcOpers) {
        this.funcOpers = funcOpers;
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

    public Func2D getFuncX() {
        return funcX;
    }

    public void setFuncX(Func2D funcX) {
        this.funcX = funcX;
    }

    public Func2D getFuncY() {
        return funcY;
    }

    public void setFuncY(Func2D funcY) {
        this.funcY = funcY;
    }

    
    @Override
    public int getTipo() {
        return PFUNC;
    }
    
}
