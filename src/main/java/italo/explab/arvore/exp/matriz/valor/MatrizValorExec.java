package italo.explab.arvore.exp.matriz.valor;

import italo.explab.ErroMSGs;
import italo.explab.arvore.Exec;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.ExecResult;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.Exp;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;
import italo.explab.var.Var;
import italo.explab.var.mat.MatrizVar;
import italo.explab.var.num.NumeroRealVar;

public class MatrizValorExec implements Exec {

    @Override
    public ExecResult exec( ExecNo no, Executor executor ) {
        MatrizValor mv = (MatrizValor)no;
        Exp[][] expsMatriz = mv.getMatriz();
        
        Codigo codigo = no.getCodigo();
        
        Var[][] matriz = this.converteParaMatriz( executor, expsMatriz );
        boolean temsubmat = this.temSubmat( matriz );
        
        int size = matriz.length;         
        
        MatrizVar matvar = null;
        if ( temsubmat ) {
            int nl = -1;
            int nc = -1;
            int maxMNL = 0;
            int maxMNC = 0;

            for( int k = 0; k < size; k++ ) {
                Var[] vet = matriz[ k ];
                int size2 = vet.length;

                int currNC = 0;                    
                for( int k2 = 0; k2 < size2; k2++ ) {
                    Var el = vet[ k2 ];
                    if ( el.getTipo() == Var.MATRIZ ) {
                        if ( ((MatrizVar)el).getNL() > maxMNL )
                            maxMNL = ((MatrizVar)el).getNL();
                        if ( ((MatrizVar)el).getNC() > maxMNC )
                            maxMNC = ((MatrizVar)el).getNC();

                        currNC += ( (MatrizVar)el ).getNC();
                    } else {
                        currNC++;
                    }
                }                

                if ( nc == -1 ) {
                    nc = currNC;
                } else if ( nc != currNC ) {
                    String sm = String.valueOf( maxMNL );
                    String sn = String.valueOf( nc );
                    String smat_m = String.valueOf( maxMNL );
                    String smat_n = String.valueOf( currNC );

                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.MATRIZ_DIM_INVALIDA, sm, sn, smat_m, smat_n ); 
                    return new ExecResult( erro );
                }
            }

            int[] currNCs = new int[ nc ];
            int[] currNCJs = new int[ nc ];
            for( int k = 0; k < nc; k++ ) {
                currNCs[ k ] = 0;
                currNCJs[ k ] = 0;
            }
            for( int k = 0; k < nc; k++ ) {
                int currNL = 0;
                boolean terminar = false;
                for( int k2 = 0; !terminar && k2 < size; k2++ ) {
                    Var[] vet = matriz[ k2 ];
                    int size2 = vet.length;
                    if ( currNCJs[k] >= size2 ) {
                        terminar = true;
                        continue;
                    }       

                    Var el = vet[ currNCJs[ k ] ];
                    if ( el.getTipo() == Var.MATRIZ ) {
                        currNL += ( (MatrizVar)el ).getNL();                                
                        if ( currNCs[k] == 0 )
                            currNCs[k] = ( (MatrizVar)el ).getNC();

                        if ( currNCs[k] > 0 ) {
                            currNCs[k]--;
                        } else {
                            currNCJs[k]++;
                        }
                    } else {
                        currNL++;
                    }                            
                }
                
                if ( nl == -1 ) {
                    nl = currNL;
                } else if ( nl != currNL ) {
                    String sm = String.valueOf( nl ); 
                    String sn = String.valueOf( maxMNC );
                    String smat_m = String.valueOf( currNL );
                    String smat_n = String.valueOf( currNCJs[k] );

                    CodigoErro erro = new CodigoErro( this.getClass(), codigo, no.getI(), ErroMSGs.MATRIZ_DIM_INVALIDA, sm, sn, smat_m, smat_n ); 
                    return new ExecResult( erro );
                }
            }            
                        
            if ( nl == -1 ) {
                matvar = new MatrizVar( 1, 0 );                               
            } else {
                matvar = this.matriz( matriz, nl, nc, maxMNL, maxMNC );                        
            }
        } else {
            int nl = size;
            int nc = ( nl > 0 ? matriz[ 0 ].length > 0 ? matriz[ 0 ].length : 0 : 0 );
            MatrizVar mat = new MatrizVar( nl, nc );
            for( int k = 0; k < nl; k++ ) {
                Var[] vet = matriz[ k ];
                int size2 = vet.length;

                for( int k2 = 0; k2 < nc; k2++ ) {
                    if ( k2 < size2 )
                        mat.setElemento( k, k2, vet[ k2 ] ); 
                    else mat.setElemento( k, k2, mat.elementoPadraoPorTC() );
                }
            }

            matvar = mat;
        }           
                
        if ( mv.isTransposta() )
            matvar = executor.getAplic().getFuncManager().getTranspostaFunc().transposta( matvar );                                                                  
                
        return new ExecResult( matvar );
    }    
    
    private Var[][] converteParaMatriz( Executor executor, Exp[][] matriz ) {
        Var[][] mat = new Var[ matriz.length ][];
        for( int i = 0; i < mat.length; i++ ) {            
            mat[i] = new Var[ matriz[i].length ];
            for( int j = 0; j < mat[i].length; j++ ) {                
                ExecResult er = executor.exec( matriz[i][j] );
                mat[i][j] = er.getVar();
            }
        }
        return mat;
    }
    
    private boolean temSubmat( Var[][] matriz ) {
        for( int i = 0; i < matriz.length; i++ )
            for( int j = 0; j < matriz[i].length; j++ )
                if ( matriz[i][j].getTipo() == Var.MATRIZ )
                    return true;
        return false;
    }
            
    private MatrizVar matriz( Var[][] elementos, int nl, int nc, int maxMNL, int maxMNC ) {
        MatrizVar mat = new MatrizVar( nl, nc );
        for( int i = 0; i < nl; i++ )
            for( int j = 0; j < nc; j++ )
                mat.setElemento( i, j, new NumeroRealVar( 0 ) ); 
        
        if ( nl > 0 && nc > 0 ) {
            this.preencheMatriz( mat, elementos, maxMNL, maxMNC );
            return mat;
        }                
        return new MatrizVar( 0, 0 );
    }
    
    public void preencheMatriz( MatrizVar mat, Var[][] elementos, int maxMNL, int maxMNC ) {        
        int size = elementos.length;
        for( int i = 0; i < size; i++ ) {
            int size2 = elementos[ i ].length;
            int jj = 0;
            for( int j = 0; j < size2; j++ ) {
                Var el = elementos[ i ][ j ];
                if ( el.getTipo() == Var.MATRIZ ) {
                    int mnl = ((MatrizVar)el).getNL();
                    int mnc = ((MatrizVar)el).getNC();
                    int k = this.matPosI( elementos, i, j );
                    int l = this.matPosJ( elementos, i, j );
                    for( int m = 0; m < mnl; m++ ) 
                        for( int n = 0; n < mnc; n++ )
                            mat.setElemento( k+m, l+n, ((MatrizVar)el).getElemento( m, n ) ); 
                    jj += mnc-1;
                } else {                    
                    mat.setElemento( i, j+jj, el ); 
                }
            }
        }
    }
    
    public int matPosI( Var[][] elementos, int i, int j ) {
        int pos = 0;
        for( int k = 0; k < i; k++ ) {
            Var el = elementos[ k ][ j ];
            if ( el.getTipo() == Var.MATRIZ ) {
                pos += ((MatrizVar)el).getNL();
            } else {
                pos++;
            }            
        }
        return pos;
    }
    
    public int matPosJ( Var[][] elementos, int i, int j ) {
        Var[] els = elementos[ i ];
        int pos = 0;
        for( int k = 0; k < j; k++ ) {
            Var el = els[ k ];
            if ( el.getTipo() == Var.MATRIZ ) {
                pos += ((MatrizVar)el).getNC();
            } else {
                pos++;
            }            
        }
        return pos;
    }
            
    public int matrizNL( MatrizVar mat ) {
        int matNL = 0;
        Var[][] matriz = mat.getMatriz();
        
        int maxNC = 0;
        for( int i = 0; i < matriz.length; i++ )
            if ( matriz[i].length > maxNC )
                maxNC = matriz[i].length;
                
        
        for( int i = 0; i < maxNC; i++ ) {
            int soma = 0;
            boolean fim = false;
            for( int j = 0; !fim && j < matriz.length; j++ ) {
                if ( i >= matriz[j].length ) {
                    fim = true;
                    continue;
                }
                Var el = matriz[j][i];                                        
                if ( el instanceof MatrizVar ) {
                    soma += ((MatrizVar)el).getNL();                                        
                } else {
                    soma++;
                }
            }
            
            if ( soma > matNL )
                matNL = soma;
        }
        return matNL;        
    }
    
    public int matrizNC( MatrizVar mat ) {
        int matNC = 0;

        Var[][] matriz = mat.getMatriz();   
        for( int i = 0; i < matriz.length; i++ ) {
            int soma = 0;
            for( int j = 0; j < matriz[i].length; j++ ) {
                Var el = matriz[i][j];                                        
                if ( el instanceof MatrizVar ) {
                    soma += ((MatrizVar)el).getNC();                                             
                } else {
                    soma++;
                }
            }
            if ( soma > matNC )
                 matNC = soma;
        }

        return matNC;        
    }         
    
}

