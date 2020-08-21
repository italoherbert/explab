package italo.explab.arvore.estruturas.node;

import italo.explab.arvore.BlocoNo;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.bloco.Bloco;
import italo.explab.arvore.instrucao.Instrucao;

public class Capture extends Instrucao implements BlocoNo {
        
    private String objetoExNome;
    private String[] exClassesNomes;
    private Bloco bloco;
    
    private int objetoExNomeI;

    @Override
    public Instrucao novo( ExecNo parent ) {
        Capture capture = new Capture();
        super.carrega( capture, parent );
        
        capture.setObjetoExNome( objetoExNome );
        capture.setExClassesNomes( exClassesNomes.clone() );
        capture.setObjetoExNomeI( objetoExNomeI );
        
        capture.setBloco( (Bloco)bloco.novo( capture ) );
        
        return capture;
    }

    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        bloco.setBaseParamsParente( this ); 
    }

    public String getObjetoExNome() {
        return objetoExNome;
    }

    public void setObjetoExNome(String objetoExNome) {
        this.objetoExNome = objetoExNome;
    }

    public int getObjetoExNomeI() {
        return objetoExNomeI;
    }

    public void setObjetoExNomeI(int objetoExNomeI) {
        this.objetoExNomeI = objetoExNomeI;
    }

    public String[] getExClassesNomes() {
        return exClassesNomes;
    }

    public void setExClassesNomes(String[] exClassesNomes) {
        this.exClassesNomes = exClassesNomes;
    }
    
    @Override
    public Bloco getBloco() {
        return bloco;
    }

    @Override
    public void setBloco(Bloco bloco) {
        this.bloco = bloco;
    }        
    
}
