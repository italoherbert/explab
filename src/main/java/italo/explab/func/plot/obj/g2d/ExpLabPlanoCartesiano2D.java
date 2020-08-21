package italo.explab.func.plot.obj.g2d;

import italo.explab.func.plot.obj.ExpLabGradeConfig;
import italo.explab.func.plot.obj.ExpLabJanelaConfig;
import java.util.ArrayList;
import java.util.List;

public class ExpLabPlanoCartesiano2D {
    
    private String titulo = null;
    private String xrotulo = "Eixo X";
    private String yrotulo = "Eixo Y";
            
    private final List<ExpLabGrafico2D> graficos = new ArrayList();
    
    private ExpLabJanelaConfig janelaConfig;
    private ExpLabGradeConfig grade;
    
    public void addGrafico( ExpLabGrafico2D graficos2d ) {
        graficos.add( graficos2d );
    }
    
    public List<ExpLabGrafico2D> getGraficos() {
        return graficos;
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
    
    public ExpLabGradeConfig getGradeConfig() {
        return grade;
    }

    public void setGradeConfig(ExpLabGradeConfig grade) {
        this.grade = grade;
    }

    public ExpLabJanelaConfig getJanelaConfig() {
        return janelaConfig;
    }

    public void setJanelaConfig(ExpLabJanelaConfig janelaConfig) {
        this.janelaConfig = janelaConfig;
    }
    
}
