package italo.explab.arvore.exp.matriz.valor;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.instrucao.Instrucao;

public class GenericaMatrizValor extends Exp implements MatrizValor {

    private Exp[][] matriz;
    private boolean transposta;

    @Override
    public Instrucao novo( ExecNo parent ) {
        GenericaMatrizValor gMatVal = new GenericaMatrizValor();
        super.carrega( gMatVal, parent );
            
        Exp[][] mat = new Exp[ matriz.length ][];
        for( int k = 0; k < matriz.length; k++ ) {
            int len = matriz[ k ].length;
            
            mat[ k ] = new Exp[ len ];
            for( int kk = 0; kk < len; kk++ )
                mat[ k ][ kk ] = (Exp)matriz[ k ][ kk ].novo( gMatVal );            
        }
        
        gMatVal.setMatriz( mat ); 
                
        return gMatVal;
    }

    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        for( int k = 0; k < matriz.length; k++ ) {
            int len = matriz[ k ].length;            
            for( int kk = 0; kk < len; kk++ )
                matriz[ k ][ kk ].setBaseParamsParente( parent ); 
        }
    }
    
    @Override
    public Exp getInstancia() {
        return this;
    }

    @Override
    public Exp[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(Exp[][] matriz) {
        this.matriz = matriz;
    }

    @Override
    public boolean isTransposta() {
        return transposta;
    }

    public void setTransposta(boolean transposta) {
        this.transposta = transposta;
    }
            
}
