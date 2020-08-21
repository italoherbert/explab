package italo.explab.func.plot.obj;

import java.awt.Color;

public class ExpLabLinhaConfig {
    
    private boolean visivel = true;    
    private Color cor = Color.BLUE;
    private boolean preenchimentoGradiente = false;
    private boolean iluminar = false;

    public boolean isVisivel() {
        return visivel;
    }

    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color cor) {
        this.cor = cor;
    }

    public boolean isPreenchimentoGradiente() {
        return preenchimentoGradiente;
    }

    public void setPreenchimentoGradiente(boolean preenchimentoGradiente) {
        this.preenchimentoGradiente = preenchimentoGradiente;
    }

    public boolean isIluminar() {
        return iluminar;
    }

    public void setIluminar(boolean iluminar) {
        this.iluminar = iluminar;
    }
    
}
