package italo.explab.arvore.exp.matriz.valor;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.Exp;
import italo.explab.arvore.exp.num.node.NumericaExp;
import italo.explab.arvore.instrucao.Instrucao;

public class NumMatrizValor extends NumericaExp implements MatrizValor {
    
    private Exp[][] matriz;
  
    @Override
    public Instrucao novo( ExecNo parent ) {
        NumMatrizValor nMatVal = new NumMatrizValor();
        super.carrega( nMatVal, parent );
    
        Exp[][] mat = new Exp[ matriz.length ][];
        for( int k = 0; k < matriz.length; k++ ) {
            int len = matriz[ k ].length;
            
            mat[ k ] = new Exp[ len ];
            for( int kk = 0; kk < len; kk++ )
                mat[ k ][ kk ] = (Exp)matriz[ k ][ kk ].novo( nMatVal );            
        }
        
        nMatVal.setMatriz( mat );                         
        return nMatVal;
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
    
}
