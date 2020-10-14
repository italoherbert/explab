package italo.explab.func.plot.obj.g3d;

import italo.explab.func.plot.obj.ExpLabLinhaConfig;
import italo.explab.func.plot.obj.ExpLabPoligonoConfig;
import italo.explab.func.plot.obj.ExpLabPontoConfig;
import italo.iplot.funcs.g3d.Func3D;
import italo.iplot.plot3d.planocartesiano.g3d.PCFuncParametrica3DOpers;

public class ExpLabPFunc3D implements ExpLabGrafico3D {

    private double minX = -1;
    private double minY = -1;
    private double minZ = -1;
    private double maxX = 1;
    private double maxY = 1;
    private double maxZ = 1;
    
    private double u1;
    private double u2;
    private double v1;
    private double v2;
    private int npu = 20;
    private int npv = 20;
    
    private boolean limitarX = false;
    private boolean limitarY = false;
    private boolean limitarZ = false;
    private boolean somenteMalha = false;
    
    private PCFuncParametrica3DOpers funcOpers = null;
    
    private ExpLabPontoConfig pontoConfig;
    private ExpLabLinhaConfig linhaConfig;
    private ExpLabPoligonoConfig poligonoConfig;
    
    private Func3D funcX = null;
    private Func3D funcY = null;
    private Func3D funcZ = null;

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

    public double getMinZ() {
        return minZ;
    }

    public void setMinZ(double minZ) {
        this.minZ = minZ;
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

    public double getMaxZ() {
        return maxZ;
    }

    public void setMaxZ(double maxZ) {
        this.maxZ = maxZ;
    }


    public double getU1() {
        return u1;
    }

    public void setU1(double u1) {
        this.u1 = u1;
    }

    public double getU2() {
        return u2;
    }

    public void setU2(double u2) {
        this.u2 = u2;
    }

    public double getV1() {
        return v1;
    }

    public void setV1(double v1) {
        this.v1 = v1;
    }

    public double getV2() {
        return v2;
    }

    public void setV2(double v2) {
        this.v2 = v2;
    }

    public int getNPU() {
        return npu;
    }

    public void setNPU(int npu) {
        this.npu = npu;
    }

    public int getNPV() {
        return npv;
    }

    public void setNPV(int npv) {
        this.npv = npv;
    }

    public boolean isLimitarZ() {
        return limitarZ;
    }

    public void setLimitarZ(boolean limitarZ) {
        this.limitarZ = limitarZ;
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

    public boolean isSomenteMalha() {
        return somenteMalha;
    }

    public void setSomenteMalha(boolean somenteMalha) {
        this.somenteMalha = somenteMalha;
    }

    public Func3D getFuncX() {
        return funcX;
    }

    public void setFuncX(Func3D funcX) {
        this.funcX = funcX;
    }

    public Func3D getFuncY() {
        return funcY;
    }

    public void setFuncY(Func3D funcY) {
        this.funcY = funcY;
    }

    public Func3D getFuncZ() {
        return funcZ;
    }

    public void setFuncZ(Func3D funcZ) {
        this.funcZ = funcZ;
    }

    public PCFuncParametrica3DOpers getFuncOpers() {
        return funcOpers;
    }

    public void setFuncOpers(PCFuncParametrica3DOpers funcOpers) {
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
    
    @Override
    public int getTipo() {
        return PFUNC;
    }
    
}
