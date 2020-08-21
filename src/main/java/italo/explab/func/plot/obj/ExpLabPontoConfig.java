package italo.explab.func.plot.obj;

import java.awt.Color;

public class ExpLabPontoConfig {
    
    private boolean visivel = false;    
    private Color cor = Color.RED;
    private int raioPX = 5;

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

    public int getRaioPX() {
        return raioPX;
    }

    public void setRaioPX(int raio) {
        this.raioPX = raio;
    }
    
    
}
