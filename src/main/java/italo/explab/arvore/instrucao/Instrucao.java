package italo.explab.arvore.instrucao;

import italo.explab.arvore.AbstractExecNo;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.incdec.IncDec;
import java.util.ArrayList;
import java.util.List;

public abstract class Instrucao extends AbstractExecNo {

    private boolean finalizadaComPontoEVirgula = false;
    private List<IncDec> incdecs = null;    
        
    public abstract Instrucao novo( ExecNo parent );
        
    public abstract void setBaseParamsParente( ExecNo parent );
                
    @Override
    protected void carrega( AbstractExecNo novo, ExecNo parent ) {
        super.carrega( novo, parent );
        
        if ( incdecs != null )
            ((Instrucao)novo).setIncDecsAposCopia( incdecs ); 
        ((Instrucao)novo).setFinalizadaComPontoEVirgula( finalizadaComPontoEVirgula );         
    }
    
    public void setIncDecsAposCopia( List<IncDec> lista ) {
        List<IncDec> copia = new ArrayList();
        for( IncDec incdec : lista ) {
            IncDec novo = (IncDec)incdec.novo( this );
            novo.setExecSeAntIgual( false ); 
        
            copia.add( novo );
        }
        
        incdecs = copia;
    }
    
    public List<IncDec> getIncDecApos() {
        return incdecs;
    }

    public List<IncDec> getIncdecs() {
        return incdecs;
    }
    
    public boolean isFinalizadaComPontoEVirgula() {
        return finalizadaComPontoEVirgula;
    }

    public void setFinalizadaComPontoEVirgula(boolean finalizadaComPontoEVirgula) {
        this.finalizadaComPontoEVirgula = finalizadaComPontoEVirgula;
    }

}
