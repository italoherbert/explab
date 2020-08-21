package italo.explab.var.mat;

public class VetorMatrizVar extends MatrizVar {

    public final static int LINHA_ID = 1;
    public final static int COLUNA_ID = 2;

    public VetorMatrizVar( MatrizVar matvar ) {                    
        super( matvar.getNL() * matvar.getNC(), 1 );        
                
        int k = 0;
        for( int i = 0; i < matvar.getNC(); i++ )
            for( int j = 0; j < matvar.getNL(); j++ )
                super.setElemento( k++, 0, matvar.getElemento( j, i ) );                                
    }
    
    public VetorMatrizVar( MatrizVar matvar, int indice, int vetTipo ) {                    
        super( ( vetTipo == LINHA_ID ? matvar.getNL() : 1 ), ( vetTipo == LINHA_ID ? 1 : matvar.getNC() ) );        
              
        for( int i = 0; i < super.getNL(); i++ ) {
            for( int j = 0; j < super.getNC(); j++ ) {
                if ( vetTipo == LINHA_ID ) {
                    super.setElemento( i, j, matvar.getElemento( i, indice ) );            
                } else {
                    super.setElemento( i, j, matvar.getElemento( indice, j ) );  
                }
            }
        }
    }
    
}
