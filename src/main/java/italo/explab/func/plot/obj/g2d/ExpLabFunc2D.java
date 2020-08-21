package italo.explab.func.plot.obj.g2d;

import italo.explab.func.plot.obj.ExpLabLinhaConfig;
import italo.iplot.funcs.g2d.Func2D;

public class ExpLabFunc2D implements ExpLabGrafico2D {
    
    private double x1 = -1;
    private double x2 = 1;
    private double y1 = Double.NEGATIVE_INFINITY;
    private double y2 = Double.POSITIVE_INFINITY;
    
    private String legenda = null;
    
    private boolean limitarY = false;
    private boolean xcompleto = false;
    
    private ExpLabLinhaConfig linha;
    
    private Func2D func = null;

    public boolean isXCompleto() {
        return xcompleto;
    }

    public void setXCompleto(boolean xcompleto) {
        this.xcompleto = xcompleto;
    }

    public boolean isLimitarY() {
        return limitarY;
    }

    public void setLimitarY(boolean limitarY) {
        this.limitarY = limitarY;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    
    public ExpLabLinhaConfig getLinhaConfig() {
        return linha;
    }

    public void setLinhaConfig(ExpLabLinhaConfig linha) {
        this.linha = linha;
    }

    public String getLegenda() {
        return legenda;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }

    public Func2D getFunc() {
        return func;
    }

    public void setFunc(Func2D func) {
        this.func = func;
    }

    @Override
    public int getTipo() {
        return FUNC;
    }
    
}
