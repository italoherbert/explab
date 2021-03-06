package italo.explab.func.plot.obj.g3d;

import italo.explab.func.plot.obj.ExpLabGradeConfig;
import italo.explab.func.plot.obj.ExpLabJanelaConfig;
import java.util.ArrayList;
import java.util.List;

public class ExpLabPlanoCartesiano3D {
    
    private String titulo = null;
    private String xrotulo = null;
    private String yrotulo = null;
    private String zrotulo = null;
    private double dx = -1;
    private double dy = -1;
    private double dz = -1;
            
    private final List<ExpLabGrafico3D> graficos = new ArrayList();
    
    private ExpLabJanelaConfig janelaConfig;
    private ExpLabGradeConfig gradeConfig;
    
    public void addGrafico( ExpLabGrafico3D graficos3d ) {
        graficos.add( graficos3d );
    }
    
    public List<ExpLabGrafico3D> getGraficos() {
        return graficos;
    }

    public double getDX() {
        return dx;
    }

    public void setDX(double dx) {
        this.dx = dx;
    }

    public double getDY() {
        return dy;
    }

    public void setDY(double dy) {
        this.dy = dy;
    }

    public double getDZ() {
        return dz;
    }

    public void setDZ(double dz) {
        this.dz = dz;
    }
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getXRotulo() {
        return xrotulo;
    }

    public void setXRotulo(String xrotulo) {
        this.xrotulo = xrotulo;
    }

    public String getYRotulo() {
        return yrotulo;
    }

    public void setYRotulo(String yrotulo) {
        this.yrotulo = yrotulo;
    }
        
    public String getZRotulo() {
        return zrotulo;
    }

    public void setZRotulo(String zrotulo) {
        this.zrotulo = zrotulo;
    }

    public ExpLabGradeConfig getGradeConfig() {
        return gradeConfig;
    }

    public void setGradeConfig(ExpLabGradeConfig gradeConfig) {
        this.gradeConfig = gradeConfig;
    }
  
    public ExpLabJanelaConfig getJanelaConfig() {
        return janelaConfig;
    }

    public void setJanelaConfig(ExpLabJanelaConfig janelaConfig) {
        this.janelaConfig = janelaConfig;
    }
    
}
