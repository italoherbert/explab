package italo.explab.matriz;

import italo.explab.matriz.indice.ArrayVO;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;
import italo.explab.var.num.NumeroRealVar;

public class MatrizUtil {
       
    public MatrizVar matriz( double[] vetor ) {
        MatrizVar mat = new MatrizVar( 1, vetor.length );
        for( int i = 0; i < vetor.length; i++ )
            mat.setElemento( 0, i, new NumeroRealVar( vetor[ i ] ) );
        return mat;
    }
    
    public MatrizVar getMatrizColunaElementos( MatrizVar matrizVar, int linha ) throws MatDIMException {
        if ( linha >= matrizVar.getNL() )
            throw new MatDIMException();
        
        MatrizVar mat = new MatrizVar( 1, matrizVar.getNC() );
        for( int j = 0; j < matrizVar.getNC(); j++ )
            mat.setElemento( 0, j, matrizVar.getElemento( linha, j ) );
        return mat;
    }
    
    public MatrizVar getMatrizLinhaElementos( MatrizVar matrizVar, int coluna ) throws MatDIMException {                
        if ( coluna >= matrizVar.getNC() )
            throw new MatDIMException();
        MatrizVar mat = new MatrizVar( matrizVar.getNL(), 1 );
        for( int i = 0; i < matrizVar.getNL(); i++ )
            mat.setElemento( i, 0, matrizVar.getElemento( i, coluna ) );
        return mat;
    }
    
    public void setElemento( MatrizVar matrizVar, int linha, int col, Var valor ) 
            throws EsperadoTipoMatrizException, EsperadoTipoNaoMatrizException, MatVetorDIMException {
        int capNL = matrizVar.getCapacidadeNL();
        int capNC = matrizVar.getCapacidadeNC();
        if ( ( linha != ArrayVO.VETOR && linha >= capNL ) || ( col != ArrayVO.VETOR && col >= capNC ) ) {                        
            if ( linha != ArrayVO.VETOR ) { 
                if ( capNL == 0 )
                    capNL = 1;
                while ( linha >= capNL )
                    capNL *= 2;                
            }
                        
            if ( col != ArrayVO.VETOR ) {
                if ( capNC == 0 )
                    capNC = 1;                 
                while( col >= capNC )  
                    capNC *= 2;                            
            }
            
            Var[][] matriz = new Var[ capNL ][ capNC ];
            for( int i = 0; i < capNL; i++ ) {
                for( int j = 0; j < capNC; j++ ) {                    
                    if ( i < matrizVar.getNL() && j < matrizVar.getNC() ) {
                        matriz[ i ][ j ] = matrizVar.getElemento( i, j );
                    } else {
                        matriz[ i ][ j ] = matrizVar.elementoPadraoPorTC();
                    }
                }
            }
            
            int matNL = matrizVar.getNL();
            int matNC = matrizVar.getNC();
            if ( linha >= matNL )
                matNL = linha+1;
            if ( col >= matNC )
                matNC = col+1;
                        
            int tc;
            if ( valor.getTipoCompativel() != matrizVar.getTipoCompativel() ) {
                tc = Var.TC_GENERICO;
            } else {
                tc = valor.getTipoCompativel();
            }
            matrizVar.setMatriz( matriz, matNL, matNC, tc );
            
            this.setEL( matrizVar, linha, col, valor );            
        } else {
            if ( ( linha != ArrayVO.VETOR && linha >= matrizVar.getNL() ) || ( col != ArrayVO.VETOR && col >= matrizVar.getNC() ) ) {
                for( int i = 0; i <= linha; i++ )
                    for( int j = 0; j <= col; j++ )
                        if ( i >= matrizVar.getNL() || j >= matrizVar.getNC() )
                            matrizVar.setElemento( i, j, matrizVar.elementoPadraoPorTC() );                                                                                        
            }
            this.setEL( matrizVar, linha, col, valor );            
        }
    }
    
    public void setVetor( MatrizVar matvar, MatrizVar valor ) throws MatVetorDIMException {                             
        int nl1 = matvar.getNL();
        int nc1 = matvar.getNC();
        int nl2 = valor.getNL();
        int nc2 = valor.getNC();
        
        int tam = ( valor.getNL() == 1 ? valor.getNC() : valor.getNL() );
        if ( tam != ( matvar.getNL() * matvar.getNC() ) )
            throw new MatVetorDIMException( nl1, nc1, nl2, nc2 );
        
        int k = 0;
        for( int j = 0; j < matvar.getNC(); j++ ) {
            for( int i = 0; i < matvar.getNL(); i++ ) {
                if ( valor.getNC() == 1 ) {
                    matvar.setElemento( i, j, valor.getElemento( k, 0 ) );
                } else {
                    matvar.setElemento( i, j, valor.getElemento( 0, k ) );
                }
                k++;
            }
        }
    }    
    
    private void setEL( MatrizVar matvar, int linha, int col, Var valor ) 
            throws EsperadoTipoMatrizException, EsperadoTipoNaoMatrizException, MatVetorDIMException {
        if ( linha == ArrayVO.VETOR || col == ArrayVO.VETOR ) {
            if ( valor.getTipo() != Var.MATRIZ )
                throw new EsperadoTipoMatrizException();
            
            MatrizVar matvalor = (MatrizVar)valor;
            int nl1 = matvar.getNL();
            int nc1 = matvar.getNC();
            int nl2 = matvalor.getNL();
            int nc2 = matvalor.getNC();
            
            if ( linha == ArrayVO.VETOR && col == ArrayVO.VETOR ) {
                if ( matvar.getNL() != matvalor.getNL() || matvar.getNC() != matvalor.getNC() )
                    throw new MatVetorDIMException( nl1, nc1, nl2, nc2 );

                for( int i = 0; i < matvar.getNL(); i++ )
                    for( int j = 0; j < matvar.getNC(); j++ )
                        matvar.setElemento( i, j, matvalor.getElemento( i, j ) );                                                
            } else if ( linha == ArrayVO.VETOR ) {
                if ( matvalor.getNC() == 1 ) {
                    if ( matvar.getNL() != matvalor.getNL() )
                        throw new MatVetorDIMException( nl1, nc1, nl2, nc2 );                
                } else {
                    if ( matvar.getNL() != matvalor.getNC() )
                        throw new MatVetorDIMException( nl1, nc1, nl2, nc2 );
                }

                for( int i = 0; i < matvar.getNL(); i++ ) {
                    if ( matvalor.getNC() == 1 ) {
                        matvar.setElemento( i, col, matvalor.getElemento( i, 0 ) );
                    } else {
                        matvar.setElemento( i, col, matvalor.getElemento( 0, i ) );                    
                    }
                }
            } else {
                if ( matvalor.getNC() == 1 ) {
                    if ( matvar.getNC() != matvalor.getNL() )
                        throw new MatVetorDIMException( nl1, nc1, nl2, nc2 );                
                } else {
                    if ( matvar.getNC() != matvalor.getNC() )
                        throw new MatVetorDIMException( nl1, nc1, nl2, nc2 );
                }

                for( int j = 0; j < matvar.getNC(); j++ ) {
                    if ( matvalor.getNC() == 1 ) {
                        matvar.setElemento( linha, j, matvalor.getElemento( j, 0 ) );
                    } else {
                        matvar.setElemento( linha, j, matvalor.getElemento( 0, j ) );                    
                    }
                }
            }            
        } else {
            if ( valor.getTipo() == Var.MATRIZ )
                throw new EsperadoTipoNaoMatrizException();
        
            matvar.setElemento( linha, col, valor );
        }
    }
        
}
