package italo.explab.arvore.estruturas.node;

import italo.explab.arvore.BlocoNo;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.bloco.Bloco;
import italo.explab.arvore.grupo.Grupo;
import italo.explab.arvore.instrucao.Instrucao;

public class TenteCapture extends Instrucao implements BlocoNo {
    
    private Bloco bloco;
    private Capture[] captures;
    private Finalize finalize;

    @Override
    public Instrucao novo( ExecNo parent ) {
        TenteCapture tc = new TenteCapture();
        super.carrega( tc, parent );
        
        tc.setBloco( (Bloco)bloco.novo( tc ) );
        
        Capture[] caps = new Capture[ captures.length ];
        for( int k = 0; k < caps.length; k++ )
            caps[ k ] = (Capture)captures[ k ].novo( tc );
        
        tc.setCaptures( caps ); 
        
        tc.setFinalize( (Finalize)finalize.novo( tc ) ); 
        
        return tc;
    }

    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        bloco.setBaseParamsParente( parent );
        
        if ( captures != null ) 
            for( Capture cap : captures )
                cap.setBaseParamsParente( parent );
        
        finalize.setBaseParamsParente( parent ); 
    }
    
    @Override
    public Bloco getBloco() {
        return bloco;
    }

    @Override
    public void setBloco(Bloco bloco) {
        this.bloco = bloco;
    }

    public Capture[] getCaptures() {
        return captures;
    }

    public void setCaptures(Capture[] captures) {
        this.captures = captures;
    }

    public Grupo getFinalize() {
        return finalize;
    }

    public void setFinalize(Finalize finalize) {
        this.finalize = finalize;
    }
            
}
