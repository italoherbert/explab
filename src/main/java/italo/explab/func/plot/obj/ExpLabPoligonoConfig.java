package italo.explab.func.plot.obj;

import java.awt.Color;

public class ExpLabPoligonoConfig {
    
    private boolean visivel = true;
    private Color cor = Color.GREEN;
    private boolean preenchimentoGradiente = true;
    private boolean iluminar = true;

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

    public void setPreenchimentoGradiente(boolean gradiente) {
        this.preenchimentoGradiente = gradiente;
    }

    public boolean isIluminar() {
        return iluminar;
    }

    public void setIluminar(boolean iluminar) {
        this.iluminar = iluminar;
    }        
    
}
