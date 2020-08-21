package italo.explab.func.plot.obj;

import java.awt.Color;

public class ExpLabGradeConfig {
        
    private boolean gradeVisivel = true;    
    private Color gradeCor = Color.BLACK;
    
    public boolean isGradeVisivel() {
        return gradeVisivel;
    }

    public void setGradeVisivel(boolean gradeVisivel) {
        this.gradeVisivel = gradeVisivel;
    }

    public Color getGradeCor() {
        return gradeCor;
    }

    public void setGradeCor(Color gradeCor) {
        this.gradeCor = gradeCor;
    }
    
}

