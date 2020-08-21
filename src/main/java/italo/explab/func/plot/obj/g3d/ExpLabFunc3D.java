package italo.explab.func.plot.obj.g3d;

import italo.explab.func.plot.obj.ExpLabLinhaConfig;
import italo.explab.func.plot.obj.ExpLabPoligonoConfig;
import italo.explab.func.plot.obj.ExpLabPontoConfig;
import italo.iplot.funcs.g3d.Func3D;

public class ExpLabFunc3D implements ExpLabGrafico3D {
    
    private double x1 = -1;
    private double y1 = -1;
    private double z1 = -1;
    private double x2 = 1;
    private double y2 = 1;
    private double z2 = 1;
    
    private int xndivs = 20;
    private int yndivs = 20;
    
    private boolean limitarZ = false;
    private boolean somenteMalha = false;
    
    private ExpLabPontoConfig pontoConfig;
    private ExpLabLinhaConfig linhaConfig;
    private ExpLabPoligonoConfig poligonoConfig;
    
    private Func3D func = null;

    public int getXNDivs() {
        return xndivs;
    }

    public void setXNDivs(int xndivs) {
        this.xndivs = xndivs;
    }

    public int getYNDivs() {
        return yndivs;
    }

    public void setYNDivs(int yndivs) {
        this.yndivs = yndivs;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getZ1() {
        return z1;
    }

    public void setZ1(double z1) {
        this.z1 = z1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    public double getZ2() {
        return z2;
    }

    public void setZ2(double z2) {
        this.z2 = z2;
    }

    public boolean isLimitarZ() {
        return limitarZ;
    }

    public void setLimitarZ(boolean limitarZ) {
        this.limitarZ = limitarZ;
    }

    public boolean isSomenteMalha() {
        return somenteMalha;
    }

    public void setSomenteMalha(boolean somenteMalha) {
        this.somenteMalha = somenteMalha;
    }

    public Func3D getFunc() {
        return func;
    }

    public void setFunc(Func3D func) {
        this.func = func;
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
        return FUNC;
    }
    
}
