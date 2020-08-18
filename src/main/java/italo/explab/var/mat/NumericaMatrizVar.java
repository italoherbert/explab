package italo.explab.var.mat;

import italo.explab.var.num.NumericaVar;
import italo.explab.var.num.NumeroRealVar;
import italo.explab.var.Var;

public class NumericaMatrizVar extends MatrizVar implements NumericaVar {
    
    public NumericaMatrizVar( double[][] matriz ) {
        super( null, 0, 0 );
        
        Var[][] mat = new Var[ matriz.length ][];
        for( int i = 0; i < matriz.length; i++ ) {
            int nc = matriz[ i ].length;
            Var[] vet = new Var[ nc ];
            for( int j = 0; j < nc; j++ )
                vet[ j ] = new NumeroRealVar( matriz[ i ][ j ] );
            mat[ i ] = vet;
        }
        
        int nl = matriz.length;
        int nc = 0;
        if ( matriz.length > 0 )
            nc = matriz[0].length;
        
        super.setMatriz( mat, nl, nc, Var.TC_NUMERICO );                 
    }
    
    public NumericaMatrizVar( MatrizVar matvar ) {
        super( matvar.getMatriz(), matvar.getNL(), matvar.getNC() );
        super.tipo = matvar.getTipo();
        super.tipoCompativel = matvar.getTipoCompativel();
    }
    
}
